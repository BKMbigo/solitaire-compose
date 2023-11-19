package com.github.bkmbigo.solitaire.presentation.solitaire.screens

import com.github.bkmbigo.solitaire.data.FirebaseScoreRepository
import com.github.bkmbigo.solitaire.data.SolitaireScore
import com.github.bkmbigo.solitaire.game.solitaire.SolitaireGame
import com.github.bkmbigo.solitaire.game.solitaire.TableStack
import com.github.bkmbigo.solitaire.game.solitaire.configuration.SolitaireGameConfiguration
import com.github.bkmbigo.solitaire.game.solitaire.hints.SolitaireHintProvider
import com.github.bkmbigo.solitaire.game.solitaire.moves.MoveSource
import com.github.bkmbigo.solitaire.game.solitaire.moves.SolitaireGameMove
import com.github.bkmbigo.solitaire.game.solitaire.moves.SolitaireUserMove
import com.github.bkmbigo.solitaire.game.solitaire.providers.SolitaireGameProvider
import com.github.bkmbigo.solitaire.game.solitaire.scoring.SolitaireScoringSystem
import com.github.bkmbigo.solitaire.game.solitaire.utils.SolitaireDealOffset
import com.github.bkmbigo.solitaire.models.solitaire.TableStackEntry
import com.github.bkmbigo.solitaire.presentation.solitaire.screens.state.SolitaireLeaderboardDialogState
import com.github.bkmbigo.solitaire.presentation.solitaire.screens.state.SolitaireLeaderboardListState
import com.github.bkmbigo.solitaire.presentation.solitaire.screens.state.SolitaireState
import com.github.bkmbigo.solitaire.utils.Platform
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

abstract class AbstractSolitaireScreenModel(
    private val firebaseScoreRepository: FirebaseScoreRepository
) {
    abstract val scope: CoroutineScope

    private val _state = MutableStateFlow(SolitaireState())
    val state = _state.asStateFlow()

    private var currentDealOffset = SolitaireDealOffset.NONE

    private var clockJob: Job? = null

    private var scoringSystem = SolitaireScoringSystem()

    private val _hint = Channel<SolitaireUserMove>()
    val hint = _hint.receiveAsFlow()

    val score = scoringSystem.points

    private val _gameTime = MutableStateFlow(Duration.ZERO)
    val gameTime = _gameTime.asStateFlow()

    private val pastMoves = mutableListOf<RecordedUserMove>()
    private val redoMoves = mutableListOf<RecordedGameMove>()

    private val availableHints = mutableListOf<SolitaireUserMove>()
    private var allHintsAreExhausted = false
    private var isHintProvided = false
    private var hintIteration = 0

    private fun getCurrentGameTime(): Duration = _gameTime.value

    protected suspend fun performCreateGame(
        provider: SolitaireGameProvider,
        configuration: SolitaireGameConfiguration
    ) {
        val newGame = provider.createGame(configuration)
        if (newGame.isValid()) {
            // Amend game to reveal bottom-most cards
            val gameWithAmendments = amendGame(newGame)

            // reset past and redo moves
            pastMoves.clear()
            redoMoves.clear()

            // reset hints
            resetHints()

            // Update Scores
            scoringSystem.initializedGame(
                provider = provider,
                configuration = configuration
            )

            // Start Clock
            _gameTime.value = Duration.ZERO
            startClock()


            _state.value = SolitaireState(
                game = gameWithAmendments.first
            )
        }
    }

    protected fun performDeal() {
        val pastDealOffset = currentDealOffset
        val newGame = _state.value.game.play(SolitaireUserMove.Deal(getCurrentGameTime(), pastDealOffset))

        if (newGame.deckPositions.isEmpty()) {
            currentDealOffset = SolitaireDealOffset.NONE
        }

        val userMove = SolitaireUserMove.Deal(getCurrentGameTime(), pastDealOffset)

        // record moves made in iteration
        pastMoves.add(
            RecordedUserMove(
                userMove = userMove,
                amendments = emptyList()
            )
        )

        // Clear redoMoves
        redoMoves.clear()

        // reset hints
        resetHints()

        // Update Scores
        scoringSystem.moveMade(
            move = userMove,
            lastMove = pastMoves.getOrNull(pastMoves.size - 2)?.userMove
        )

        _state.value = _state.value.copy(
            game = newGame,
            canUndo = pastMoves.isNotEmpty(),
            canRedo = redoMoves.isNotEmpty()
        )
    }

    protected fun performPlay(move: SolitaireUserMove.CardMove) {
        if (!move.isValid(_state.value.game)) return

        // Move obtained from screen does not have a timeSinceStart. We need to update it here
        val moveWithTime = move.copy(timeSinceStart = getCurrentGameTime())

        val newGame = _state.value.game.play(moveWithTime)

        // If card moved from deck, increase the currentDealOffset
        if (moveWithTime.from is MoveSource.FromDeck) {
            currentDealOffset = currentDealOffset.increase()
        }

        val gameWithAmendments = amendGame(newGame)

        // Validate current game
        if (!gameWithAmendments.first.isValid()) return

        // record moves made in iteration
        pastMoves.add(
            RecordedUserMove(
                userMove = moveWithTime,
                amendments = gameWithAmendments.second
            )
        )

        // Clear redoMoves
        redoMoves.clear()

        // reset hints
        resetHints()

        // Update scores
        scoringSystem.moveMade(
            move = moveWithTime,
            lastMove = pastMoves.getOrNull(pastMoves.size - 2)?.userMove,
            fromDeckDifficulty = if (moveWithTime.from is MoveSource.FromDeck) {
                calculateFromDeckDifficulty(moveWithTime.from.index, _state.value.game.deck.size)
            } else null,
            doesRevealCard = gameWithAmendments.second.any { it is SolitaireGameMove.RevealCard },
            isRepetitiveMove = false
        )

        _state.value = _state.value.copy(
            game = gameWithAmendments.first,
            canUndo = pastMoves.isNotEmpty(),
            canRedo = redoMoves.isNotEmpty()
        )
    }

    protected fun performUndo() {
        /* The user seeks to undo their last move
        *  1.Perform a reverse move action on the last recorded move
        *  2. Add the undone move to the redo move stack */

        pastMoves.lastOrNull()?.let { lastMove ->
            lastMove.userMove.reversed(getCurrentGameTime())?.let { reverseMove ->
                val reverseAmendments = lastMove.amendments.mapNotNull { it.reversed(getCurrentGameTime()) }.reversed()

                var newGame = _state.value.game

                reverseAmendments.forEach { amendment ->
                    newGame = newGame.play(amendment)
                }

                newGame = newGame.play(reverseMove)

                // If card moved to deck, decrease the currentDealOffset
                if (reverseMove is SolitaireGameMove.ReturnToDeck) {
                    currentDealOffset = currentDealOffset.decrease()
                }

                pastMoves.removeLastOrNull()

                redoMoves.add(
                    RecordedGameMove(
                        userMove = reverseMove,
                        amendments = reverseAmendments
                    )
                )

                // reset hints
                resetHints()

                // Update Score
                scoringSystem.undoMovePerformed(
                    move = lastMove.userMove,
                    doesReturnCardToDeck = if (reverseMove is SolitaireGameMove.ReturnToDeck) {
                        currentDealOffset.increase()
                    } else null,
                    doesHideCard = reverseAmendments.any { it is SolitaireGameMove.HideCard },
                    doesMoveCardFromFoundation = reverseMove is SolitaireUserMove.CardMove && reverseMove.from == MoveSource.FromFoundation
                )

                _state.value = _state.value.copy(
                    game = newGame,
                    canUndo = pastMoves.isNotEmpty(),
                    canRedo = redoMoves.isNotEmpty()
                )
            }
        }
    }

    protected fun performRedo() {
        /* The user would like to redo a previously undone move. */
        redoMoves.lastOrNull()?.let { lastMove ->
            lastMove.userMove.reversed(getCurrentGameTime())?.let { reverse ->
                val reverseUserMove = reverse as? SolitaireUserMove
                reverseUserMove?.let { reverseMove ->
                    val reverseAmendments =
                        lastMove.amendments.mapNotNull { it.reversed(getCurrentGameTime()) }.reversed()

                    var newGame = _state.value.game

                    newGame = newGame.play(reverseMove)

                    // If card moved from deck, increase the currentDealOffset
                    if (reverseMove is SolitaireUserMove.CardMove && reverseMove.from is MoveSource.FromDeck) {
                        currentDealOffset = currentDealOffset.increase()
                    }

                    reverseAmendments.forEach { amendment ->
                        newGame = newGame.play(amendment)
                    }


                    redoMoves.removeLastOrNull()

                    pastMoves.add(
                        RecordedUserMove(
                            userMove = reverseMove,
                            amendments = reverseAmendments
                        )
                    )

                    // reset hints
                    resetHints()


                    // Update Scores
                    when (lastMove.userMove) {
                        is SolitaireUserMove.Deal -> {
                            // Update Scores
                            scoringSystem.redoMovePerformed(
                                move = lastMove.userMove
                            )
                        }

                        is SolitaireUserMove.CardMove -> {
                            // Update scores
                            scoringSystem.redoMovePerformed(
                                move = lastMove.userMove,
                                lastMove = pastMoves.getOrNull(pastMoves.size - 2)?.userMove,
                                fromDeckDifficulty = if (reverseMove is SolitaireUserMove.CardMove && reverseMove.from is MoveSource.FromDeck) {
                                    calculateFromDeckDifficulty(reverseMove.from.index, newGame.deck.size)
                                } else null,
                                doesRevealCard = reverseAmendments.any { it is SolitaireGameMove.RevealCard },
                                isRepetitiveMove = false
                            )
                        }

                        else -> {}
                    }

                    _state.value = _state.value.copy(
                        game = newGame,
                        canUndo = pastMoves.isNotEmpty(),
                        canRedo = redoMoves.isNotEmpty()
                    )
                }
            }
        }
    }

    protected fun performHint() {
        if (!isHintProvided) {
            availableHints.apply {
                clear()
                val hints = SolitaireHintProvider.provideHints(state.value.game).mapNotNull { it as? SolitaireUserMove }
                addAll(hints)
            }
            isHintProvided = true
        } else {
            // If the game is currently showing a hint, increment the hint iterator in order to obtain the next hint
            hintIteration = if (hintIteration >= availableHints.size - 1) {
                // Store a value to remember that you have accounted for all hints
                allHintsAreExhausted = true

                // Reset hintIteration to 0
                0
            } else {
                hintIteration + 1
            }
        }

        // Update Scores
        if (!allHintsAreExhausted && availableHints.isNotEmpty()) {
            scoringSystem.hintProvided()
        }

        // Pass the hint to the state
        availableHints.getOrNull(hintIteration)?.let { _hint.trySend(it) }
    }

    protected suspend fun submitScore(
        playerName: String,
        leaderboard: String?,
        platform: Platform
    ) {
        _state.value = _state.value.copy(
            showLeaderboardDialog = if (_state.value.showLeaderboardDialog is SolitaireLeaderboardDialogState.LeaderboardAndScore) {
                (_state.value.showLeaderboardDialog as SolitaireLeaderboardDialogState.LeaderboardAndScore).copy(
                    isEntryAllowed = false,
                    isEntryLoading = true
                )
            } else {
                _state.value.showLeaderboardDialog
            }
        )

        firebaseScoreRepository.addKlondikeScore(
            SolitaireScore(
                playerName = playerName,
                score = score.value,
                leaderboard = leaderboard,
                platform = platform
            )
        )

        _state.value = _state.value.copy(
            showLeaderboardDialog = if (_state.value.showLeaderboardDialog is SolitaireLeaderboardDialogState.LeaderboardAndScore) {
                (_state.value.showLeaderboardDialog as SolitaireLeaderboardDialogState.LeaderboardAndScore).copy(
                    isEntryAllowed = false,
                    isEntryLoading = false
                )
            } else {
                _state.value.showLeaderboardDialog
            }
        )
    }

    protected suspend fun showLeaderboardDialogBeforeWin() {
        _state.value = _state.value.copy(
            showLeaderboardDialog = SolitaireLeaderboardDialogState.LeaderboardOnly(
                leaderboard = SolitaireLeaderboardListState.Loading
            )
        )

        val latestList = retrieveLeaderboard()

        _state.value = _state.value.copy(
            showLeaderboardDialog = _state.value.showLeaderboardDialog?.copyWithLeaderboard(
                leaderboard = SolitaireLeaderboardListState.Success(latestList)
            )
        )
    }

    protected suspend fun showLeaderboardDialogAfterWin(platform: Platform) {
        _state.value = _state.value.copy(
            showLeaderboardDialog = SolitaireLeaderboardDialogState.LeaderboardAndScore(
                leaderboard = SolitaireLeaderboardListState.Loading,
                isEntryAllowed = true,
                score = score.value,
                platform = platform
            )
        )

        val latestList = retrieveLeaderboard()

        _state.value = _state.value.copy(
            showLeaderboardDialog = _state.value.showLeaderboardDialog?.copyWithLeaderboard(
                leaderboard = SolitaireLeaderboardListState.Success(latestList)
            )
        )
    }

    protected suspend fun getLatestCustomLeaderboard(leaderboard: String?) {
        _state.value = _state.value.copy(
            showLeaderboardDialog = _state.value.showLeaderboardDialog?.copyWithLeaderboard(
                leaderboard = SolitaireLeaderboardListState.Loading
            )
        )

        val newList = if (!leaderboard.isNullOrBlank()) {
            retrieveLeaderboardByCustomLeaderboard(leaderboard)
        } else {
            retrieveLeaderboard()
        }

        _state.value = _state.value.copy(
            showLeaderboardDialog = _state.value.showLeaderboardDialog?.copyWithLeaderboard(
                leaderboard = SolitaireLeaderboardListState.Success(newList)
            )
        )
    }

    protected fun performHideLeaderboardDialog() {
        _state.value = _state.value.copy(
            showLeaderboardDialog = null
        )
    }

    private suspend fun retrieveLeaderboardByCustomLeaderboard(leaderboard: String) =
        firebaseScoreRepository.getLeaderboard(leaderboard)

    private suspend fun retrieveLeaderboard(): List<SolitaireScore> =
        firebaseScoreRepository.getTopLeaderboard()

    private fun resetHints() {
        availableHints.clear()
        hintIteration = 0
        allHintsAreExhausted = false
        isHintProvided = false
    }

    private fun calculateFromDeckDifficulty(deckIndex: Int, deskSize: Int): SolitaireDealOffset {
        val rem = deckIndex % 3
        return if (rem == 0 || deckIndex == deskSize) {
            // The card is on top
            SolitaireDealOffset.NONE
        } else if (rem == 2) {
            SolitaireDealOffset.ONE
        } else {
            SolitaireDealOffset.TWO
        }
    }

    private fun amendGame(newGame: SolitaireGame): Pair<SolitaireGame, List<SolitaireGameMove>> {
        /* Corrects the game. Essentially, it reveals the top-most card on the stack if there are no revealed cards and there are hidden cards. */

        // Find reveal amendments
        val revealAmendments = newGame.findAmendments()

        var currentGame = newGame

        // Play each reveal amendment to get a newGame Todo: Use a side-effect free method (e.g call Game.play() with a list of moves).
        revealAmendments.forEach {
            currentGame = currentGame.play(it)
        }

        return currentGame to revealAmendments
    }

    private suspend fun startClock() {
        clockJob?.cancel()

        clockJob = scope.launch {
            withContext(Dispatchers.Default) {
                while (isActive) {
                    delay(1000)
                    _gameTime.value += (1).toDuration(DurationUnit.SECONDS)
                }
            }
        }
    }

    private fun SolitaireGame.findAmendments(): List<SolitaireGameMove> = tableStacks.mapIndexed { index, tableStack ->
        if (tableStack.hasRevealAmendment())
            SolitaireGameMove.RevealCard(
                timeSinceStart = getCurrentGameTime(),
                tableStackEntry = getTableStackEntryFromIndex(index)
            )
        else null
    }.filterNotNull()

    private fun TableStack.hasRevealAmendment(): Boolean = revealedCards.isEmpty() && hiddenCards.isNotEmpty()

    private fun getTableStackEntryFromIndex(index: Int) = TableStackEntry.entries[index]

    private data class RecordedUserMove(
        val userMove: SolitaireUserMove,
        val amendments: List<SolitaireGameMove>
    ) : List<SolitaireGameMove> by listOf(listOf(userMove), amendments).flatten()

    private data class RecordedGameMove(
        val userMove: SolitaireGameMove,
        val amendments: List<SolitaireGameMove>
    ) : List<SolitaireGameMove> by listOf(listOf(userMove), amendments).flatten()
}

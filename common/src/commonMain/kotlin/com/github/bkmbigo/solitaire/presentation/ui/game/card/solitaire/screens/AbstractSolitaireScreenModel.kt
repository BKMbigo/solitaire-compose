package com.github.bkmbigo.solitaire.presentation.ui.game.card.solitaire.screens

import com.github.bkmbigo.solitaire.game.solitaire.SolitaireGame
import com.github.bkmbigo.solitaire.game.solitaire.TableStack
import com.github.bkmbigo.solitaire.game.solitaire.configuration.SolitaireCardsPerDeal
import com.github.bkmbigo.solitaire.game.solitaire.configuration.SolitaireGameConfiguration
import com.github.bkmbigo.solitaire.game.solitaire.hints.SolitaireHintProvider
import com.github.bkmbigo.solitaire.game.solitaire.moves.MoveSource
import com.github.bkmbigo.solitaire.game.solitaire.moves.SolitaireGameMove
import com.github.bkmbigo.solitaire.game.solitaire.moves.SolitaireUserMove
import com.github.bkmbigo.solitaire.game.solitaire.providers.SolitaireGameProvider
import com.github.bkmbigo.solitaire.game.solitaire.utils.SolitaireDealOffset
import com.github.bkmbigo.solitaire.models.solitaire.TableStackEntry
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class AbstractSolitaireScreenModel {
    private val _state = MutableStateFlow(SolitaireState())
    val state = _state.asStateFlow()

    private var currentDealOffset = SolitaireDealOffset.NONE

    private val _hint = Channel<SolitaireUserMove>()
    val hint = _hint.receiveAsFlow()

    private val pastMoves = mutableListOf<RecordedUserMove>()
    private val redoMoves = mutableListOf<RecordedGameMove>()

    private val availableHints = mutableListOf<SolitaireUserMove>()
    private var isHintProvided = false
    private var hintIteration = 0

    protected suspend fun performCreateGame(provider: SolitaireGameProvider) {
        val newGame = provider.createGame(SolitaireGameConfiguration(SolitaireCardsPerDeal.THREE))
        if (newGame.isValid()) {
            // Amend game to reveal bottom-most cards
            val gameWithAmendments = amendGame(newGame)

            // reset past and redo moves
            pastMoves.clear()
            redoMoves.clear()

            // reset hints
            resetHints()

            _state.value = SolitaireState(
                game = gameWithAmendments.first
            )
        }
    }

    protected fun performDeal() {
        if (!_state.value.isDrawn && !_state.value.isWon) {
            val pastDealOffset = currentDealOffset
            val newGame = _state.value.game.play(SolitaireUserMove.Deal(pastDealOffset))

            if (newGame.deckPositions.isEmpty()) {
                currentDealOffset = SolitaireDealOffset.NONE
            }

            // record moves made in iteration
            pastMoves.add(
                RecordedUserMove(
                    userMove = SolitaireUserMove.Deal(pastDealOffset),
                    amendments = emptyList()
                )
            )

            // Clear redoMoves
            redoMoves.clear()

            // reset hints
            resetHints()

            _state.value = _state.value.copy(
                game = newGame,
                canUndo = pastMoves.isNotEmpty(),
                canRedo = redoMoves.isNotEmpty(),
                isWon = newGame.isWon(),
                isDrawn = newGame.isDrawn()
            )
        }
    }

    protected fun performPlay(move: SolitaireUserMove) {
        if (!_state.value.isDrawn && !_state.value.isWon) {

            if (!move.isValid(_state.value.game)) return

            val newGame = _state.value.game.play(move)

            // If card moved from deck, increase the currentDealOffset
            if (move is SolitaireUserMove.CardMove && move.from is MoveSource.FromDeck) {
                currentDealOffset = currentDealOffset.increase()
            }

            val gameWithAmendments = amendGame(newGame)

            // Validate current game
            if (!gameWithAmendments.first.isValid()) return

            // record moves made in iteration
            pastMoves.add(
                RecordedUserMove(
                    userMove = move,
                    amendments = gameWithAmendments.second
                )
            )

            // Clear redoMoves
            redoMoves.clear()

            // reset hints
            resetHints()

            _state.value = _state.value.copy(
                game = gameWithAmendments.first,
                canUndo = pastMoves.isNotEmpty(),
                canRedo = redoMoves.isNotEmpty(),
                isWon = gameWithAmendments.first.isWon(),
                isDrawn = gameWithAmendments.first.isDrawn()
            )
        }
    }

    protected fun performUndo() {
        /* The user seeks to undo their last move
        *  1.Perform a reverse move action on the last recorded move
        *  2. Add the undone move to the redo move stack */

        pastMoves.lastOrNull()?.let { lastMove ->
            lastMove.userMove.reversed()?.let { reverseMove ->
                val reverseAmendments = lastMove.amendments.mapNotNull { it.reversed() }.reversed()

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

                _state.value = _state.value.copy(
                    game = newGame,
                    canUndo = pastMoves.isNotEmpty(),
                    canRedo = redoMoves.isNotEmpty(),
                    isWon = newGame.isWon(),
                    isDrawn = newGame.isDrawn()
                )
            }
        }
    }

    protected fun performRedo() {
        /* The user would like to redo a previously undone move. */
        redoMoves.lastOrNull()?.let { lastMove ->
            lastMove.userMove.reversed()?.let { reverse ->
                val reverseUserMove = reverse as? SolitaireUserMove
                reverseUserMove?.let { reverseMove ->
                    val reverseAmendments = lastMove.amendments.mapNotNull { it.reversed() }.reversed()

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

                    _state.value = _state.value.copy(
                        game = newGame,
                        canUndo = pastMoves.isNotEmpty(),
                        canRedo = redoMoves.isNotEmpty(),
                        isWon = newGame.isWon(),
                        isDrawn = newGame.isDrawn()
                    )
                }
            }
        }
    }

    protected fun performHint() {
        if(!isHintProvided) {
            availableHints.apply {
                clear()
                val hints = SolitaireHintProvider.provideHints(state.value.game).mapNotNull { it as? SolitaireUserMove }
                addAll(hints)
            }
            isHintProvided = true
        } else {
            // If the game is currently showing a hint, increment the hint iterator in order to obtain the next hint
            hintIteration = if (hintIteration >= availableHints.size - 1) 0 else hintIteration + 1
        }

        // Pass the hint to the state
        availableHints.getOrNull(hintIteration)?.let { _hint.trySend(it) }
    }

    private fun resetHints() {
        availableHints.clear()
        hintIteration = 0
        isHintProvided = false
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

    private fun SolitaireGame.findAmendments(): List<SolitaireGameMove> = tableStacks.mapIndexed { index, tableStack ->
        if (tableStack.hasRevealAmendment()) SolitaireGameMove.RevealCard(getTableStackEntryFromIndex(index)) else null
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

package com.github.bkmbigo.solitaire.presentation.ui.game.card.solitaire.screens

import com.github.bkmbigo.solitaire.game.solitaire.SolitaireGame
import com.github.bkmbigo.solitaire.game.solitaire.TableStack
import com.github.bkmbigo.solitaire.game.solitaire.moves.MoveSource
import com.github.bkmbigo.solitaire.game.solitaire.moves.SolitaireGameMove
import com.github.bkmbigo.solitaire.game.solitaire.moves.SolitaireUserMove
import com.github.bkmbigo.solitaire.game.solitaire.providers.SolitaireGameProvider
import com.github.bkmbigo.solitaire.models.solitaire.TableStackEntry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class AbstractSolitaireScreenModel {
    private val _state = MutableStateFlow(SolitaireState())
    val state = _state.asStateFlow()

    private val pastMoves = mutableListOf<RecordedMove>()

    protected suspend fun performCreateGame(provider: SolitaireGameProvider) {
        val newGame = provider.createGame()
        if (newGame.isValid()) {
            val gameWithAmendments = amendGame(newGame)
            _state.value = SolitaireState(
                game = gameWithAmendments.first
            )
        }
    }

    protected suspend fun performDeal() {
        if (!_state.value.isDrawn && !_state.value.isWon) {
            val newGame = _state.value.game.play(SolitaireUserMove.Deal)

            // record moves made in iteration
            pastMoves.add(
                RecordedMove(
                    userMove = SolitaireUserMove.Deal,
                    amendments = emptyList()
                )
            )

            _state.value = _state.value.copy(
                game = newGame,
                deckPosition = if(_state.value.deckPosition < newGame.deck.size) _state.value.deckPosition + 1 else 0,
                canUndo = pastMoves.isNotEmpty(),
                isWon = newGame.isWon(),
                isDrawn = newGame.isDrawn()
            )
        }
    }

    protected suspend fun performPlay(move: SolitaireUserMove) {
        if (!_state.value.isDrawn && !_state.value.isWon) {

            if (!move.isValid(_state.value.game)) return

            val newGame = _state.value.game.play(move)

            val gameWithAmendments = amendGame(newGame)

            // Validate current game
            if (!gameWithAmendments.first.isValid()) return

            // record moves made in iteration
            pastMoves.add(
                RecordedMove(
                    userMove = move,
                    amendments = gameWithAmendments.second
                )
            )

            val newDeckPosition = if (move is SolitaireUserMove.CardMove && move.from is MoveSource.FromDeck) {
                _state.value.deckPosition - 1
            } else _state.value.deckPosition

            _state.value = _state.value.copy(
                game = gameWithAmendments.first,
                deckPosition = newDeckPosition,
                canUndo = pastMoves.isNotEmpty(),
                isWon = gameWithAmendments.first.isWon(),
                isDrawn = gameWithAmendments.first.isDrawn()
            )
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

    private fun SolitaireGame.findAmendments(): List<SolitaireGameMove> = tableStacks.mapIndexed { index, tableStack ->
        if (tableStack.hasRevealAmendment()) SolitaireGameMove.RevealCard(getTableStackEntryFromIndex(index)) else null
    }.filterNotNull()

    private fun TableStack.hasRevealAmendment(): Boolean = revealedCards.isEmpty() && hiddenCards.isNotEmpty()

    private fun getTableStackEntryFromIndex(index: Int) = TableStackEntry.entries[index]

    private data class RecordedMove(
        val userMove: SolitaireUserMove,
        val amendments: List<SolitaireGameMove>
    ): List<SolitaireGameMove> by listOf(listOf(userMove), amendments).flatten()
}

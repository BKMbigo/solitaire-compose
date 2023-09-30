package com.github.bkmbigo.solitaire.game.solitaire.hints

import com.github.bkmbigo.solitaire.game.GameHintProvider
import com.github.bkmbigo.solitaire.game.solitaire.SolitaireGame
import com.github.bkmbigo.solitaire.game.solitaire.hints.SolitaireFoundationToTableStackHintProvider.invoke
import com.github.bkmbigo.solitaire.game.solitaire.moves.MoveDestination
import com.github.bkmbigo.solitaire.game.solitaire.moves.MoveSource
import com.github.bkmbigo.solitaire.game.solitaire.moves.SolitaireGameMove
import com.github.bkmbigo.solitaire.game.solitaire.moves.dsl.move
import com.github.bkmbigo.solitaire.game.solitaire.moves.dsl.to
import com.github.bkmbigo.solitaire.models.core.CardRank
import com.github.bkmbigo.solitaire.models.solitaire.TableStackEntry

object SolitaireHintProvider : GameHintProvider<SolitaireGame, SolitaireGameMove> {
    override fun provideHints(game: SolitaireGame): List<SolitaireGameMove> {
        /* How the game provides hints:
        *   1. Find move from TableStack to Foundation.
        *   2. Find move from Deck to Foundation.
        *   3. Find move between table stacks (Move is only valid if it reveals a card or if the bottom-most card is not a King).
        *   4. Find move from Deck to table stack
        *   5. Find if card(s) can be drawn from the foundation to table stacks to help reveal cards
        *   6. Find if a card in the table can be moved to the foundation by moving cards above them on the stack. */

        val moves = mutableListOf<SolitaireGameMove>()

        // 1
        for (tableStackEntry in TableStackEntry.entries) {
            game.tableStack(tableStackEntry).lastCard?.let { card ->
                val move = card move MoveSource.FromTable(tableStackEntry) to MoveDestination.ToFoundation
                if (move.isValid(game)) {
                    moves.add(move)
                }
            }
        }

        // 2
        game.deckPositions.lastOrNull()?.let { lastDeckPosition ->
            game.deck.getOrNull(game.deck.size - lastDeckPosition)?.let { card ->
                val move = card move MoveSource.FromDeck(game.deck.size - lastDeckPosition) to MoveDestination.ToFoundation
                if (move.isValid(game)) {
                    moves.add(move)
                }
            }
        }

        // 3
        moves.addAll(game.findTableStackPossibleMoves())

        // 4
        moves.addAll(game.findDeckToTableStackMoves())

        // 5
        if (moves.isEmpty()) {
            moves.addAll(game.findFoundationToTableStackMoves())
        }

        // 6
        moves.addAll(SolitaireAdvancedTableStackToFoundationHintProvider(game))

        return moves
    }

    /** Finds all possible moves from Deck To Table */
    private fun SolitaireGame.findDeckToTableStackMoves(): List<SolitaireGameMove> {
        val moves = mutableListOf<SolitaireGameMove>()

        TableStackEntry.entries.forEach { tableStackEntry ->
            deckPositions.lastOrNull()?.let { lastDeckPosition ->
                deck.getOrNull(deck.size - lastDeckPosition)?.let { card ->
                    val move =
                        card move MoveSource.FromDeck(deck.size - lastDeckPosition) to MoveDestination.ToTable(
                            tableStackEntry
                        )
                    if (move.isValid(this)) {
                        moves.add(move)
                    }
                }
            }
        }

        return moves
    }

    /** Find all possible TableStack to TableStack moves */
    private fun SolitaireGame.findTableStackPossibleMoves(): List<SolitaireGameMove> {
        val moves = mutableListOf<SolitaireGameMove>()

        tableStacks.forEachIndexed { currentIndex, currentTableStack ->
            tableStacks.forEachIndexed { targetIndex, _ ->
                if (currentIndex != targetIndex) {
                    val move = currentTableStack.revealedCards move TableStackEntry.entries[currentIndex] to
                            TableStackEntry.entries[targetIndex]
                    if (move.isValid(this) && currentTableStack.revealedCards.isNotEmpty() && !(currentTableStack.hiddenCards.isEmpty() && currentTableStack.firstRevealedCard?.rank == CardRank.KING)) {
                        moves.add(move)
                    }
                }
            }
        }

        return moves
    }

    /** Find if card(s) can be drawn from the foundation to table stacks to help reveal cards */
    internal fun SolitaireGame.findFoundationToTableStackMoves(): List<SolitaireGameMove> =
        SolitaireFoundationToTableStackHintProvider(this)


}

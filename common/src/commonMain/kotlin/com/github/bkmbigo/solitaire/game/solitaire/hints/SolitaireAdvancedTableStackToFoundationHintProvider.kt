package com.github.bkmbigo.solitaire.game.solitaire.hints

import com.github.bkmbigo.solitaire.game.solitaire.SolitaireGame
import com.github.bkmbigo.solitaire.game.solitaire.moves.MoveDestination
import com.github.bkmbigo.solitaire.game.solitaire.moves.SolitaireUserMove
import com.github.bkmbigo.solitaire.game.solitaire.moves.dsl.move
import com.github.bkmbigo.solitaire.game.solitaire.moves.dsl.to
import com.github.bkmbigo.solitaire.models.core.Card
import com.github.bkmbigo.solitaire.models.core.CardRank
import com.github.bkmbigo.solitaire.models.core.CardRank.*
import com.github.bkmbigo.solitaire.models.core.CardSuite.*
import com.github.bkmbigo.solitaire.models.core.utils.of
import com.github.bkmbigo.solitaire.models.solitaire.TableStackEntry

/* Finds moves where a TableStack can be moved to facilitate a move to the Foundation */
object SolitaireAdvancedTableStackToFoundationHintProvider {

    operator fun invoke(
        game: SolitaireGame
    ): List<SolitaireUserMove> {
        /* Find the next cards to be placed on the foundation.
        * Check through revealed TableStacks for the nextCards.
        * If a match is found, ensure that:
        *       - There are cards on top.
        * Find a move for the card(s) on top of the marched card (A tableStack - tableStack move). */

        with(game) {
            val moves = mutableListOf<SolitaireUserMove>()

            val nextCards = findFoundationNextCards(game)

            TableStackEntry.entries.forEach { currentTableStackEntry ->
                val matches = tableStack(currentTableStackEntry).filter { it in nextCards }
                matches.forEach { match ->
                    val currentTableStack = tableStack(currentTableStackEntry)
                    if (currentTableStack.lastCard != match) {
                        TableStackEntry.entries.forEach { targetTableStackEntry ->
                            if (currentTableStackEntry != targetTableStackEntry) {
                                val matchIndex = currentTableStack.revealedCards.indexOf(match)
                                val cardsToMove = currentTableStack.revealedCards.filterIndexed { index, card ->
                                    matchIndex < index
                                }
                                val move = cardsToMove move currentTableStackEntry to targetTableStackEntry
                                if (move.isValid(game)) {
                                    moves.add(move)
                                    // moves.add(match move currentTableStackEntry to MoveDestination.ToFoundation)
                                }
                            }
                        }
                    }
                }
            }

            return moves
        }
    }

    internal fun findFoundationNextCards(game: SolitaireGame): List<Card> {
        with(game) {
            return listOfNotNull(
                getNextCard(spadeFoundationStack)?.of(SPADE),
                getNextCard(cloverFoundationStack)?.of(CLOVER),
                getNextCard(heartsFoundationStack)?.of(HEARTS),
                getNextCard(diamondFoundationStack)?.of(DIAMOND),
            )
        }
    }

    internal fun getNextCard(
        foundationCards: List<Card>
    ): CardRank? {
        val lastCard = foundationCards.lastOrNull()
        return when {
            lastCard == null -> ACE
            lastCard.rank == KING -> null
            else -> CardRank.entries[lastCard.rank.ordinal + 1]
        }
    }

}

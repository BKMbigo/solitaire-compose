package com.github.bkmbigo.solitaire.game.solitaire.logic

import com.github.bkmbigo.solitaire.game.solitaire.SolitaireGame
import com.github.bkmbigo.solitaire.game.solitaire.configuration.SolitaireCardsPerDeal
import com.github.bkmbigo.solitaire.game.solitaire.moves.MoveDestination
import com.github.bkmbigo.solitaire.game.solitaire.moves.MoveSource
import com.github.bkmbigo.solitaire.game.solitaire.moves.SolitaireGameMove
import com.github.bkmbigo.solitaire.game.solitaire.moves.SolitaireUserMove
import com.github.bkmbigo.solitaire.game.solitaire.moves.dsl.moveSolitaireInstantlyFrom
import com.github.bkmbigo.solitaire.game.solitaire.moves.dsl.moveTo
import com.github.bkmbigo.solitaire.game.utils.isImmediatelyLowerTo
import com.github.bkmbigo.solitaire.game.utils.isImmediatelyUpperTo
import com.github.bkmbigo.solitaire.models.core.CardRank
import com.github.bkmbigo.solitaire.models.solitaire.TableStackEntry

object SolitaireDeckDrawDeterminer {

    /**  */
    // Some of the moves returned by the method are invalid
    fun SolitaireGame.findRemainingDeckMoves(): List<SolitaireGameMove> {
        val moves = mutableListOf<SolitaireUserMove>()

        when (configuration.cardsPerDeal) {
            SolitaireCardsPerDeal.ONE -> {
                (1.. deck.size).forEach { index ->
                    moves.addAll(findMovesForCardAtIndex(index))
                }
            }
            SolitaireCardsPerDeal.THREE -> {
                /* If deckPositions isEmpty or the last index on deckPositions is a multiple of three:
                *       Find moves for all indexes that are multiples of three
                *       Find moves for the last card
                * Otherwise:
                *       Carry out two runs,
                *           Continuously add 3 to the current last number until >= deck.size
                *               FindAllPossibleMovesForCardAtIndex
                *           For Possible moves for multiples of 3 */
                if (deckPositions.isEmpty() || deckPositions.last() % 3 == 0) {
                    val div = deck.size / 3
                    (0 .. div).map { it * 3 }.forEach { index ->
                        moves.addAll(findMovesForCardAtIndex(index))
                    }
                    moves.addAll(findMovesForCardAtIndex(deck.size))
                } else {
                    var inc = deckPositions.last()

                    while(inc <= deck.size) {
                        moves.addAll(findMovesForCardAtIndex(inc))
                        inc += 3
                    }

                    val div = deck.size / 3
                    (0 .. div).map { it * 3 }.forEach { index ->
                        moves.addAll(findMovesForCardAtIndex(index))
                    }
                    moves.addAll(findMovesForCardAtIndex(deck.size))
                }
            }
        }

        return moves
    }

    private fun SolitaireGame.findMovesForCardAtIndex(index: Int): List<SolitaireUserMove> {
        val moves = mutableListOf<SolitaireUserMove>()

        deck.getOrNull(deck.size - index)?.let { card ->
            val move =
                card moveSolitaireInstantlyFrom MoveSource.FromDeck(deck.size - index) moveTo MoveDestination.ToFoundation
            if (move.verifyDeckMove(this)) {
                moves.add(move)
            }
        }

        TableStackEntry.entries.forEach { tableStackEntry ->
                deck.getOrNull(deck.size - index)?.let { card ->
                    val move =
                        card moveSolitaireInstantlyFrom MoveSource.FromDeck(deck.size - index) moveTo tableStackEntry
                    if (move.verifyDeckMove(this)) {
                        moves.add(move)
                    }
                }

        }

        return moves
    }

    private fun SolitaireUserMove.CardMove.verifyDeckMove(game: SolitaireGame): Boolean {
        when (to) {
            is MoveDestination.ToFoundation -> {
                if (cards.size != 1) return false
                val card = cards.first()
                val foundationStack = game.foundationStack(card.suite)

                if (foundationStack.isEmpty()) {
                    if (card.rank != CardRank.ACE) return false
                } else {
                    val lastCard = foundationStack.last()
                    if (!card.rank.isImmediatelyUpperTo(lastCard.rank)) return false
                }
            }

            is MoveDestination.ToTable -> {
                val tableStack = game.tableStack(to.tableStackEntry)

                if (tableStack.isEmpty()) {
                    if (cards.first().rank != CardRank.KING) return false
                } else {
                    val firstCardInSelection = cards.first()
                    val lastCardInStack = tableStack.lastCard
                    if (lastCardInStack == null ||
                        firstCardInSelection.color == lastCardInStack.color ||
                        !firstCardInSelection.rank.isImmediatelyLowerTo(lastCardInStack.rank)
                    ) return false
                }

                if (!cards.isValidTableStack()) return false
            }

        }

        return true
    }
}

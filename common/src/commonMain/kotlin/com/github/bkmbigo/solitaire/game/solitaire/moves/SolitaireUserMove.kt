package com.github.bkmbigo.solitaire.game.solitaire.moves

import com.github.bkmbigo.solitaire.game.solitaire.SolitaireGame
import com.github.bkmbigo.solitaire.game.solitaire.logic.isValidTableStack
import com.github.bkmbigo.solitaire.game.solitaire.moves.dsl.move
import com.github.bkmbigo.solitaire.game.solitaire.moves.dsl.to
import com.github.bkmbigo.solitaire.game.utils.isImmediatelyLowerTo
import com.github.bkmbigo.solitaire.game.utils.isImmediatelyUpperTo
import com.github.bkmbigo.solitaire.models.core.Card
import com.github.bkmbigo.solitaire.models.core.CardRank

sealed class SolitaireUserMove: SolitaireGameMove() {
    data object Deal : SolitaireUserMove() {
        override fun isValid(game: SolitaireGame): Boolean = true
        override fun reversed(): SolitaireGameMove = Undeal
    }

    data class CardMove(
        val cards: List<Card>,
        val from: MoveSource,
        val to: MoveDestination
    ) : SolitaireUserMove() {
        constructor(
            card: Card,
            from: MoveSource,
            to: MoveDestination,
        ) : this(
            listOf(card),
            from,
            to
        )

        override fun isValid(game: SolitaireGame): Boolean {
            /*Checks whether the proposed move is valid
            *   1. Check if the ToDestination is capable of accepting the card
            *           i). if ToDestination is TableStack:
            *                   -   if table stack is empty:
            *                           - first card is a KING
            *                       else
            *                           - check if first card's in selection color does not match with last card's color
            *                           - check if first card in selection rank is just lower than last card (e.g 2 -> 3)
            *                   - check if cards is a valid table stack
            *           ii). if ToDestination is FoundationStack:
            *                   - check if number of cards is 1 (cannot place more than one card in foundation)
            *                   - check if suite matches the suite in the foundation stack      // Technically does not occur since game stores stacks according to suites
            *                   - check
            *                           - if foundation stack is empty:
            *                               - card is an ACE
            *                           - else
            *                               - card rank is just higher than last card in stack (e.g 3 -> 2)
            *   2. From Checks:                         // for validity
            *           -   check validity of from
            *           -   i). if from deck:
            *                       - check if only one card.
            *                       - check if deck contains card at index.
            *               ii). if from foundation:
            *                       - check if only one card.
            *                       - check if card is top of stack.
            *               iii). if from table:
            *                       - check all cards belong to revealed cards.
            *                       - check if stack is valid.
            *                       - check if cards are on top of stack (No card is on top of cards)
            *   3. Check if the game is valid after the move    // The check can be eliminated as long as game creation is checked for validity
            *           - Cards in the game are exactly 54
            *           - Foundation stacks are valid
            *           - Table stacks are valid */

            if (cards.isEmpty()) return false // cards cannot be empty

            when (to) {
                is MoveDestination.ToTable -> {
                    val tableStack = game.tableStack(to.tableStackEntry)

                    if (tableStack.isEmpty()) {
                        if (cards.first().rank != CardRank.KING) return false
                    } else {
                        val firstCardInSelection = cards.first()
                        val lastCardInStack = tableStack.lastCard!!
                        if (firstCardInSelection.color == lastCardInStack.color ||
                            !firstCardInSelection.rank.isImmediatelyLowerTo(lastCardInStack.rank)
                        ) return false
                    }

                    if (!cards.isValidTableStack()) return false
                }

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
            }

            when (from) {
                is MoveSource.FromDeck -> {
                    if (cards.size != 1) return false

                    if (game.deck[from.index] != cards.first())
                        return false
                }

                MoveSource.FromFoundation -> {
                    if (cards.size != 1) return false

                    val card = cards.first()
                    if (game.foundationStack(card.suite).last() != card) return false
                }

                is MoveSource.FromTable -> {
                    if (!game.tableStack(from.tableStackEntry).revealedCards.takeLast(cards.size)
                            .containsAll(cards)
                    ) return false

                    if (!cards.isValidTableStack()) return false
                }
            }

            return true
        }

        override fun reversed(): SolitaireGameMove? {
            when (from) {
                is MoveSource.FromDeck -> {
                    val returnToDeckSource = when (to) {
                        MoveDestination.ToFoundation -> ReturnToDeckSource.FromFoundation
                        is MoveDestination.ToTable -> ReturnToDeckSource.FromTable(to.tableStackEntry)
                    }

                    if (cards.size != 1) {
                        return null
                    }

                    return ReturnToDeck(
                        card = cards.first(),
                        from = returnToDeckSource,
                        index = from.index
                    )
                }
                else -> {
                    val reverseFrom = when (to) {
                        MoveDestination.ToFoundation -> MoveSource.FromFoundation
                        is MoveDestination.ToTable -> MoveSource.FromTable(to.tableStackEntry)
                    }

                    val reverseTo = when (from) {
                        is MoveSource.FromDeck -> { return null }
                        MoveSource.FromFoundation -> MoveDestination.ToFoundation
                        is MoveSource.FromTable -> MoveDestination.ToTable(from.tableStackEntry)
                    }

                    return cards move reverseFrom to reverseTo
                }
            }
        }


    }
}

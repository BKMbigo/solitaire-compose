package com.github.bkmbigo.solitaire.game.solitaire

import com.github.bkmbigo.solitaire.game.Game
import com.github.bkmbigo.solitaire.game.solitaire.logic.allIndexed
import com.github.bkmbigo.solitaire.game.solitaire.logic.isFullyValid
import com.github.bkmbigo.solitaire.game.solitaire.logic.isValidTableStack
import com.github.bkmbigo.solitaire.game.solitaire.moves.MoveDestination
import com.github.bkmbigo.solitaire.game.solitaire.moves.MoveSource
import com.github.bkmbigo.solitaire.game.solitaire.moves.SolitaireGameMove
import com.github.bkmbigo.solitaire.game.utils.isImmediatelyLowerTo
import com.github.bkmbigo.solitaire.models.core.Card
import com.github.bkmbigo.solitaire.models.core.CardSuite
import com.github.bkmbigo.solitaire.models.solitaire.utils.TableStackEntry

data class SolitaireGame(
    val deck: List<Card>,

    val spadeFoundationStack: List<Card>,
    val cloverFoundationStack: List<Card>,
    val heartsFoundationStack: List<Card>,
    val diamondFoundationStack: List<Card>,

    val firstTableStackState: TableStack,
    val secondTableStackState: TableStack,
    val thirdTableStackState: TableStack,
    val fourthTableStackState: TableStack,
    val fifthTableStackState: TableStack,
    val sixthTableStackState: TableStack,
    val seventhTableStackState: TableStack,
    val eighthTableStackState: TableStack
) : Game<SolitaireGameMove> {

    fun tableStack(entry: TableStackEntry) = when (entry) {
        TableStackEntry.ONE -> firstTableStackState
        TableStackEntry.TWO -> secondTableStackState
        TableStackEntry.THREE -> thirdTableStackState
        TableStackEntry.FOUR -> fourthTableStackState
        TableStackEntry.FIVE -> fifthTableStackState
        TableStackEntry.SIX -> sixthTableStackState
        TableStackEntry.SEVEN -> seventhTableStackState
        TableStackEntry.EIGHT -> eighthTableStackState
    }

    fun foundationStack(suite: CardSuite) = when (suite) {
        CardSuite.SPADE -> spadeFoundationStack
        CardSuite.CLOVER -> cloverFoundationStack
        CardSuite.HEARTS -> heartsFoundationStack
        CardSuite.DIAMOND -> diamondFoundationStack
    }

    val tableStacks
        get() = listOf(
            firstTableStackState,
            secondTableStackState,
            thirdTableStackState,
            fourthTableStackState,
            fifthTableStackState,
            sixthTableStackState,
            seventhTableStackState,
            eighthTableStackState
        )

    val foundationStacks
        get() = listOf(
            spadeFoundationStack,
            cloverFoundationStack,
            heartsFoundationStack,
            diamondFoundationStack
        )

    companion object {
        val ALL_CARDS_IN_DECK = SolitaireGame(
            deck = Card.values().toList(),
            spadeFoundationStack = emptyList(),
            cloverFoundationStack = emptyList(),
            heartsFoundationStack = emptyList(),
            diamondFoundationStack = emptyList(),
            firstTableStackState = TableStack.EMPTY,
            secondTableStackState = TableStack.EMPTY,
            thirdTableStackState = TableStack.EMPTY,
            fourthTableStackState = TableStack.EMPTY,
            fifthTableStackState = TableStack.EMPTY,
            sixthTableStackState = TableStack.EMPTY,
            seventhTableStackState = TableStack.EMPTY,
            eighthTableStackState = TableStack.EMPTY
        )
    }

    override fun play(move: SolitaireGameMove): SolitaireGame {
        var newGame = this
        if (!move.isValid()) return newGame


        when (move) {
            SolitaireGameMove.Deal -> {
                return newGame
            }

            is SolitaireGameMove.CardMove -> {


                when (move.from) {
                    MoveSource.FromDeck -> {
                        newGame = newGame.copy(
                            deck = newGame.deck.toMutableList().apply { remove(move.cards.first()) }
                        )
                    }

                    MoveSource.FromFoundation -> {
                        val card = move.cards.first()
                        newGame = newGame.withFoundationStack(
                            suite = card.suite,
                            cards = foundationStack(card.suite).toMutableList().apply {
                                remove(card)
                            }
                        )
                    }

                    is MoveSource.FromTable -> {
                        newGame = newGame.withTableStack(
                            move.from.tableStackEntry,
                            newGame.tableStack(move.from.tableStackEntry)
                                .withRemovedCards(move.cards)
                        )
                    }
                }

                /* Change to: (typically add card to destination) */
                when (move.to) {
                    MoveDestination.ToFoundation -> {
                        val card = move.cards.first()

                        newGame = newGame.withFoundationStack(
                            card.suite,
                            newGame.foundationStack(card.suite).toMutableList().apply { add(card) }
                        )
                    }

                    is MoveDestination.ToTable -> {
                        newGame = newGame.withTableStack(
                            move.to.tableStackEntry,
                            newGame.tableStack(move.to.tableStackEntry).withAddedCards(move.cards)
                        )
                    }
                }
            }
        }

        return if (newGame.isValid()) newGame else this         // Todo: Check will be removed as validation will be moved to moves instead of entire games
    }

    override fun isValid(): Boolean {
        /*  A solitaire game is valid iff:
        *       1. Card check:
        *           - There are only 52 cards in the entire game (hidden and revealed)
        *           - There are 13 cards of each suite.
        *           - There are 4 cards in each rank.
        *       2. All foundation stacks are valid.
        *           - contain the corresponding suite only
        *           - cards are in incremental order from A -> K
        *       3. All table stacks are valid.
        *           - colors are interchanging in subsequent cards.
        *           - ranks are in decrementing order K -> A*/

        val allCards = listOf(deck, foundationStacks.flatten(), tableStacks.flatten()).flatten()
        if (allCards.size != 52) return false // 1.(i)
        if (!allCards.groupBy { it.suite }
                .all { it.value.size == 13 } || !allCards.groupBy { it.rank }
                .all { it.value.size == 4 }) return false // 1.(ii) & 1.(iii)

        if (!foundationStacks.all { it.isValidFoundationStack() }) return false

        return tableStacks.all { it.revealedCards.isValidTableStack() }
    }

    override fun isWon(): Boolean {
        // Cannot win a game with a card on the deck
        if (!deckIsEmpty()) return false

        /* Can win if:
        *   1. The player has arranged all cards on their respective foundation stack:
        *           i). No card is present on the table stacks.
        *           ii). All foundation stacks are filled (Have 13 cards).  (Check can be skipped since no cards are present on the deck and the table)
        *           iii). All foundation stacks are valid (They are sorted from A - K).
        *   2. The player has managed to arrange 4 table stacks from K - A:
        *           i). No card is present on the foundation stacks.
        *           ii). There are only 4 table stacks with 13 cards.
        *           iii). The arrangement of table stacks is fully valid:
        *                       i). The stack does not have a hidden card.
        *                       ii). The rank of the cards decrements from top to bottom.
        *                       iii). The color of the suite of the cards changes from a card to the subsequent card.
        *
        *   - Finally, check if game is valid. */

        if (tableStacksAreEmpty()) {
            if (!getNonEmptyFoundationStacks().all { stack -> stack.size == 13 && stack.isValidFoundationStack() }) return false
        } else if (foundationStacksAreEmpty()) {
            val nonEmptyStacks = getNonEmptyTableStacks()
            if (nonEmptyStacks.size != 4) return false // 2.(ii)
            if (!nonEmptyStacks.all { it.size == 13 && it.isFullyValid()  /* 2.(iii)*/ }) return false
        } else {
            return false
        }

        return isValid()
    }

    override fun isDrawn(): Boolean {
        return false
        // TODO("Not yet implemented")
    }

    fun withFoundationStack(suite: CardSuite, cards: List<Card>): SolitaireGame =
        when (suite) {
            CardSuite.SPADE -> copy(spadeFoundationStack = cards)
            CardSuite.CLOVER -> copy(cloverFoundationStack = cards)
            CardSuite.HEARTS -> copy(heartsFoundationStack = cards)
            CardSuite.DIAMOND -> copy(diamondFoundationStack = cards)
        }

    fun withTableStack(tableStackEntry: TableStackEntry, stack: TableStack): SolitaireGame =
        when (tableStackEntry) {
            TableStackEntry.ONE -> copy(firstTableStackState = stack)
            TableStackEntry.TWO -> copy(secondTableStackState = stack)
            TableStackEntry.THREE -> copy(thirdTableStackState = stack)
            TableStackEntry.FOUR -> copy(fourthTableStackState = stack)
            TableStackEntry.FIVE -> copy(fifthTableStackState = stack)
            TableStackEntry.SIX -> copy(sixthTableStackState = stack)
            TableStackEntry.SEVEN -> copy(seventhTableStackState = stack)
            TableStackEntry.EIGHT -> copy(eighthTableStackState = stack)
        }

    private fun deckIsEmpty(): Boolean = deck.isEmpty()

    private fun tableStacksAreEmpty(): Boolean =
        firstTableStackState.isEmpty() &&
                secondTableStackState.isEmpty() &&
                thirdTableStackState.isEmpty() &&
                fourthTableStackState.isEmpty() &&
                fifthTableStackState.isEmpty() &&
                sixthTableStackState.isEmpty() &&
                seventhTableStackState.isEmpty() &&
                eighthTableStackState.isEmpty()

    private fun foundationStacksAreEmpty(): Boolean =
        spadeFoundationStack.isEmpty() &&
                cloverFoundationStack.isEmpty() &&
                heartsFoundationStack.isEmpty() &&
                diamondFoundationStack.isEmpty()

    private fun emptyTableStacks(): Int = count(
        firstTableStackState.isEmpty(),
        secondTableStackState.isEmpty(),
        thirdTableStackState.isEmpty(),
        fourthTableStackState.isEmpty(),
        fifthTableStackState.isEmpty(),
        sixthTableStackState.isEmpty(),
        seventhTableStackState.isEmpty(),
        eighthTableStackState.isEmpty()
    )

    private fun emptyFoundationStacks(): Int = count(
        spadeFoundationStack.isEmpty(),
        cloverFoundationStack.isEmpty(),
        heartsFoundationStack.isEmpty(),
        diamondFoundationStack.isEmpty()
    )

    private fun getEmptyFoundationStacks() = foundationStacks.filter { it.isEmpty() }

    private fun getNonEmptyFoundationStacks() =
        foundationStacks.filter { it.isNotEmpty() }

    private fun getEmptyTableStacks() = tableStacks.filter { it.isEmpty() }

    private fun getNonEmptyTableStacks() = tableStacks.filter { it.isNotEmpty() }

    private fun nonEmptyTableStacks(): Int = 8 - emptyTableStacks()
    private fun nonEmptyFoundationStacks(): Int = 8 - emptyFoundationStacks()

    private fun allFoundationStacksAreFilled(): Boolean =
        spadeFoundationStack.size == 13 &&
                cloverFoundationStack.size == 13 &&
                heartsFoundationStack.size == 13 &&
                diamondFoundationStack.size == 13

    /** Check if foundation stack is valid
     * <p>  - Cards have a similar suite
     *      - Cards increment rank from A -> K </p>*/
    private fun List<Card>.isValidFoundationStack(): Boolean = allIndexed { index, card ->
        if (index == 0) {
            true
        } else {
            val suite = first().suite
            val previousCard = get(index - 1)
            card.suite == suite && previousCard.rank.isImmediatelyLowerTo(card.rank)
        }
    }

    private fun count(
        vararg checks: Boolean
    ): Int = checks.count { it }
}
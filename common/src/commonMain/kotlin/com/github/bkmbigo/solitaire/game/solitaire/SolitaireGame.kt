package com.github.bkmbigo.solitaire.game.solitaire

import com.github.bkmbigo.solitaire.game.Game
import com.github.bkmbigo.solitaire.game.solitaire.configuration.SolitaireCardsPerDeal
import com.github.bkmbigo.solitaire.game.solitaire.configuration.SolitaireGameConfiguration
import com.github.bkmbigo.solitaire.game.solitaire.hints.SolitaireAdvancedTableStackToFoundationHintProvider
import com.github.bkmbigo.solitaire.game.solitaire.hints.SolitaireHintProvider.findDeckToFoundationMoves
import com.github.bkmbigo.solitaire.game.solitaire.hints.SolitaireHintProvider.findDeckToTableStackMoves
import com.github.bkmbigo.solitaire.game.solitaire.hints.SolitaireHintProvider.findFoundationToTableStackMoves
import com.github.bkmbigo.solitaire.game.solitaire.hints.SolitaireHintProvider.findTableStackPossibleMoves
import com.github.bkmbigo.solitaire.game.solitaire.hints.SolitaireHintProvider.findTableStackToFoundationMoves
import com.github.bkmbigo.solitaire.game.solitaire.logic.SolitaireDeckDrawDeterminer.findRemainingDeckMoves
import com.github.bkmbigo.solitaire.game.solitaire.logic.allIndexed
import com.github.bkmbigo.solitaire.game.solitaire.logic.isFullyValid
import com.github.bkmbigo.solitaire.game.solitaire.logic.isValidTableStack
import com.github.bkmbigo.solitaire.game.solitaire.moves.*
import com.github.bkmbigo.solitaire.game.solitaire.utils.SolitaireDealOffset
import com.github.bkmbigo.solitaire.game.utils.isImmediatelyLowerTo
import com.github.bkmbigo.solitaire.models.core.Card
import com.github.bkmbigo.solitaire.models.core.CardSuite
import com.github.bkmbigo.solitaire.models.solitaire.TableStackEntry

/** A solitaire game */
data class SolitaireGame(
    val configuration: SolitaireGameConfiguration,

    /** The index of cards in the current deck. Please Note that the current card in display is actually deckSize - deckPosition */
    val deckPositions: List<Int> = emptyList(),

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
    val seventhTableStackState: TableStack
) : Game<SolitaireGame, SolitaireGameMove> {

    fun tableStack(entry: TableStackEntry) = when (entry) {
        TableStackEntry.ONE -> firstTableStackState
        TableStackEntry.TWO -> secondTableStackState
        TableStackEntry.THREE -> thirdTableStackState
        TableStackEntry.FOUR -> fourthTableStackState
        TableStackEntry.FIVE -> fifthTableStackState
        TableStackEntry.SIX -> sixthTableStackState
        TableStackEntry.SEVEN -> seventhTableStackState
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
            seventhTableStackState
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
            configuration = SolitaireGameConfiguration(SolitaireCardsPerDeal.ONE),
            deck = Card.entries.toList(),
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
            seventhTableStackState = TableStack.EMPTY
        )

        val EMPTY_GAME = SolitaireGame(
            configuration = SolitaireGameConfiguration(SolitaireCardsPerDeal.ONE),
            deck = emptyList(),
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
            seventhTableStackState = TableStack.EMPTY
        )
    }

    /* Todo: Accept a list of moves to enable optimizations. */
    override fun play(move: SolitaireGameMove): SolitaireGame {
        var newGame = this

        when (move) {
            is SolitaireUserMove.Deal -> {
                when (configuration.cardsPerDeal) {
                    SolitaireCardsPerDeal.ONE -> {
                        val isNotThrough = if (newGame.deckPositions.isNotEmpty()) {
                            newGame.deckPositions.last() < newGame.deck.size
                        } else newGame.deck.isNotEmpty()

                        val newDeckPositions = if (!isNotThrough) {
                            emptyList()
                        } else {
                            val size = newGame.deckPositions.size

                            val lastElement = newGame.deckPositions.getOrNull(size - 1)
                            val secondLastElement = newGame.deckPositions.getOrNull(size - 2)

                            val list = mutableListOf<Int>()

                            secondLastElement?.let {
                                list.add(it)
                            }
                            lastElement?.let {
                                list.add(it)
                            }
                            if (lastElement != null) {
                                list.add(lastElement + 1)
                            } else {
                                // The deckPositions list is empty, initialize it with the first card
                                list.add(1)
                            }

                            list
                        }

                        newGame = newGame.copy(
                            deckPositions = newDeckPositions
                        )
                    }

                    SolitaireCardsPerDeal.THREE -> {
                        // Case 1: The deck is empty
                        if (newGame.deckPositions.isEmpty()) {
                            // Handle instance where the deck has less than 3 cards
                            newGame = newGame.copy(
                                deckPositions = when (newGame.deck.size) {
                                    0 -> emptyList()
                                    1 -> listOf(1)
                                    2 -> listOf(1, 2)
                                    else -> listOf(1, 2, 3)
                                }
                            )
                        } else {
                            /* There are three instances to handle:
                            * 1. There are no remaining unrevealed cards -> produce an emptyList()
                            * 2. There are remaining cards and their number is greater than or equal to three
                            *       For instance, previous deckPositions is (1, 2, 3) and there are 24 cards on deck -> produce (4, 5, 6)
                            * 3. There are remaining cards, but they are less than 3
                            *       In such instances, the last draw should contain three cards, including already shown cards
                            *           For instance, the previous deckPositions has (19, 20, 21) and the deckSize is 22, the next iteration should be (20, 21, 22)*/

                            val last = newGame.deckPositions.last()

                            val isThrough = last >= newGame.deck.size // There are no remaining cards
                            if (isThrough) {
                                newGame = newGame.copy(
                                    deckPositions = emptyList()
                                )
                            } else {
                                /* Check if there are enough remaining cards to fill the deck */
                                val canFill = last + 3 <= newGame.deck.size

                                if (canFill) {
                                    newGame = newGame.copy(
                                        deckPositions = listOf(last + 1, last + 2, last + 3)
                                    )
                                } else {
                                    when (newGame.deck.size) {
                                        last + 2 -> {
                                            newGame = newGame.copy(
                                                deckPositions = listOf(last, last + 1, last + 2)
                                            )
                                        }

                                        last + 1 -> {
                                            newGame = newGame.copy(
                                                deckPositions = listOf(last - 1, last, last + 1)
                                            )
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            }

            is SolitaireUserMove.CardMove -> {

                when (move.from) {
                    is MoveSource.FromDeck -> {
                        newGame = newGame.copy(
                            deck = newGame.deck.toMutableList().apply { remove(move.cards.first()) },
                            deckPositions = newGame.deckPositions.toMutableList().apply {
                                if (isNotEmpty()) {
                                    if (size > 1) {
                                        removeLast()
                                    } else {
                                        val position = first()
                                        removeLast()
                                        if (position > 1) {
                                            // If possible, replace the last element
                                            add(position - 1)
                                        }
                                    }
                                }
                            }
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

            is SolitaireGameMove.HideCard -> {
                newGame = newGame.withTableStack(
                    move.tableStackEntry,
                    newGame.tableStack(move.tableStackEntry).withHideCard()
                )
            }

            is SolitaireGameMove.RevealCard -> {
                newGame = newGame.withTableStack(
                    move.tableStackEntry,
                    newGame.tableStack(move.tableStackEntry).withRevealCard()
                )
            }

            is SolitaireGameMove.ReturnToDeck -> {
                val size = newGame.deckPositions.size

                val newDeckPositions = mutableListOf<Int>()

                val lastElement = newGame.deckPositions.getOrNull(size - 1)
                val secondLastElement = newGame.deckPositions.getOrNull(size - 2)

                secondLastElement?.let { newDeckPositions.add(it) }
                lastElement?.let { newDeckPositions.add(it) }

                // Add the new index
                if (lastElement != null) {
                    newDeckPositions.add(lastElement + 1)
                } else {
                    newDeckPositions.add(1)
                }

                newGame = newGame.copy(
                    deck = deck.toMutableList().apply {
                        add(move.index, move.card)
                    },
                    deckPositions = newDeckPositions
                )

                /* Remove card from source: */
                when (move.from) {
                    ReturnToDeckSource.FromFoundation -> {
                        newGame = newGame.withFoundationStack(
                            suite = move.card.suite,
                            cards = foundationStack(move.card.suite).toMutableList().apply {
                                remove(move.card)
                            }
                        )
                    }

                    is ReturnToDeckSource.FromTable -> {
                        newGame = newGame.withTableStack(
                            move.from.tableStackEntry,
                            newGame.tableStack(move.from.tableStackEntry)
                                .withRemovedCard(move.card)
                        )
                    }
                }
            }

            is SolitaireGameMove.Undeal -> {
                /* when deckPositions is empty, just show the last cards (maximum of three) */
                val newDeckPositions = if (newGame.deckPositions.isEmpty()) {
                    when (val size = newGame.deck.size) {
                        0 -> emptyList()
                        1 -> listOf(1)
                        2 -> listOf(1, 2)
                        else -> listOf(size - 2, size - 1, size)
                    }
                } else {
                    when (configuration.cardsPerDeal) {
                        SolitaireCardsPerDeal.ONE -> {
                            when (val lastPosition = newGame.deckPositions.last()) {
                                0 -> emptyList()
                                1 -> emptyList()
                                2 -> listOf(1)
                                3 -> listOf(1, 2)
                                else -> listOf(lastPosition - 3, lastPosition - 2, lastPosition - 1)
                            }
                        }

                        SolitaireCardsPerDeal.THREE -> {
                            if (newGame.deckPositions.last() <= 3) {
                                // Where the list consists of numbers lower or equal to 3, return an empty list
                                emptyList()
                            } else {
                                val first = newGame.deckPositions.first()
                                val firstOffset = when (first % 3) {
                                    0 -> SolitaireDealOffset.ONE
                                    1 -> SolitaireDealOffset.NONE
                                    else -> SolitaireDealOffset.TWO
                                }

                                when(firstOffset) {
                                    SolitaireDealOffset.NONE -> when(move.offset) {
                                        SolitaireDealOffset.NONE -> listOf(first - 3, first - 2, first - 1)
                                        SolitaireDealOffset.ONE -> listOf(first - 1, first, first + 1)
                                        SolitaireDealOffset.TWO -> listOf(first - 2, first - 1, first)
                                    }
                                    SolitaireDealOffset.ONE -> when(move.offset) {
                                        SolitaireDealOffset.NONE -> listOf(first - 2, first - 1, first)
                                        SolitaireDealOffset.ONE -> listOf(first - 3, first - 2, first - 1)
                                        SolitaireDealOffset.TWO -> listOf(first - 1, first, first + 1)
                                    }
                                    SolitaireDealOffset.TWO -> when(move.offset) {
                                        SolitaireDealOffset.NONE -> listOf(first - 1, first, first + 1)
                                        SolitaireDealOffset.ONE -> listOf(first - 2, first - 1, first)
                                        SolitaireDealOffset.TWO -> listOf(first - 3, first - 2, first - 1)
                                    }
                                }
                            }
                        }
                    }
                }

                newGame = newGame.copy(deckPositions = newDeckPositions)
            }
        }

        /* This is purposefully made to evade tests. In theory, a play move should never invalidate a game.
        Todo: Check will be removed as validation will be moved to moves instead of entire games */

        return if (newGame.isValid())
            newGame
        else
            if (isValid())
                this
            else
                newGame
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
        *       3. All revealed table stacks are valid.
        *           - colors are interchanging in subsequent cards.
        *           - ranks are in decrementing order K -> A*/

        val allCards = listOf(deck, foundationStacks.flatten(), tableStacks.flatten()).flatten()
        if (allCards.size != 52) return false // 1.(i)
        if (
            !allCards.groupBy { it.suite }.all { it.value.size == 13 } ||
            !allCards.groupBy { it.rank }.all { it.value.size == 4 }
        ) return false // 1.(ii) & 1.(iii)

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
        if (findDeckToTableStackMoves().isNotEmpty()) return false
        if (findDeckToFoundationMoves().isNotEmpty()) return false
        if (findTableStackToFoundationMoves().isNotEmpty()) return false
        if (findTableStackPossibleMoves().isNotEmpty()) return false
        if (findFoundationToTableStackMoves().isNotEmpty()) return false
        if (SolitaireAdvancedTableStackToFoundationHintProvider(this).isNotEmpty()) return false
        if (findRemainingDeckMoves().isNotEmpty()) return false
        return true
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
        }

    fun withFoundationStack(fn: List<Card>.() -> List<Card>): SolitaireGame {
        val spades = spadeFoundationStack.fn()
        val clover = cloverFoundationStack.fn()
        val hearts = heartsFoundationStack.fn()
        val diamond = diamondFoundationStack.fn()

        return copy(
            spadeFoundationStack = spades,
            cloverFoundationStack = clover,
            heartsFoundationStack = hearts,
            diamondFoundationStack = diamond
        )
    }

    fun withTableStacks(fn: TableStack.() -> TableStack): SolitaireGame {
        val firstTableStack = firstTableStackState.fn()
        val secondTableStack = secondTableStackState.fn()
        val thirdTableStack = thirdTableStackState.fn()
        val fourthTableStack = fourthTableStackState.fn()
        val fifthTableStack = fifthTableStackState.fn()
        val sixthTableStack = sixthTableStackState.fn()
        val seventhTableStack = seventhTableStackState.fn()

        return copy(
            firstTableStackState = firstTableStack,
            secondTableStackState = secondTableStack,
            thirdTableStackState = thirdTableStack,
            fourthTableStackState = fourthTableStack,
            fifthTableStackState = fifthTableStack,
            sixthTableStackState = sixthTableStack,
            seventhTableStackState = seventhTableStack
        )

    }

    private fun deckIsEmpty(): Boolean = deck.isEmpty()

    private fun tableStacksAreEmpty(): Boolean =
        firstTableStackState.isEmpty() &&
                secondTableStackState.isEmpty() &&
                thirdTableStackState.isEmpty() &&
                fourthTableStackState.isEmpty() &&
                fifthTableStackState.isEmpty() &&
                sixthTableStackState.isEmpty() &&
                seventhTableStackState.isEmpty()

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
        seventhTableStackState.isEmpty()
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

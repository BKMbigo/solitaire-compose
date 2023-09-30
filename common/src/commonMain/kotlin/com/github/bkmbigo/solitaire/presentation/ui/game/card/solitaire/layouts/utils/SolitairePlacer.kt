package com.github.bkmbigo.solitaire.presentation.ui.game.card.solitaire.layouts.utils

import androidx.compose.runtime.Stable
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.IntOffset
import com.github.bkmbigo.solitaire.game.solitaire.moves.MoveDestination
import com.github.bkmbigo.solitaire.models.core.CardSuite
import com.github.bkmbigo.solitaire.models.solitaire.TableStackEntry
import com.github.bkmbigo.solitaire.presentation.ui.game.card.solitaire.layouts.SolitaireGameLayout
import kotlin.math.roundToInt

/**
 * This class performs two functions:
 * 1. Places cards on [SolitaireGameLayout]
 * 2. Generates a move destination for a move. */

// In the future, the class will be responsible for determining the size of cards in the game

/*
* The structure of the class is:
*   fun updatePlacementValues() -> Called to update the placement values
*   fun generateMoveDestination() --> generates the move destination
*
*   calculatePositionOf() functions: IntOffset
*       calculatePositionOfDeckCard()
*       calculatePositionOfFoundation()
*       calculatePositionOfTableStack()
*   place() functions
*       place()
*       placeDeckCards()
*       placeFoundationCards()
*       placeTableStacks()
* */

/* Additionally, there are other extension functions:
*   SolitaireMoveGenerator has the following functions:
*       processDeckMove
*       processFoundationMove
*       processTableStackMove
* */

@Stable
internal class SolitairePlacer {

    var cardHeight: Int = 0
        private set
    var cardWidth: Int = 0
        private set
    var gameHeight: Int = 0
        private set
    var gameWidth: Int = 0
        private set
    var deckSeparation: Int = 0
        private set
    var cardOnDeckSeparation: Int = 0
        private set
    var foundationSeparation: Int = 0
        private set
    var heightSpacing: Int = 0
        private set
    var minimalTableStackSeparation: Int = 0
        private set
    var optimalTableStackHeightSeparation: Int = 0
        private set


    private val deckWidth
        get() = cardWidth * 2 + deckSeparation + cardOnDeckSeparation * 2
    private val foundationWidth
        get() = cardWidth * 4 + foundationSeparation * 3

    private val foundationStartPosition: Int
        get() = if (deckWidth + foundationWidth > gameWidth) {
            // What happens when both the deck and foundation cannot fit in the game width?
            // Perhaps we can place them in a 2 * 2 grid?
            // TODO: Handle case where the deck and foundation cannot fit in the game width
            gameWidth - foundationWidth
        } else {
            // Both the deck and the foundation can fit in the game width
            gameWidth - foundationWidth
        }

    private val minimalTableStackWidth
        get() = cardWidth * 7 + minimalTableStackSeparation * 6

    private val tableStackXSeparation: Int
        get() = if (gameWidth > minimalTableStackWidth) {
            (gameWidth - cardWidth * 7) / 6
        } else {
            0
        }

    val individualTableStackYSeparation: Int
        get() = optimalTableStackHeightSeparation

    /** Updates the values used in placement of cards on the table stack.
     * @param cardHeight Height of a single card.
     * @param cardWidth Width of a single card.
     * @param gameHeight Height of the game.
     * @param gameWidth Width of the game.
     * @param deckSeparation Separation of hidden and revealed cards on deck.
     * @param cardOnDeckSeparation Separation of revealed cards on deck.
     * @param foundationSeparation Separation of foundations on the game.
     * @param heightSpacing Height separation between foundation and table stacks.
     * @param minimalTableStackSeparation Minimal allowed separation between table stacks.
     * @param optimalTableStackHeightSeparation Optimal height separation between cards on a stack.*/
    fun updatePlacementValues(
        cardHeight: Int,
        cardWidth: Int,
        gameHeight: Int,
        gameWidth: Int,
        deckSeparation: Int,
        cardOnDeckSeparation: Int,
        foundationSeparation: Int,
        heightSpacing: Int,
        minimalTableStackSeparation: Int,
        optimalTableStackHeightSeparation: Int
    ) {
        this.cardHeight = cardHeight
        this.cardWidth = cardWidth
        this.gameHeight = gameHeight
        this.gameWidth = gameWidth
        this.deckSeparation = deckSeparation
        this.cardOnDeckSeparation = cardOnDeckSeparation
        this.foundationSeparation = foundationSeparation
        this.heightSpacing = heightSpacing
        this.minimalTableStackSeparation = minimalTableStackSeparation
        this.optimalTableStackHeightSeparation = optimalTableStackHeightSeparation
    }

    /** Uses calculated drag gestures to generate a move destination based on the final position of the drag.
     * @param dragStart Start offset of the drag.
     * @param offsetX Drag offset towards the X
     * @param dragStart Drag offset towards Y. */
    fun generateMoveDestination(
        dragStart: IntOffset,
        offsetX: Float,
        offsetY: Float
    ): MoveDestination? {
        /* Destination can only be either to the foundation (In this case, I will consider the foundation in general(all suites)) or a specific table stack.
        *       i) Calculate final drag position
        *       ii) Check if is foundation:
        *               Check if x is between foundationStart..gameWidth
        *               Check if y is <= cardHeight
        *       iii) Check if it is a table stack:
        *               Check if y is >= cardHeight + heightSpacing
        *               Check if x matches any table stack
        * */
        val finalDragX = (dragStart.x + offsetX).roundToInt()
        val finalDragY = (dragStart.y + offsetY).roundToInt()

        val foundationTolerance = cardWidth * 3 / 4

        if (finalDragX in foundationStartPosition - foundationTolerance..gameWidth && finalDragY <= cardHeight) {
            return MoveDestination.ToFoundation
        }

        if (finalDragY >= cardHeight + heightSpacing) {

            val tolerance =
                if (cardWidth > foundationSeparation)
                    cardWidth / 2
                else
                    foundationSeparation / 2

            when (finalDragX) {
                in calculateTableStackXPosition(TableStackEntry.ONE) - tolerance..<calculateTableStackXPosition(
                    TableStackEntry.ONE
                ) + cardWidth -> {
                    return MoveDestination.ToTable(TableStackEntry.ONE)
                }

                in calculateTableStackXPosition(TableStackEntry.TWO) - tolerance..<calculateTableStackXPosition(
                    TableStackEntry.TWO
                ) + cardWidth -> {
                    return MoveDestination.ToTable(TableStackEntry.TWO)
                }

                in calculateTableStackXPosition(TableStackEntry.THREE) - tolerance..<calculateTableStackXPosition(
                    TableStackEntry.THREE
                ) + cardWidth -> {
                    return MoveDestination.ToTable(TableStackEntry.THREE)
                }

                in calculateTableStackXPosition(TableStackEntry.FOUR) - tolerance..<calculateTableStackXPosition(
                    TableStackEntry.FOUR
                ) + cardWidth -> {
                    return MoveDestination.ToTable(TableStackEntry.FOUR)
                }

                in calculateTableStackXPosition(TableStackEntry.FIVE) - tolerance..<calculateTableStackXPosition(
                    TableStackEntry.FIVE
                ) + cardWidth -> {
                    return MoveDestination.ToTable(TableStackEntry.FIVE)
                }

                in calculateTableStackXPosition(TableStackEntry.SIX) - tolerance..<calculateTableStackXPosition(
                    TableStackEntry.SIX
                ) + cardWidth -> {
                    return MoveDestination.ToTable(TableStackEntry.SIX)
                }

                in calculateTableStackXPosition(TableStackEntry.SEVEN) - tolerance..<calculateTableStackXPosition(
                    TableStackEntry.SEVEN
                ) + cardWidth -> {
                    return MoveDestination.ToTable(TableStackEntry.SEVEN)
                }
            }
        }

        return null
    }

    fun place(
        scope: Placeable.PlacementScope,
        placeable: Placeable,
        layoutId: SolitaireLayoutId
    ) {
        with(scope) {
            when (layoutId) {
                SolitaireLayoutId.EMPTY_DECK -> {
                    placeable.place(0, 0, 0.5f)
                }

                SolitaireLayoutId.DECK_OVERLAY -> {
                    placeable.place(0, 0, 1.5f)
                }
//            SolitaireLayoutId.DECK_CARD -> {}
                SolitaireLayoutId.EMPTY_FOUNDATION_SPADE -> {
                    placeable.place(calculateFoundationXPosition(CardSuite.SPADE), 0)
                }

                SolitaireLayoutId.EMPTY_FOUNDATION_CLOVER -> {
                    placeable.place(calculateFoundationXPosition(CardSuite.CLOVER), 0)
                }

                SolitaireLayoutId.EMPTY_FOUNDATION_HEARTS -> {
                    placeable.place(calculateFoundationXPosition(CardSuite.HEARTS), 0)
                }

                SolitaireLayoutId.EMPTY_FOUNDATION_DIAMOND -> {
                    placeable.place(calculateFoundationXPosition(CardSuite.DIAMOND), 0)
                }
//            SolitaireLayoutId.FOUNDATION_SPADE -> {}
//            SolitaireLayoutId.FOUNDATION_CLOVER -> {}
//            SolitaireLayoutId.FOUNDATION_HEARTS -> {}
//            SolitaireLayoutId.FOUNDATION_DIAMOND -> {}
//            SolitaireLayoutId.FIRST_TABLE_STACK -> {}
//            SolitaireLayoutId.SECOND_TABLE_STACK -> {}
//            SolitaireLayoutId.THIRD_TABLE_STACK -> {}
//            SolitaireLayoutId.FOURTH_TABLE_STACK -> {}
//            SolitaireLayoutId.FIFTH_TABLE_STACK -> {}
//            SolitaireLayoutId.SIXTH_TABLE_STACK -> {}
//            SolitaireLayoutId.SEVENTH_TABLE_STACK ->{}
                else -> {}
            }
        }
    }

    fun placeDeckCards(
        scope: Placeable.PlacementScope,
        placeables: List<Placeable>,
        deckPositions: List<Int>,
    ) {
        with(scope) {
            when (deckPositions.size) {
                0 -> {
                    placeables.forEach {
                        it.place(0, 0, 0.6f)
                    }
                }

                1 -> {
                    val cardIndex = deckPositions.first()

                    placeables.forEachIndexed { index, cardPlaceable ->
                        if (index == placeables.size - cardIndex) {
                            cardPlaceable.place(cardWidth + deckSeparation, 0)
                        } else {
                            cardPlaceable.place(0, 0, 0.6f)
                        }
                    }
                }

                2 -> {
                    val cardIndexes = deckPositions.map { placeables.size - it }

                    placeables.forEachIndexed { index, cardPlaceable ->
                        when (index) {
                            cardIndexes[0] -> {
                                cardPlaceable.place(cardWidth + deckSeparation, 0, 0.9f)
                            }

                            cardIndexes[1] -> {
                                cardPlaceable.place(cardWidth + deckSeparation + cardOnDeckSeparation, 0, 1f)
                            }

                            else -> {
                                cardPlaceable.place(0, 0, 0.6f)
                            }
                        }
                    }
                }

                else -> {
                    val cardIndexes = deckPositions.map { placeables.size - it }

                    placeables.forEachIndexed { index, cardPlaceable ->
                        when {
                            index == cardIndexes[0] -> {
                                cardPlaceable.place(cardWidth + deckSeparation, 0, 0.8f)
                            }

                            index == cardIndexes[1] -> {
                                cardPlaceable.place(cardWidth + deckSeparation + cardOnDeckSeparation, 0, 0.9f)
                            }

                            index == cardIndexes[2] -> {
                                cardPlaceable.place(cardWidth + deckSeparation + cardOnDeckSeparation * 2, 0, 1.0f)
                            }

                            index > cardIndexes[2] -> {
                                cardPlaceable.place(0, 0, 0.2f)
                            }

                            else -> {
                                cardPlaceable.place(0, 0, 0.6f)
                            }
                        }

                    }
                }
            }
        }
    }

    /** Places foundation cards on the stack. */
    fun placeFoundationCards(
        scope: Placeable.PlacementScope,
        placeables: List<Placeable>,
        suite: CardSuite
    ) {
        with(scope) {
            placeables.forEach {
                it.place(calculateFoundationXPosition(suite), 0)
            }
        }
    }

    fun placeTableStacks(
        scope: Placeable.PlacementScope,
        placeables: List<Placeable>,
        tableStackEntry: TableStackEntry
    ) {
        with(scope) {
            placeIndividualTableStack(
                placeables,
                calculateTableStackPosition(tableStackEntry)
            )
        }
    }

    private fun Placeable.PlacementScope.placeIndividualTableStack(
        placeables: List<Placeable>,
        topLeft: IntOffset
    ) {
        /* Under the ideal situation, all cards on the table stack should fit. All calls to the function should ensure that the cards can fit, if not, ensure that the user can scroll. */
        placeables.forEachIndexed { index, placeable ->
            placeable.place(
                x = topLeft.x,
                y = topLeft.y + index * individualTableStackYSeparation
            )
        }
    }

    fun calculateFoundationXPosition(
        suite: CardSuite
    ): Int = when (suite) {
        CardSuite.SPADE -> foundationStartPosition
        CardSuite.CLOVER -> foundationStartPosition + cardWidth + foundationSeparation
        CardSuite.HEARTS -> foundationStartPosition + cardWidth * 2 + foundationSeparation * 2
        CardSuite.DIAMOND -> foundationStartPosition + cardWidth * 3 + foundationSeparation * 3
    }

    fun calculateTableStackPosition(
        tableStackEntry: TableStackEntry
    ): IntOffset = IntOffset(
        x = calculateTableStackXPosition(tableStackEntry),
        y = cardHeight + heightSpacing
    )

    fun calculateTableStackXPosition(
        tableStackEntry: TableStackEntry
    ): Int = when (tableStackEntry) {
        TableStackEntry.ONE -> 0
        TableStackEntry.TWO -> tableStackXSeparation + cardWidth
        TableStackEntry.THREE -> (tableStackXSeparation + cardWidth) * 2
        TableStackEntry.FOUR -> (tableStackXSeparation + cardWidth) * 3
        TableStackEntry.FIVE -> (tableStackXSeparation + cardWidth) * 4
        TableStackEntry.SIX -> (tableStackXSeparation + cardWidth) * 5
        TableStackEntry.SEVEN -> (tableStackXSeparation + cardWidth) * 6
    }


}

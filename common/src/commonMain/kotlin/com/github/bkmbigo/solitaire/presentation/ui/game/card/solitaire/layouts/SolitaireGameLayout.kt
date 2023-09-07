package com.github.bkmbigo.solitaire.presentation.ui.game.card.solitaire.layouts

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.github.bkmbigo.solitaire.game.solitaire.moves.MoveDestination
import com.github.bkmbigo.solitaire.game.solitaire.moves.MoveSource
import com.github.bkmbigo.solitaire.game.solitaire.moves.dsl.move
import com.github.bkmbigo.solitaire.game.solitaire.moves.dsl.to
import com.github.bkmbigo.solitaire.models.core.Card
import com.github.bkmbigo.solitaire.models.core.CardSuite
import com.github.bkmbigo.solitaire.models.solitaire.TableStackEntry
import com.github.bkmbigo.solitaire.presentation.ui.core.locals.cardtheme.LocalCardTheme
import com.github.bkmbigo.solitaire.presentation.ui.game.card.solitaire.components.deck.DeckOverlay
import com.github.bkmbigo.solitaire.presentation.ui.game.card.solitaire.components.deck.EmptyDeck
import com.github.bkmbigo.solitaire.presentation.ui.game.card.solitaire.components.foundation.EmptyFoundation
import com.github.bkmbigo.solitaire.presentation.ui.game.card.solitaire.layouts.utils.*
import com.github.bkmbigo.solitaire.presentation.ui.game.card.solitaire.screens.SolitaireAction
import com.github.bkmbigo.solitaire.presentation.ui.game.card.solitaire.screens.SolitaireState
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun SolitaireGameLayout(
    state: SolitaireState,
    onAction: (SolitaireAction) -> Unit,
    cardView: @Composable (card: Card, isHidden: Boolean, modifier: Modifier, isSelected: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val cardTheme = LocalCardTheme.current

    val solitairePlacer = remember { SolitairePlacer() }

    /* I'm not satisfied with the amount of derivedStateOf calls. */
    val deck by derivedStateOf { state.game.deck }

    val spadeFoundation by derivedStateOf { state.game.spadeFoundationStack }
    val cloverFoundation by derivedStateOf { state.game.cloverFoundationStack }
    val heartsFoundation by derivedStateOf { state.game.heartsFoundationStack }
    val diamondFoundation by derivedStateOf { state.game.diamondFoundationStack }

    val firstTableStack by derivedStateOf { state.game.firstTableStackState }
    val secondTableStack by derivedStateOf { state.game.secondTableStackState }
    val thirdTableStack by derivedStateOf { state.game.thirdTableStackState }
    val fourthTableStack by derivedStateOf { state.game.fourthTableStackState }
    val fifthTableStack by derivedStateOf { state.game.fifthTableStackState }
    val sixthTableStack by derivedStateOf { state.game.sixthTableStackState }
    val seventhTableStack by derivedStateOf { state.game.seventhTableStackState }

    Layout(
        modifier = modifier,
        content = {
            // Surface for empty deck.
            EmptyDeck(
                modifier = Modifier
                    .size(cardTheme.cardSize)
                    .layoutId(SolitaireLayoutId.EMPTY_DECK)
            )

            // Overlay on top of deck. Handles deal() events
            DeckOverlay(
                onDeal = {
                    onAction(SolitaireAction.Deal)
                },
                modifier = Modifier
                    .size(cardTheme.cardSize)
                    .layoutId(SolitaireLayoutId.DECK_OVERLAY)
            )

            // Deck cards
            deck.forEachIndexed { index, card ->

                var isDragging by remember { mutableStateOf(false) }
                var offsetX by remember { mutableStateOf(0f) }
                var offsetY by remember { mutableStateOf(0f) }

                cardView(
                    card,
                    (state.game.deckPosition < deck.size - index),
                    Modifier
                        .size(cardTheme.cardSize)
                        .layoutId(SolitaireLayoutId.DECK_CARD)
                        .offset {
                            IntOffset(
                                x = offsetX.roundToInt(),
                                y = offsetY.roundToInt()
                            )
                        }
                        .then(
                            when {
                                isDragging -> Modifier.zIndex(DraggingZIndex)
                                else -> Modifier
                            }
                        )

                        .pointerInput(state.game) {
                            if (state.game.deckPosition > 0 && state.game.deckPosition == deck.size - index) {
                                detectTapGestures(
                                    onDoubleTap = {
                                        val move =
                                            card move MoveSource.FromDeck(state.game.deck.size - state.game.deckPosition) to MoveDestination.ToFoundation
                                        if (move.isValid(state.game)) {
                                            onAction(
                                                SolitaireAction.PlayMove(move)
                                            )
                                        }
                                    }
                                )
                            }
                        }
                        .pointerInput(state.game) {
                            if (state.game.deckPosition > 0 && state.game.deckPosition == deck.size - index) {
                                detectDragGestures(
                                    onDragStart = {
                                        isDragging = true
                                    },
                                    onDragEnd = {
                                        coroutineScope.launch {
                                            solitairePlacer.processDeckMove(
                                                game = state.game,
                                                card = card,
                                                deckPosition = state.game.deckPosition,
                                                offsetX = offsetX,
                                                offsetY = offsetY,
                                                onMoveSuccess = {
                                                    onAction(SolitaireAction.PlayMove(it))
                                                    isDragging = false
                                                    offsetX = 0f
                                                    offsetY = 0f
                                                },
                                                onMoveFailed = {
                                                    isDragging = false
                                                    offsetX = 0f
                                                    offsetY = 0f
                                                }
                                            )
                                        }
                                    }
                                ) { change, dragAmount ->
                                    offsetX += dragAmount.x
                                    offsetY += dragAmount.y
                                }
                            }
                        },
                    false
                )
            }

            // Empty Foundation
            EmptyFoundation(
                suite = CardSuite.SPADE,
                modifier = Modifier
                    .size(cardTheme.cardSize)
                    .layoutId(SolitaireLayoutId.EMPTY_FOUNDATION_SPADE)
            )
            EmptyFoundation(
                suite = CardSuite.CLOVER,
                modifier = Modifier
                    .size(cardTheme.cardSize)
                    .layoutId(SolitaireLayoutId.EMPTY_FOUNDATION_CLOVER)
            )
            EmptyFoundation(
                suite = CardSuite.HEARTS,
                modifier = Modifier
                    .size(cardTheme.cardSize)
                    .layoutId(SolitaireLayoutId.EMPTY_FOUNDATION_HEARTS)
            )
            EmptyFoundation(
                suite = CardSuite.DIAMOND,
                modifier = Modifier
                    .size(cardTheme.cardSize)
                    .layoutId(SolitaireLayoutId.EMPTY_FOUNDATION_DIAMOND)
            )

            var spadeTopIsDragging by remember { mutableStateOf(false) }
            var spadeTopOffsetX by remember { mutableStateOf(0f) }
            var spadeTopOffsetY by remember { mutableStateOf(0f) }

            // cards on game's Foundation
            spadeFoundation.forEachIndexed { index, card ->
                cardView(
                    card,
                    false,
                    Modifier
                        .size(cardTheme.cardSize)
                        .layoutId(SolitaireLayoutId.FOUNDATION_SPADE)
                        .then(
                            when {
                                spadeTopIsDragging ->
                                    Modifier.zIndex(DraggingZIndex)

                                else -> Modifier
                            }
                        )
                        .then(
                            if (spadeTopIsDragging && index == spadeFoundation.size - 1) {
                                Modifier.offset {
                                    IntOffset(
                                        x = spadeTopOffsetX.roundToInt(),
                                        y = spadeTopOffsetY.roundToInt()
                                    )
                                }
                            } else
                                Modifier
                        )
                        .pointerInput(state.game) {
                            if (index == spadeFoundation.size - 1) {
                                detectDragGestures(
                                    onDragStart = {
                                        spadeTopIsDragging = true
                                    },
                                    onDragEnd = {
                                        coroutineScope.launch {
                                            solitairePlacer.processFoundationMove(
                                                game = state.game,
                                                card = card,
                                                offsetX = spadeTopOffsetX,
                                                offsetY = spadeTopOffsetY,
                                                onMoveSuccess = {
                                                    onAction(SolitaireAction.PlayMove(it))
                                                    spadeTopIsDragging = false
                                                    spadeTopOffsetX = 0f
                                                    spadeTopOffsetY = 0f
                                                },
                                                onMoveFailed = {
                                                    spadeTopIsDragging = false
                                                    spadeTopOffsetX = 0f
                                                    spadeTopOffsetY = 0f
                                                }
                                            )
                                        }
                                    }
                                ) { change, dragAmount ->
                                    spadeTopOffsetX += dragAmount.x
                                    spadeTopOffsetY += dragAmount.y
                                }
                            }
                        },
                    false
                )
            }

            var cloverTopIsDragging by remember { mutableStateOf(false) }
            var cloverTopOffsetX by remember { mutableStateOf(0f) }
            var cloverTopOffsetY by remember { mutableStateOf(0f) }

            cloverFoundation.forEachIndexed { index, card ->
                cardView(
                    card,
                    false,
                    Modifier
                        .size(cardTheme.cardSize)
                        .layoutId(SolitaireLayoutId.FOUNDATION_CLOVER)
                        .then(
                            when {
                                cloverTopIsDragging ->
                                    Modifier.zIndex(DraggingZIndex)

                                else -> Modifier
                            }
                        )
                        .then(
                            if (cloverTopIsDragging && index == cloverFoundation.size - 1) {
                                Modifier.offset {
                                    IntOffset(
                                        x = cloverTopOffsetX.roundToInt(),
                                        y = cloverTopOffsetY.roundToInt()
                                    )
                                }
                            } else
                                Modifier
                        )
                        .pointerInput(state.game) {
                            if (index == cloverFoundation.size - 1) {
                                detectDragGestures(
                                    onDragStart = {
                                        cloverTopIsDragging = true
                                    },
                                    onDragEnd = {
                                        coroutineScope.launch {
                                            solitairePlacer.processFoundationMove(
                                                game = state.game,
                                                card = card,
                                                offsetX = cloverTopOffsetX,
                                                offsetY = cloverTopOffsetY,
                                                onMoveSuccess = {
                                                    onAction(SolitaireAction.PlayMove(it))
                                                    cloverTopIsDragging = false
                                                    cloverTopOffsetX = 0f
                                                    cloverTopOffsetY = 0f
                                                },
                                                onMoveFailed = {
                                                    cloverTopIsDragging = false
                                                    cloverTopOffsetX = 0f
                                                    cloverTopOffsetY = 0f
                                                }
                                            )
                                        }
                                    }
                                ) { change, dragAmount ->
                                    cloverTopOffsetX += dragAmount.x
                                    cloverTopOffsetY += dragAmount.y
                                }
                            }
                        },
                    false
                )
            }

            var heartsTopIsDragging by remember { mutableStateOf(false) }
            var heartsTopOffsetX by remember { mutableStateOf(0f) }
            var heartsTopOffsetY by remember { mutableStateOf(0f) }

            heartsFoundation.forEachIndexed { index, card ->
                cardView(
                    card,
                    false,
                    Modifier
                        .size(cardTheme.cardSize)
                        .layoutId(SolitaireLayoutId.FOUNDATION_HEARTS)
                        .then(
                            when {
                                heartsTopIsDragging ->
                                    Modifier.zIndex(DraggingZIndex)

                                else -> Modifier
                            }
                        )
                        .then(
                            if (heartsTopIsDragging && index == heartsFoundation.size - 1) {
                                Modifier.offset {
                                    IntOffset(
                                        x = heartsTopOffsetX.roundToInt(),
                                        y = heartsTopOffsetY.roundToInt()
                                    )
                                }
                            } else
                                Modifier
                        )
                        .pointerInput(state.game) {
                            if (index == heartsFoundation.size - 1) {
                                detectDragGestures(
                                    onDragStart = {
                                        heartsTopIsDragging = true
                                    },
                                    onDragEnd = {
                                        coroutineScope.launch {
                                            solitairePlacer.processFoundationMove(
                                                game = state.game,
                                                card = card,
                                                offsetX = heartsTopOffsetX,
                                                offsetY = heartsTopOffsetY,
                                                onMoveSuccess = {
                                                    onAction(SolitaireAction.PlayMove(it))
                                                    heartsTopIsDragging = false
                                                    heartsTopOffsetX = 0f
                                                    heartsTopOffsetY = 0f
                                                },
                                                onMoveFailed = {
                                                    heartsTopIsDragging = false
                                                    heartsTopOffsetX = 0f
                                                    heartsTopOffsetY = 0f
                                                }
                                            )
                                        }
                                    }
                                ) { change, dragAmount ->
                                    heartsTopOffsetX += dragAmount.x
                                    heartsTopOffsetY += dragAmount.y
                                }
                            }
                        },
                    false
                )
            }


            var diamondTopIsDragging by remember { mutableStateOf(false) }
            var diamondTopOffsetX by remember { mutableStateOf(0f) }
            var diamondTopOffsetY by remember { mutableStateOf(0f) }

            diamondFoundation.forEachIndexed { index, card ->
                cardView(
                    card,
                    false,
                    Modifier
                        .size(cardTheme.cardSize)
                        .layoutId(SolitaireLayoutId.FOUNDATION_DIAMOND)
                        .then(
                            when {
                                diamondTopIsDragging ->
                                    Modifier.zIndex(DraggingZIndex)

                                else -> Modifier
                            }
                        )
                        .then(
                            if (diamondTopIsDragging && index == diamondFoundation.size - 1) {
                                Modifier.offset {
                                    IntOffset(
                                        x = diamondTopOffsetX.roundToInt(),
                                        y = diamondTopOffsetY.roundToInt()
                                    )
                                }
                            } else
                                Modifier
                        )
                        .pointerInput(state.game) {
                            if (index == diamondFoundation.size - 1) {
                                detectDragGestures(
                                    onDragStart = {
                                        diamondTopIsDragging = true
                                    },
                                    onDragEnd = {
                                        coroutineScope.launch {
                                            solitairePlacer.processFoundationMove(
                                                game = state.game,
                                                card = card,
                                                offsetX = diamondTopOffsetX,
                                                offsetY = diamondTopOffsetY,
                                                onMoveSuccess = {
                                                    onAction(SolitaireAction.PlayMove(it))
                                                    diamondTopIsDragging = false
                                                    diamondTopOffsetX = 0f
                                                    diamondTopOffsetY = 0f
                                                },
                                                onMoveFailed = {
                                                    diamondTopIsDragging = false
                                                    diamondTopOffsetX = 0f
                                                    diamondTopOffsetY = 0f
                                                }
                                            )
                                        }
                                    }
                                ) { change, dragAmount ->
                                    diamondTopOffsetX += dragAmount.x
                                    diamondTopOffsetY += dragAmount.y
                                }
                            }
                        },
                    false
                )
            }

            // cards from game's table stacks

            /* The drag events are consumed by the card being dragged, this means that the cards below the card on the stack do not catch the drag event.
            *   The offset calculated from the drag event is then stored together with the index of the card being dragged.
            *   The offset is then applied to the card being dragged and all cards with a higher index to represent the stack being dragged. */

            // First TableStack
            var firstTableOffsetIndex by remember { mutableStateOf<Int?>(null) }
            var firstTableOffsetX by remember { mutableStateOf(0f) }
            var firstTableOffsetY by remember { mutableStateOf(0f) }

            firstTableStack.hiddenCards.forEach { card ->
                cardView(
                    card,
                    true,
                    Modifier
                        .size(cardTheme.cardSize)
                        .layoutId(SolitaireLayoutId.FIRST_TABLE_STACK),
                    false
                )
            }
            firstTableStack.revealedCards.forEachIndexed { index, card ->
                cardView(
                    card,
                    false,
                    Modifier
                        .size(cardTheme.cardSize)
                        .layoutId(SolitaireLayoutId.FIRST_TABLE_STACK)
                        .then(
                            when {
                                firstTableOffsetIndex != null && firstTableOffsetIndex!! <= index ->
                                    Modifier.zIndex(DraggingZIndex)

                                else -> Modifier
                            }
                        )
                        .then(
                            // the dragOffset is not null and the index is lower than or equal to the cards index
                            firstTableOffsetIndex?.let { offsetIndex ->
                                if (offsetIndex <= index) {
                                    Modifier.offset {
                                        IntOffset(
                                            x = firstTableOffsetX.roundToInt(),
                                            y = firstTableOffsetY.roundToInt()
                                        )
                                    }
                                } else
                                    Modifier
                            } ?: Modifier
                        )
                        .pointerInput(state.game) {
                            if (index == state.game.firstTableStackState.revealedCards.size - 1) {
                                detectTapGestures(
                                    onDoubleTap = {
                                        val move =
                                            card move MoveSource.FromTable(TableStackEntry.ONE) to MoveDestination.ToFoundation
                                        if (move.isValid(state.game)) {
                                            onAction(
                                                SolitaireAction.PlayMove(move)
                                            )
                                        }
                                    }
                                )
                            }
                        }
                        .pointerInput(state.game) {
                            detectDragGestures(
                                onDragStart = {
                                    firstTableOffsetIndex = index
                                },
                                onDragEnd = {
                                    coroutineScope.launch {
                                        solitairePlacer.processTableStackMove(
                                            game = state.game,
                                            tableStackEntry = TableStackEntry.ONE,
                                            index = index,
                                            offsetX = firstTableOffsetX,
                                            offsetY = firstTableOffsetY,
                                            onMoveSuccess = {
                                                onAction(SolitaireAction.PlayMove(it))
                                                firstTableOffsetIndex = null
                                                firstTableOffsetX = 0f
                                                firstTableOffsetY = 0f
                                            },
                                            onMoveFailed = {
                                                firstTableOffsetIndex = null
                                                firstTableOffsetX = 0f
                                                firstTableOffsetY = 0f
                                            }
                                        )
                                    }
                                }
                            ) { change, dragAmount ->
                                firstTableOffsetX += dragAmount.x
                                firstTableOffsetY += dragAmount.y

                                change.consume()
                            }
                        },
                    false
                )
            }

            // Second TableStack

            var secondTableOffsetIndex by remember { mutableStateOf<Int?>(null) }
            var secondTableOffsetX by remember { mutableStateOf(0f) }
            var secondTableOffsetY by remember { mutableStateOf(0f) }

            secondTableStack.hiddenCards.forEach { card ->
                cardView(
                    card,
                    true,
                    Modifier
                        .size(cardTheme.cardSize)
                        .layoutId(SolitaireLayoutId.SECOND_TABLE_STACK),
                    false
                )
            }
            secondTableStack.revealedCards.forEachIndexed { index, card ->
                cardView(
                    card,
                    false,
                    Modifier
                        .size(cardTheme.cardSize)
                        .layoutId(SolitaireLayoutId.SECOND_TABLE_STACK)
                        .then(
                            when {
                                secondTableOffsetIndex != null && secondTableOffsetIndex!! <= index ->
                                    Modifier.zIndex(DraggingZIndex)

                                else -> Modifier
                            }
                        )
                        .then(
                            // the dragOffset is not null and the index is lower than or equal to the cards index
                            secondTableOffsetIndex?.let { offsetIndex ->
                                if (offsetIndex <= index) {
                                    Modifier.offset {
                                        IntOffset(
                                            x = secondTableOffsetX.roundToInt(),
                                            y = secondTableOffsetY.roundToInt()
                                        )
                                    }
                                } else
                                    Modifier
                            } ?: Modifier
                        )
                        .pointerInput(state.game) {
                            if (index == state.game.secondTableStackState.revealedCards.size - 1) {
                                detectTapGestures(
                                    onDoubleTap = {
                                        val move =
                                            card move MoveSource.FromTable(TableStackEntry.TWO) to MoveDestination.ToFoundation
                                        if (move.isValid(state.game)) {
                                            onAction(
                                                SolitaireAction.PlayMove(move)
                                            )
                                        }
                                    }
                                )
                            }
                        }
                        .pointerInput(state.game) {
                            detectDragGestures(
                                onDragStart = {
                                    secondTableOffsetIndex = index
                                },
                                onDragEnd = {
                                    coroutineScope.launch {
                                        solitairePlacer.processTableStackMove(
                                            game = state.game,
                                            tableStackEntry = TableStackEntry.TWO,
                                            index = index,
                                            offsetX = secondTableOffsetX,
                                            offsetY = secondTableOffsetY,
                                            onMoveSuccess = {
                                                onAction(SolitaireAction.PlayMove(it))
                                                secondTableOffsetIndex = null
                                                secondTableOffsetX = 0f
                                                secondTableOffsetY = 0f
                                            },
                                            onMoveFailed = {
                                                secondTableOffsetIndex = null
                                                secondTableOffsetX = 0f
                                                secondTableOffsetY = 0f
                                            }
                                        )
                                    }
                                }
                            ) { change, dragAmount ->
                                secondTableOffsetX += dragAmount.x
                                secondTableOffsetY += dragAmount.y

                                change.consume()
                            }
                        },
                    false
                )
            }

            // Third TableStack

            var thirdTableOffsetIndex by remember { mutableStateOf<Int?>(null) }
            var thirdTableOffsetX by remember { mutableStateOf(0f) }
            var thirdTableOffsetY by remember { mutableStateOf(0f) }

            thirdTableStack.hiddenCards.forEach { card ->
                cardView(
                    card,
                    true,
                    Modifier
                        .size(cardTheme.cardSize)
                        .layoutId(SolitaireLayoutId.THIRD_TABLE_STACK),
                    false
                )
            }
            thirdTableStack.revealedCards.forEachIndexed { index, card ->
                cardView(
                    card,
                    false,
                    Modifier
                        .size(cardTheme.cardSize)
                        .layoutId(SolitaireLayoutId.THIRD_TABLE_STACK)
                        .then(
                            when {
                                thirdTableOffsetIndex != null && thirdTableOffsetIndex!! <= index ->
                                    Modifier.zIndex(DraggingZIndex)

                                else -> Modifier
                            }
                        )
                        .then(
                            // the dragOffset is not null and the index is lower than or equal to the cards index
                            thirdTableOffsetIndex?.let { offsetIndex ->
                                if (offsetIndex <= index) {
                                    Modifier.offset {
                                        IntOffset(
                                            x = thirdTableOffsetX.roundToInt(),
                                            y = thirdTableOffsetY.roundToInt()
                                        )
                                    }
                                } else
                                    Modifier
                            } ?: Modifier
                        )
                        .pointerInput(state.game) {
                            if (index == state.game.thirdTableStackState.revealedCards.size - 1) {
                                detectTapGestures(
                                    onDoubleTap = {
                                        val move =
                                            card move MoveSource.FromTable(TableStackEntry.THREE) to MoveDestination.ToFoundation
                                        if (move.isValid(state.game)) {
                                            onAction(
                                                SolitaireAction.PlayMove(move)
                                            )
                                        }
                                    }
                                )
                            }
                        }
                        .pointerInput(state.game) {
                            detectDragGestures(
                                onDragStart = {
                                    thirdTableOffsetIndex = index
                                },
                                onDragEnd = {
                                    solitairePlacer.processTableStackMove(
                                        game = state.game,
                                        tableStackEntry = TableStackEntry.THREE,
                                        index = index,
                                        offsetX = thirdTableOffsetX,
                                        offsetY = thirdTableOffsetY,
                                        onMoveSuccess = {
                                            onAction(SolitaireAction.PlayMove(it))
                                            thirdTableOffsetIndex = null
                                            thirdTableOffsetX = 0f
                                            thirdTableOffsetY = 0f
                                        },
                                        onMoveFailed = {
                                            thirdTableOffsetIndex = null
                                            thirdTableOffsetX = 0f
                                            thirdTableOffsetY = 0f
                                        }
                                    )
                                }
                            ) { change, dragAmount ->
                                thirdTableOffsetX += dragAmount.x
                                thirdTableOffsetY += dragAmount.y

                                change.consume()
                            }
                        },
                    false
                )
            }

            // Fourth TableStack

            var fourthTableOffsetIndex by remember { mutableStateOf<Int?>(null) }
            var fourthTableOffsetX by remember { mutableStateOf(0f) }
            var fourthTableOffsetY by remember { mutableStateOf(0f) }

            fourthTableStack.hiddenCards.forEach { card ->
                cardView(
                    card,
                    true,
                    Modifier
                        .size(cardTheme.cardSize)
                        .layoutId(SolitaireLayoutId.FOURTH_TABLE_STACK),
                    false
                )
            }
            fourthTableStack.revealedCards.forEachIndexed { index, card ->
                cardView(
                    card,
                    false,
                    Modifier
                        .size(cardTheme.cardSize)
                        .layoutId(SolitaireLayoutId.FOURTH_TABLE_STACK)
                        .then(
                            when {
                                fourthTableOffsetIndex != null && fourthTableOffsetIndex!! <= index ->
                                    Modifier.zIndex(DraggingZIndex)

                                else -> Modifier
                            }
                        )
                        .then(
                            // the dragOffset is not null and the index is lower than or equal to the cards index
                            fourthTableOffsetIndex?.let { offsetIndex ->
                                if (offsetIndex <= index) {
                                    Modifier.offset {
                                        IntOffset(
                                            x = fourthTableOffsetX.roundToInt(),
                                            y = fourthTableOffsetY.roundToInt()
                                        )
                                    }
                                } else
                                    Modifier
                            } ?: Modifier
                        )
                        .pointerInput(state.game) {
                            if (index == state.game.fourthTableStackState.revealedCards.size - 1) {
                                detectTapGestures(
                                    onDoubleTap = {
                                        val move =
                                            card move MoveSource.FromTable(TableStackEntry.FOUR) to MoveDestination.ToFoundation
                                        if (move.isValid(state.game)) {
                                            onAction(
                                                SolitaireAction.PlayMove(move)
                                            )
                                        }
                                    }
                                )
                            }
                        }
                        .pointerInput(state.game) {
                            detectDragGestures(
                                onDragStart = {
                                    fourthTableOffsetIndex = index
                                },
                                onDragEnd = {
                                    solitairePlacer.processTableStackMove(
                                        game = state.game,
                                        tableStackEntry = TableStackEntry.FOUR,
                                        index = index,
                                        offsetX = fourthTableOffsetX,
                                        offsetY = fourthTableOffsetY,
                                        onMoveSuccess = {
                                            onAction(SolitaireAction.PlayMove(it))
                                            fourthTableOffsetIndex = null
                                            fourthTableOffsetX = 0f
                                            fourthTableOffsetY = 0f
                                        },
                                        onMoveFailed = {
                                            fourthTableOffsetIndex = null
                                            fourthTableOffsetX = 0f
                                            fourthTableOffsetY = 0f
                                        }
                                    )
                                }
                            ) { change, dragAmount ->
                                fourthTableOffsetX += dragAmount.x
                                fourthTableOffsetY += dragAmount.y

                                change.consume()
                            }
                        },
                    false
                )
            }

            // Fifth TableStack

            var fifthTableOffsetIndex by remember { mutableStateOf<Int?>(null) }
            var fifthTableOffsetX by remember { mutableStateOf(0f) }
            var fifthTableOffsetY by remember { mutableStateOf(0f) }

            fifthTableStack.hiddenCards.forEach { card ->
                cardView(
                    card,
                    true,
                    Modifier
                        .size(cardTheme.cardSize)
                        .layoutId(SolitaireLayoutId.FIFTH_TABLE_STACK),
                    false
                )
            }
            fifthTableStack.revealedCards.forEachIndexed { index, card ->
                cardView(
                    card,
                    false,
                    Modifier
                        .size(cardTheme.cardSize)
                        .layoutId(SolitaireLayoutId.FIFTH_TABLE_STACK)
                        .then(
                            when {
                                fifthTableOffsetIndex != null && fifthTableOffsetIndex!! <= index ->
                                    Modifier.zIndex(DraggingZIndex)

                                else -> Modifier
                            }
                        )
                        .then(
                            // the dragOffset is not null and the index is lower than or equal to the cards index
                            fifthTableOffsetIndex?.let { offsetIndex ->
                                if (offsetIndex <= index) {
                                    Modifier.offset {
                                        IntOffset(
                                            x = fifthTableOffsetX.roundToInt(),
                                            y = fifthTableOffsetY.roundToInt()
                                        )
                                    }
                                } else
                                    Modifier
                            } ?: Modifier
                        )
                        .pointerInput(state.game) {
                            if (index == state.game.fifthTableStackState.revealedCards.size - 1) {
                                detectTapGestures(
                                    onDoubleTap = {
                                        val move =
                                            card move MoveSource.FromTable(TableStackEntry.FIVE) to MoveDestination.ToFoundation
                                        if (move.isValid(state.game)) {
                                            onAction(
                                                SolitaireAction.PlayMove(move)
                                            )
                                        }
                                    }
                                )
                            }
                        }
                        .pointerInput(state.game) {
                            detectDragGestures(
                                onDragStart = {
                                    fifthTableOffsetIndex = index
                                },
                                onDragEnd = {
                                    solitairePlacer.processTableStackMove(
                                        game = state.game,
                                        tableStackEntry = TableStackEntry.FIVE,
                                        index = index,
                                        offsetX = fifthTableOffsetX,
                                        offsetY = fifthTableOffsetY,
                                        onMoveSuccess = {
                                            onAction(SolitaireAction.PlayMove(it))
                                            fifthTableOffsetIndex = null
                                            fifthTableOffsetX = 0f
                                            fifthTableOffsetY = 0f
                                        },
                                        onMoveFailed = {
                                            fifthTableOffsetIndex = null
                                            fifthTableOffsetX = 0f
                                            fifthTableOffsetY = 0f
                                        }
                                    )
                                }
                            ) { change, dragAmount ->
                                fifthTableOffsetX += dragAmount.x
                                fifthTableOffsetY += dragAmount.y

                                change.consume()
                            }
                        },
                    false
                )
            }

            // Sixth TableStack

            var sixthTableOffsetIndex by remember { mutableStateOf<Int?>(null) }
            var sixthTableOffsetX by remember { mutableStateOf(0f) }
            var sixthTableOffsetY by remember { mutableStateOf(0f) }

            sixthTableStack.hiddenCards.forEach { card ->
                cardView(
                    card,
                    true,
                    Modifier
                        .size(cardTheme.cardSize)
                        .layoutId(SolitaireLayoutId.SIXTH_TABLE_STACK),
                    false
                )
            }
            sixthTableStack.revealedCards.forEachIndexed { index, card ->
                cardView(
                    card,
                    false,
                    Modifier
                        .size(cardTheme.cardSize)
                        .layoutId(SolitaireLayoutId.SIXTH_TABLE_STACK)
                        .then(
                            when {
                                sixthTableOffsetIndex != null && sixthTableOffsetIndex!! <= index ->
                                    Modifier.zIndex(DraggingZIndex)

                                else -> Modifier
                            }
                        )
                        .then(
                            // the dragOffset is not null and the index is lower than or equal to the cards index
                            sixthTableOffsetIndex?.let { offsetIndex ->
                                if (offsetIndex <= index) {
                                    Modifier.offset {
                                        IntOffset(
                                            x = sixthTableOffsetX.roundToInt(),
                                            y = sixthTableOffsetY.roundToInt()
                                        )
                                    }
                                } else
                                    Modifier
                            } ?: Modifier
                        )
                        .pointerInput(state.game) {
                            if (index == state.game.sixthTableStackState.revealedCards.size - 1) {
                                detectTapGestures(
                                    onDoubleTap = {
                                        val move =
                                            card move MoveSource.FromTable(TableStackEntry.SIX) to MoveDestination.ToFoundation
                                        if (move.isValid(state.game)) {
                                            onAction(
                                                SolitaireAction.PlayMove(move)
                                            )
                                        }
                                    }
                                )
                            }
                        }
                        .pointerInput(state.game) {
                            detectDragGestures(
                                onDragStart = {
                                    sixthTableOffsetIndex = index
                                },
                                onDragEnd = {
                                    solitairePlacer.processTableStackMove(
                                        game = state.game,
                                        tableStackEntry = TableStackEntry.SIX,
                                        index = index,
                                        offsetX = sixthTableOffsetX,
                                        offsetY = sixthTableOffsetY,
                                        onMoveSuccess = {
                                            onAction(SolitaireAction.PlayMove(it))
                                            sixthTableOffsetIndex = null
                                            sixthTableOffsetX = 0f
                                            sixthTableOffsetY = 0f
                                        },
                                        onMoveFailed = {
                                            sixthTableOffsetIndex = null
                                            sixthTableOffsetX = 0f
                                            sixthTableOffsetY = 0f
                                        }
                                    )
                                }
                            ) { change, dragAmount ->
                                sixthTableOffsetX += dragAmount.x
                                sixthTableOffsetY += dragAmount.y

                                change.consume()
                            }
                        },
                    false
                )
            }

            // Seventh TableStack

            var seventhTableOffsetIndex by remember { mutableStateOf<Int?>(null) }
            var seventhTableOffsetX by remember { mutableStateOf(0f) }
            var seventhTableOffsetY by remember { mutableStateOf(0f) }

            seventhTableStack.hiddenCards.forEach { card ->
                cardView(
                    card,
                    true,
                    Modifier
                        .size(cardTheme.cardSize)
                        .layoutId(SolitaireLayoutId.SEVENTH_TABLE_STACK),
                    false
                )
            }
            seventhTableStack.revealedCards.forEachIndexed { index, card ->
                cardView(
                    card,
                    false,
                    Modifier
                        .size(cardTheme.cardSize)
                        .layoutId(SolitaireLayoutId.SEVENTH_TABLE_STACK)
                        .then(
                            when {
                                seventhTableOffsetIndex != null && seventhTableOffsetIndex!! <= index ->
                                    Modifier.zIndex(DraggingZIndex)

                                else -> Modifier
                            }
                        )
                        .then(
                            // the dragOffset is not null and the index is lower than or equal to the cards index
                            seventhTableOffsetIndex?.let { offsetIndex ->
                                if (offsetIndex <= index) {
                                    Modifier.offset {
                                        IntOffset(
                                            x = seventhTableOffsetX.roundToInt(),
                                            y = seventhTableOffsetY.roundToInt()
                                        )
                                    }
                                } else
                                    Modifier
                            } ?: Modifier
                        )
                        .pointerInput(state.game) {
                            if (index == state.game.seventhTableStackState.revealedCards.size - 1) {
                                detectTapGestures(
                                    onDoubleTap = {
                                        val move =
                                            card move MoveSource.FromTable(TableStackEntry.SEVEN) to MoveDestination.ToFoundation
                                        if (move.isValid(state.game)) {
                                            onAction(
                                                SolitaireAction.PlayMove(move)
                                            )
                                        }
                                    }
                                )
                            }
                        }
                        .pointerInput(state.game) {
                            detectDragGestures(
                                onDragStart = {
                                    seventhTableOffsetIndex = index
                                },
                                onDragEnd = {
                                    solitairePlacer.processTableStackMove(
                                        game = state.game,
                                        tableStackEntry = TableStackEntry.SEVEN,
                                        index = index,
                                        offsetX = seventhTableOffsetX,
                                        offsetY = seventhTableOffsetY,
                                        onMoveSuccess = {
                                            onAction(SolitaireAction.PlayMove(it))
                                            seventhTableOffsetIndex = null
                                            seventhTableOffsetX = 0f
                                            seventhTableOffsetY = 0f
                                        },
                                        onMoveFailed = {
                                            seventhTableOffsetIndex = null
                                            seventhTableOffsetX = 0f
                                            seventhTableOffsetY = 0f
                                        }
                                    )
                                }
                            ) { change, dragAmount ->
                                seventhTableOffsetX += dragAmount.x
                                seventhTableOffsetY += dragAmount.y

                                change.consume()
                            }
                        },
                    false
                )
            }

        }
    ) { measurables, constraints ->
        val measurablesByLayoutId = measurables.groupBy { it.layoutId as? SolitaireLayoutId }

        val emptyDeckMeasurable = measurablesByLayoutId[SolitaireLayoutId.EMPTY_DECK]?.firstOrNull()
        val overlayDeckMeasurable = measurablesByLayoutId[SolitaireLayoutId.DECK_OVERLAY]?.firstOrNull()
        val cardsOnDeckMeasurables = measurablesByLayoutId[SolitaireLayoutId.DECK_CARD] ?: emptyList()

        val emptySpadeFoundationMeasurable =
            measurablesByLayoutId[SolitaireLayoutId.EMPTY_FOUNDATION_SPADE]?.firstOrNull()
        val emptyCloverFoundationMeasurable =
            measurablesByLayoutId[SolitaireLayoutId.EMPTY_FOUNDATION_CLOVER]?.firstOrNull()
        val emptyHeartsFoundationMeasurable =
            measurablesByLayoutId[SolitaireLayoutId.EMPTY_FOUNDATION_HEARTS]?.firstOrNull()
        val emptyDiamondFoundationMeasurable =
            measurablesByLayoutId[SolitaireLayoutId.EMPTY_FOUNDATION_DIAMOND]?.firstOrNull()

        val spadesFoundationMeasurables = measurablesByLayoutId[SolitaireLayoutId.FOUNDATION_SPADE] ?: emptyList()
        val cloverFoundationMeasurables = measurablesByLayoutId[SolitaireLayoutId.FOUNDATION_CLOVER] ?: emptyList()
        val heartsFoundationMeasurables = measurablesByLayoutId[SolitaireLayoutId.FOUNDATION_HEARTS] ?: emptyList()
        val diamondFoundationMeasurables = measurablesByLayoutId[SolitaireLayoutId.FOUNDATION_DIAMOND] ?: emptyList()

        val firstTableMeasurables = measurablesByLayoutId[SolitaireLayoutId.FIRST_TABLE_STACK] ?: emptyList()
        val secondTableMeasurables = measurablesByLayoutId[SolitaireLayoutId.SECOND_TABLE_STACK] ?: emptyList()
        val thirdTableMeasurables = measurablesByLayoutId[SolitaireLayoutId.THIRD_TABLE_STACK] ?: emptyList()
        val fourthTableMeasurables = measurablesByLayoutId[SolitaireLayoutId.FOURTH_TABLE_STACK] ?: emptyList()
        val fifthTableMeasurables = measurablesByLayoutId[SolitaireLayoutId.FIFTH_TABLE_STACK] ?: emptyList()
        val sixthTableMeasurables = measurablesByLayoutId[SolitaireLayoutId.SIXTH_TABLE_STACK] ?: emptyList()
        val seventhTableMeasurables = measurablesByLayoutId[SolitaireLayoutId.SEVENTH_TABLE_STACK] ?: emptyList()


        val cardWidth = cardTheme.cardSize.width.roundToPx()
        val cardHeight = cardTheme.cardSize.height.roundToPx()
        val cardConstraints = Constraints.fixed(cardWidth, cardHeight)

        val heightSpacing = HeightSpacing.roundToPx()
        val deckSeparation = cardWidth * 1 / 2
        val foundationSeparation = cardWidth * 1 / 4
        val cardOnDeckSeparation = cardTheme.horizontalCardStackSeparation.roundToPx()

        val verticalCardSeparation = cardTheme.verticalCardStackSeparation.roundToPx()
        val deckWidth = cardWidth * 2 + deckSeparation + cardOnDeckSeparation * 2

        solitairePlacer.updatePlacementValues(
            cardHeight = cardHeight,
            cardWidth = cardWidth,
            gameHeight = constraints.maxHeight,
            gameWidth = constraints.maxWidth,
            deckSeparation = deckSeparation,
            cardOnDeckSeparation = cardOnDeckSeparation,
            foundationSeparation = foundationSeparation,
            heightSpacing = heightSpacing,
            minimalTableStackSeparation = cardWidth * 1 / 8,
            optimalTableStackHeightSeparation = verticalCardSeparation
        )

        val emptyDeckPlaceable = emptyDeckMeasurable?.measure(cardConstraints)
        val overlayDeckPlaceable = overlayDeckMeasurable?.measure(cardConstraints)
        val cardsOnDeckPlaceable = cardsOnDeckMeasurables.map { it.measure(cardConstraints) }
        val emptySpadeFoundationPlaceable = emptySpadeFoundationMeasurable?.measure(cardConstraints)
        val emptyCloverFoundationPlaceable = emptyCloverFoundationMeasurable?.measure(cardConstraints)
        val emptyHeartsFoundationPlaceable = emptyHeartsFoundationMeasurable?.measure(cardConstraints)
        val emptyDiamondFoundationPlaceable = emptyDiamondFoundationMeasurable?.measure(cardConstraints)
        val spadesFoundationCardsPlaceables = spadesFoundationMeasurables.map { it.measure(cardConstraints) }
        val cloverFoundationCardsPlaceables = cloverFoundationMeasurables.map { it.measure(cardConstraints) }
        val heartsFoundationCardsPlaceables = heartsFoundationMeasurables.map { it.measure(cardConstraints) }
        val diamondFoundationCardsPlaceables = diamondFoundationMeasurables.map { it.measure(cardConstraints) }
        val firstTableStackPlaceables = firstTableMeasurables.map { it.measure(cardConstraints) }
        val secondTableStackPlaceables = secondTableMeasurables.map { it.measure(cardConstraints) }
        val thirdTableStackPlaceables = thirdTableMeasurables.map { it.measure(cardConstraints) }
        val fourthTableStackPlaceables = fourthTableMeasurables.map { it.measure(cardConstraints) }
        val fifthTableStackPlaceables = fifthTableMeasurables.map { it.measure(cardConstraints) }
        val sixthTableStackPlaceables = sixthTableMeasurables.map { it.measure(cardConstraints) }
        val seventhTableStackPlaceables = seventhTableMeasurables.map { it.measure(cardConstraints) }

        /* Calculating whether table stacks can fit into the width. */
        val minimalTableStackWidth = cardWidth * 7 + foundationSeparation * 9
        val canFitTableStacks = constraints.maxWidth > minimalTableStackWidth

        layout(constraints.maxWidth, constraints.maxHeight) {
            /* First, let's place the deck */
            solitairePlacer.placeDeckCards(this, cardsOnDeckPlaceable, state.game.deckPosition)
            emptyDeckPlaceable?.let { placeable ->
                solitairePlacer.place(this, placeable, SolitaireLayoutId.EMPTY_DECK)
            }
            overlayDeckPlaceable?.let { placeable ->
                solitairePlacer.place(this, placeable, SolitaireLayoutId.DECK_OVERLAY)
            }

            // Diamond Foundation
            emptyDiamondFoundationPlaceable?.let { placeable ->
                solitairePlacer.place(this, placeable, SolitaireLayoutId.EMPTY_FOUNDATION_DIAMOND)
            }
            solitairePlacer.placeFoundationCards(this, diamondFoundationCardsPlaceables, CardSuite.DIAMOND)

            // Hearts Foundation
            emptyHeartsFoundationPlaceable?.let { placeable ->
                solitairePlacer.place(this, placeable, SolitaireLayoutId.EMPTY_FOUNDATION_HEARTS)
            }
            solitairePlacer.placeFoundationCards(this, heartsFoundationCardsPlaceables, CardSuite.HEARTS)

            // Clover Foundation
            emptyCloverFoundationPlaceable?.let { placeable ->
                solitairePlacer.place(this, placeable, SolitaireLayoutId.EMPTY_FOUNDATION_CLOVER)
            }
            solitairePlacer.placeFoundationCards(this, cloverFoundationCardsPlaceables, CardSuite.CLOVER)

            // Spade Foundation
            emptySpadeFoundationPlaceable?.let { placeable ->
                solitairePlacer.place(this, placeable, SolitaireLayoutId.EMPTY_FOUNDATION_SPADE)
            }
            solitairePlacer.placeFoundationCards(this, spadesFoundationCardsPlaceables, CardSuite.SPADE)


            /* Let's place the table stacks. */
            solitairePlacer.placeTableStacks(this, firstTableStackPlaceables, TableStackEntry.ONE)
            solitairePlacer.placeTableStacks(this, secondTableStackPlaceables, TableStackEntry.TWO)
            solitairePlacer.placeTableStacks(this, thirdTableStackPlaceables, TableStackEntry.THREE)
            solitairePlacer.placeTableStacks(this, fourthTableStackPlaceables, TableStackEntry.FOUR)
            solitairePlacer.placeTableStacks(this, fifthTableStackPlaceables, TableStackEntry.FIVE)
            solitairePlacer.placeTableStacks(this, sixthTableStackPlaceables, TableStackEntry.SIX)
            solitairePlacer.placeTableStacks(this, seventhTableStackPlaceables, TableStackEntry.SEVEN)

        }
    }
}


private val HeightSpacing = 32.dp

private const val DraggingZIndex = 5.0f
private const val NormalZIndex = 1.0f



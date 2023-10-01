package com.github.bkmbigo.solitaire.presentation.solitaire.layouts

import androidx.compose.animation.core.Animatable
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
import com.github.bkmbigo.solitaire.game.solitaire.moves.SolitaireUserMove
import com.github.bkmbigo.solitaire.game.solitaire.moves.dsl.move
import com.github.bkmbigo.solitaire.game.solitaire.moves.dsl.to
import com.github.bkmbigo.solitaire.models.core.Card
import com.github.bkmbigo.solitaire.models.core.CardSuite
import com.github.bkmbigo.solitaire.models.solitaire.TableStackEntry
import com.github.bkmbigo.solitaire.presentation.core.locals.cardtheme.LocalCardTheme
import com.github.bkmbigo.solitaire.presentation.solitaire.components.deck.DeckOverlay
import com.github.bkmbigo.solitaire.presentation.solitaire.components.deck.EmptyDeck
import com.github.bkmbigo.solitaire.presentation.solitaire.components.foundation.EmptyFoundation
import com.github.bkmbigo.solitaire.presentation.solitaire.layouts.utils.*
import com.github.bkmbigo.solitaire.presentation.solitaire.screens.SolitaireAction
import com.github.bkmbigo.solitaire.presentation.solitaire.screens.SolitaireState
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun SolitaireGameLayout(
    state: SolitaireState,
    hint: Flow<SolitaireUserMove>,
    onAction: (SolitaireAction) -> Unit,
    cardView: @Composable (card: Card, isHidden: Boolean, modifier: Modifier, isSelected: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val cardTheme = LocalCardTheme.current

    val solitairePlacer = remember { SolitairePlacer() }

    var currentHint by remember { mutableStateOf<SolitaireUserMove?>(null) }

    // Used as a flag to cancel any ongoing hint animations
    var cancelHintAnimation: SolitaireHintAnimationCancellation? by remember { mutableStateOf(null) }

    /* I'm not satisfied with the amount of derivedStateOf calls. */
    val deck by derivedStateOf { state.game.deck }

    LaunchedEffect(Unit) {
        hint.collect { move ->
            if (currentHint == null) {
                currentHint = move
            }
        }
    }

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
                var deckIsAnimating by remember { mutableStateOf(false) }

                val animateOffsetX = remember { Animatable(0f) }
                val animateOffsetY = remember { Animatable(0f) }

                LaunchedEffect(cancelHintAnimation) {
                    if (cancelHintAnimation != null && deckIsAnimating) {
                        // Cancel any ongoing animations

                        when (cancelHintAnimation) {
                            SolitaireHintAnimationCancellation.AnimateBack -> {
                                val xBackAnim = async { animateOffsetX.animateTo(0f) }
                                val yBackAnim = async { animateOffsetY.animateTo(0f) }
                                awaitAll(xBackAnim, yBackAnim)
                            }

                            SolitaireHintAnimationCancellation.StopInPlace -> {
                                animateOffsetX.stop()
                                animateOffsetY.stop()
                            }

                            else -> {}
                        }

                        deckIsAnimating = false
                        currentHint = null
                        cancelHintAnimation = null
                    }
                }


                LaunchedEffect(state, currentHint) {
                    // Change should only affect the playable card
                    if (state.game.deckPositions.isNotEmpty() && state.game.deckPositions.last() == deck.size - index) {
                        // Ensure that the current hint is from deck
                        if (currentHint is SolitaireUserMove.CardMove) {
                            val moveHint = currentHint as SolitaireUserMove.CardMove
                            if (moveHint.from is MoveSource.FromDeck) {
                                // Calculate the position of the card
                                val startPosition = with(solitairePlacer) {
                                    when (state.game.deckPositions.size) {
                                        0 -> null
                                        1 -> cardWidth + deckSeparation
                                        2 -> cardWidth + deckSeparation + cardOnDeckSeparation
                                        else -> cardWidth + deckSeparation + cardOnDeckSeparation * 2
                                    }
                                }

                                if (startPosition != null) {
                                    when (moveHint.to) {
                                        // If move is to foundation, animate the x position of the card
                                        MoveDestination.ToFoundation -> {
                                            deckIsAnimating = true
                                            moveHint.cards.firstOrNull()?.suite?.let {
                                                animateOffsetX.animateTo(
                                                    solitairePlacer.calculateFoundationXPosition(it).toFloat() -
                                                            startPosition
                                                )
                                                cancelHintAnimation = SolitaireHintAnimationCancellation.AnimateBack
                                            }
                                        }

                                        is MoveDestination.ToTable -> {
                                            deckIsAnimating = true
                                            moveHint.cards.firstOrNull()?.let { card ->
                                                val topLeft =
                                                    solitairePlacer.calculateTableStackPosition(moveHint.to.tableStackEntry)

                                                val xAnim =
                                                    async { animateOffsetX.animateTo(topLeft.x.toFloat() - startPosition) }
                                                val yAnim = async {
                                                    animateOffsetY.animateTo(
                                                        topLeft.y.toFloat() + state.game.tableStack(moveHint.to.tableStackEntry).size * solitairePlacer.individualTableStackYSeparation.toFloat()
                                                    )
                                                }
                                                awaitAll(xAnim, yAnim)
                                                cancelHintAnimation = SolitaireHintAnimationCancellation.AnimateBack
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                cardView(
                    card,
                    !(state.game.deckPositions.isNotEmpty() && state.game.deckPositions.contains(deck.size - index)),
                    Modifier
                        .size(cardTheme.cardSize)
                        .layoutId(SolitaireLayoutId.DECK_CARD)
                        .offset {
                            IntOffset(
                                x = animateOffsetX.value.roundToInt(),
                                y = animateOffsetY.value.roundToInt()
                            )
                        }
                        .then(
                            when {
                                deckIsAnimating -> Modifier.zIndex(DraggingZIndex)
                                isDragging -> Modifier.zIndex(DraggingZIndex)
                                else -> Modifier
                            }
                        )
                        .pointerInput(state.game) {
                            if (state.game.deckPositions.isNotEmpty() && state.game.deckPositions.last() == deck.size - index) {
                                detectTapGestures(
                                    onDoubleTap = {
                                        val move =
                                            card move MoveSource.FromDeck(index) to MoveDestination.ToFoundation
                                        if (move.isValid(state.game)) {
                                            onAction(SolitaireAction.PlayMove(move))
                                        }
                                    }
                                )
                            }
                        }
                        .pointerInput(state.game) {
                            if (state.game.deckPositions.isNotEmpty() && state.game.deckPositions.last() == deck.size - index) {
                                detectDragGestures(
                                    onDragStart = {
                                        isDragging = true
                                        if (deckIsAnimating) {
                                            cancelHintAnimation = SolitaireHintAnimationCancellation.StopInPlace
                                        }
                                    },
                                    onDragEnd = {
                                        coroutineScope.launch {
                                            solitairePlacer.processDeckMove(
                                                game = state.game,
                                                card = card,
                                                offsetX = animateOffsetX.value,
                                                offsetY = animateOffsetY.value,
                                                onMoveSuccess = {
                                                    onAction(SolitaireAction.PlayMove(it))
                                                    isDragging = false
                                                    coroutineScope.launch {
                                                        animateOffsetX.snapTo(0f)
                                                        animateOffsetY.snapTo(0f)
                                                    }
                                                },
                                                onMoveFailed = {
                                                    isDragging = false
                                                    coroutineScope.launch {
                                                        animateOffsetX.snapTo(0f)
                                                        animateOffsetY.snapTo(0f)
                                                    }
                                                }
                                            )
                                        }
                                    }
                                ) { change, dragAmount ->
                                    coroutineScope.launch {
                                        animateOffsetX.snapTo(animateOffsetX.value + dragAmount.x)
                                        animateOffsetY.snapTo(animateOffsetY.value + dragAmount.y)
                                    }
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

            FoundationPlacement(
                suite = CardSuite.SPADE,
                state = state,
                currentHint = currentHint,
                solitairePlacer = solitairePlacer,
                cardView = cardView,
                cancelHintAnimation = cancelHintAnimation,
                onAction = onAction,
                onCancelHintAnimationChange = {
                    cancelHintAnimation = it
                },
                onCurrentHintChange = {
                    currentHint = it
                }
            )

            FoundationPlacement(
                suite = CardSuite.CLOVER,
                state = state,
                currentHint = currentHint,
                solitairePlacer = solitairePlacer,
                cardView = cardView,
                cancelHintAnimation = cancelHintAnimation,
                onAction = onAction,
                onCancelHintAnimationChange = {
                    cancelHintAnimation = it
                },
                onCurrentHintChange = {
                    currentHint = it
                }
            )

            FoundationPlacement(
                suite = CardSuite.HEARTS,
                state = state,
                currentHint = currentHint,
                solitairePlacer = solitairePlacer,
                cardView = cardView,
                cancelHintAnimation = cancelHintAnimation,
                onAction = onAction,
                onCancelHintAnimationChange = {
                    cancelHintAnimation = it
                },
                onCurrentHintChange = {
                    currentHint = it
                }
            )

            FoundationPlacement(
                suite = CardSuite.DIAMOND,
                state = state,
                currentHint = currentHint,
                solitairePlacer = solitairePlacer,
                cardView = cardView,
                cancelHintAnimation = cancelHintAnimation,
                onAction = onAction,
                onCancelHintAnimationChange = {
                    cancelHintAnimation = it
                },
                onCurrentHintChange = {
                    currentHint = it
                }
            )

            // First TableStack
            StackPlacement(
                tableStackEntry = TableStackEntry.ONE,
                state = state,
                currentHint = currentHint,
                solitairePlacer = solitairePlacer,
                cardView = cardView,
                cancelHintAnimation = cancelHintAnimation,
                onAction = onAction,
                onCancelHintAnimationChange = {
                    cancelHintAnimation = it
                },
                onCurrentHintChange = {
                    currentHint = it
                }
            )

            // Second TableStack
            StackPlacement(
                tableStackEntry = TableStackEntry.TWO,
                state = state,
                currentHint = currentHint,
                solitairePlacer = solitairePlacer,
                cardView = cardView,
                cancelHintAnimation = cancelHintAnimation,
                onAction = onAction,
                onCancelHintAnimationChange = {
                    cancelHintAnimation = it
                },
                onCurrentHintChange = {
                    currentHint = it
                }
            )

            // Third TableStack
            StackPlacement(
                tableStackEntry = TableStackEntry.THREE,
                state = state,
                currentHint = currentHint,
                solitairePlacer = solitairePlacer,
                cardView = cardView,
                cancelHintAnimation = cancelHintAnimation,
                onAction = onAction,
                onCancelHintAnimationChange = {
                    cancelHintAnimation = it
                },
                onCurrentHintChange = {
                    currentHint = it
                }
            )

            // Fourth TableStack
            StackPlacement(
                tableStackEntry = TableStackEntry.FOUR,
                state = state,
                currentHint = currentHint,
                solitairePlacer = solitairePlacer,
                cardView = cardView,
                cancelHintAnimation = cancelHintAnimation,
                onAction = onAction,
                onCancelHintAnimationChange = {
                    cancelHintAnimation = it
                },
                onCurrentHintChange = {
                    currentHint = it
                }
            )


            // Fifth TableStack
            StackPlacement(
                tableStackEntry = TableStackEntry.FIVE,
                state = state,
                currentHint = currentHint,
                solitairePlacer = solitairePlacer,
                cardView = cardView,
                cancelHintAnimation = cancelHintAnimation,
                onAction = onAction,
                onCancelHintAnimationChange = {
                    cancelHintAnimation = it
                },
                onCurrentHintChange = {
                    currentHint = it
                }
            )


            // Sixth TableStack
            StackPlacement(
                tableStackEntry = TableStackEntry.SIX,
                state = state,
                currentHint = currentHint,
                solitairePlacer = solitairePlacer,
                cardView = cardView,
                cancelHintAnimation = cancelHintAnimation,
                onAction = onAction,
                onCancelHintAnimationChange = {
                    cancelHintAnimation = it
                },
                onCurrentHintChange = {
                    currentHint = it
                }
            )

            // Seventh TableStack
            StackPlacement(
                tableStackEntry = TableStackEntry.SEVEN,
                state = state,
                currentHint = currentHint,
                solitairePlacer = solitairePlacer,
                cardView = cardView,
                cancelHintAnimation = cancelHintAnimation,
                onAction = onAction,
                onCancelHintAnimationChange = {
                    cancelHintAnimation = it
                },
                onCurrentHintChange = {
                    currentHint = it
                }
            )

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
            solitairePlacer.placeDeckCards(this, cardsOnDeckPlaceable, state.game.deckPositions)
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

@Composable
private fun FoundationPlacement(
    suite: CardSuite,
    state: SolitaireState,
    currentHint: SolitaireUserMove?,
    solitairePlacer: SolitairePlacer,
    cardView: @Composable (card: Card, isHidden: Boolean, modifier: Modifier, isSelected: Boolean) -> Unit,
    cancelHintAnimation: SolitaireHintAnimationCancellation?,
    onAction: (SolitaireAction) -> Unit,
    onCancelHintAnimationChange: (SolitaireHintAnimationCancellation?) -> Unit,
    onCurrentHintChange: (SolitaireUserMove?) -> Unit
) {
    val cardTheme = LocalCardTheme.current
    val coroutineScope = rememberCoroutineScope()

    val foundationCards by derivedStateOf { state.game.foundationStack(suite) }

    val layoutId = remember(suite) {
        when (suite) {
            CardSuite.SPADE -> SolitaireLayoutId.FOUNDATION_SPADE
            CardSuite.CLOVER -> SolitaireLayoutId.FOUNDATION_CLOVER
            CardSuite.HEARTS -> SolitaireLayoutId.FOUNDATION_HEARTS
            CardSuite.DIAMOND -> SolitaireLayoutId.FOUNDATION_DIAMOND
        }
    }

    var foundationTopIsAnimating by remember { mutableStateOf(false) }
    var foundationTopIsDragging by remember { mutableStateOf(false) }

    val foundationTopAnimationOffsetX = remember { Animatable(0f) }
    val foundationTopAnimationOffsetY = remember { Animatable(0f) }

    LaunchedEffect(cancelHintAnimation) {
        if (cancelHintAnimation != null && foundationTopIsAnimating) {
            // Cancel any ongoing animations

            when (cancelHintAnimation) {
                SolitaireHintAnimationCancellation.AnimateBack -> {
                    val xBackAnim = async { foundationTopAnimationOffsetX.animateTo(0f) }
                    val yBackAnim = async { foundationTopAnimationOffsetY.animateTo(0f) }
                    awaitAll(xBackAnim, yBackAnim)
                }

                SolitaireHintAnimationCancellation.StopInPlace -> {
                    foundationTopAnimationOffsetX.stop()
                    foundationTopAnimationOffsetY.stop()
                }
            }

            onCancelHintAnimationChange(null)
            foundationTopIsAnimating = false
            onCurrentHintChange(null)
        }
    }

    LaunchedEffect(state, currentHint) {
        // Ensure that the current hint is from foundation, is only one card and specifically from the spade foundation
        if (currentHint is SolitaireUserMove.CardMove && currentHint.from == MoveSource.FromFoundation && currentHint.cards.size == 1) {
            val animatingCard = currentHint.cards.first()
            if (animatingCard.suite == suite) {
                val startXPosition = solitairePlacer.calculateFoundationXPosition(suite)

                when (currentHint.to) {
                    MoveDestination.ToFoundation -> {
                        /* noop: Reason being that a card that cannot move from foundation to foundation */
                    }

                    is MoveDestination.ToTable -> {
                        foundationTopIsAnimating = true
                        currentHint.cards.firstOrNull()?.let { card ->
                            val topLeft =
                                solitairePlacer.calculateTableStackPosition(currentHint.to.tableStackEntry)

                            val xAnim =
                                async { foundationTopAnimationOffsetX.animateTo(topLeft.x.toFloat() - startXPosition) }
                            val yAnim = async {
                                foundationTopAnimationOffsetY.animateTo(
                                    topLeft.y.toFloat() + state.game.tableStack(currentHint.to.tableStackEntry).size * solitairePlacer.individualTableStackYSeparation.toFloat()
                                )
                            }
                            awaitAll(xAnim, yAnim)
                            onCancelHintAnimationChange(SolitaireHintAnimationCancellation.AnimateBack)
                        }
                    }
                }
            }
        }
    }

    // cards on game's Foundation
    foundationCards.forEachIndexed { index, card ->
        cardView(
            card,
            false,
            Modifier
                .size(cardTheme.cardSize)
                .layoutId(layoutId)
                .then(
                    when {
                        foundationTopIsAnimating -> Modifier.zIndex(DraggingZIndex)
                        foundationTopIsDragging -> Modifier.zIndex(DraggingZIndex)

                        else -> Modifier
                    }
                )
                .then(
                    if ((foundationTopIsDragging || foundationTopIsAnimating) && index == foundationCards.size - 1) {
                        Modifier.offset {
                            IntOffset(
                                x = foundationTopAnimationOffsetX.value.roundToInt(),
                                y = foundationTopAnimationOffsetY.value.roundToInt()
                            )
                        }
                    } else
                        Modifier
                )
                .pointerInput(state.game) {
                    if (index == foundationCards.size - 1) {
                        detectDragGestures(
                            onDragStart = {
                                foundationTopIsDragging = true
                                if (foundationTopIsAnimating) {
                                    onCancelHintAnimationChange(SolitaireHintAnimationCancellation.StopInPlace)
                                }
                            },
                            onDragEnd = {
                                coroutineScope.launch {
                                    solitairePlacer.processFoundationMove(
                                        game = state.game,
                                        card = card,
                                        offsetX = foundationTopAnimationOffsetX.value,
                                        offsetY = foundationTopAnimationOffsetY.value,
                                        onMoveSuccess = {
                                            onAction(SolitaireAction.PlayMove(it))
                                            foundationTopIsDragging = false
                                            coroutineScope.launch {
                                                foundationTopAnimationOffsetX.snapTo(0f)
                                                foundationTopAnimationOffsetY.snapTo(0f)
                                            }
                                        },
                                        onMoveFailed = {
                                            foundationTopIsDragging = false
                                            coroutineScope.launch {
                                                foundationTopAnimationOffsetX.snapTo(0f)
                                                foundationTopAnimationOffsetY.snapTo(0f)
                                            }
                                        }
                                    )
                                }
                            }
                        ) { change, dragAmount ->
                            coroutineScope.launch {
                                foundationTopAnimationOffsetX.snapTo(foundationTopAnimationOffsetX.value + dragAmount.x)
                                foundationTopAnimationOffsetY.snapTo(foundationTopAnimationOffsetY.value + dragAmount.y)
                            }
                        }
                    }
                },
            false
        )
    }
}

@Composable
private fun StackPlacement(
    tableStackEntry: TableStackEntry,
    state: SolitaireState,
    currentHint: SolitaireUserMove?,
    solitairePlacer: SolitairePlacer,
    cardView: @Composable (card: Card, isHidden: Boolean, modifier: Modifier, isSelected: Boolean) -> Unit,
    cancelHintAnimation: SolitaireHintAnimationCancellation?,
    onAction: (SolitaireAction) -> Unit,
    onCancelHintAnimationChange: (SolitaireHintAnimationCancellation?) -> Unit,
    onCurrentHintChange: (SolitaireUserMove?) -> Unit
) {
    val cardTheme = LocalCardTheme.current
    val coroutineScope = rememberCoroutineScope()

    /* The drag events are consumed by the card being dragged, this means that the cards below the card on the stack do not catch the drag event.
    *   The offset calculated from the drag event is then stored together with the index of the card being dragged.
    *   The offset is then applied to the card being dragged and all cards with a higher index to represent the stack being dragged. */

    var tableStackAnimatingIndex by remember { mutableStateOf<Int?>(null) }
    var tableStackDraggingIndex by remember { mutableStateOf<Int?>(null) }

    val tableStack by derivedStateOf { state.game.tableStack(tableStackEntry) }

    val layoutId = remember(tableStackEntry) {
        when (tableStackEntry) {
            TableStackEntry.ONE -> SolitaireLayoutId.FIRST_TABLE_STACK
            TableStackEntry.TWO -> SolitaireLayoutId.SECOND_TABLE_STACK
            TableStackEntry.THREE -> SolitaireLayoutId.THIRD_TABLE_STACK
            TableStackEntry.FOUR -> SolitaireLayoutId.FOURTH_TABLE_STACK
            TableStackEntry.FIVE -> SolitaireLayoutId.FIFTH_TABLE_STACK
            TableStackEntry.SIX -> SolitaireLayoutId.SIXTH_TABLE_STACK
            TableStackEntry.SEVEN -> SolitaireLayoutId.SEVENTH_TABLE_STACK
        }
    }

    val tableStackAnimationOffsetX = remember { Animatable(0f) }
    val tableStackAnimationOffsetY = remember { Animatable(0f) }

    LaunchedEffect(cancelHintAnimation) {
        if (cancelHintAnimation != null && tableStackAnimatingIndex != null) {
            // Cancel any ongoing animations

            when (cancelHintAnimation) {
                SolitaireHintAnimationCancellation.AnimateBack -> {
                    val xBackAnim = async { tableStackAnimationOffsetX.animateTo(0f) }
                    val yBackAnim = async { tableStackAnimationOffsetY.animateTo(0f) }
                    awaitAll(xBackAnim, yBackAnim)
                }

                SolitaireHintAnimationCancellation.StopInPlace -> {
                    tableStackAnimationOffsetX.stop()
                    tableStackAnimationOffsetY.stop()
                }
            }

            onCancelHintAnimationChange(null)
            onCurrentHintChange(null)
            tableStackAnimatingIndex = null
        }
    }

    LaunchedEffect(state, currentHint) {
        // Ensure that the current hint is from foundation, is only one card and specifically from the spade foundation
        if (
            currentHint is SolitaireUserMove.CardMove &&
            currentHint.from is MoveSource.FromTable &&
            currentHint.from.tableStackEntry == tableStackEntry
        ) {

            val cardIndex = tableStack.revealedCards.size - currentHint.cards.size
            val startPosition = solitairePlacer.calculateTableStackPosition(tableStackEntry)

            when (currentHint.to) {
                MoveDestination.ToFoundation -> {
                    // Ensure that there is only one card as only one card can be moved to foundation
                    if (currentHint.cards.size == 1) {
                        currentHint.cards.firstOrNull()?.let { card ->
                            tableStackAnimatingIndex = cardIndex
                            val targetXPosition = solitairePlacer.calculateFoundationXPosition(card.suite)

                            val xAnim =
                                async { tableStackAnimationOffsetX.animateTo(targetXPosition - startPosition.x.toFloat()) }
                            val yAnim = async {
                                tableStackAnimationOffsetY.animateTo(
                                    0 - (startPosition.y.toFloat() + (tableStack.size - 1) * solitairePlacer.individualTableStackYSeparation.toFloat())
                                )
                            }
                            awaitAll(xAnim, yAnim)
                            onCancelHintAnimationChange(SolitaireHintAnimationCancellation.AnimateBack)
                        }
                    } else {
                        onCancelHintAnimationChange(SolitaireHintAnimationCancellation.AnimateBack)
                    }
                }

                is MoveDestination.ToTable -> {
                    tableStackAnimatingIndex = cardIndex

                    val targetTopLeftPosition =
                        solitairePlacer.calculateTableStackPosition(currentHint.to.tableStackEntry)

                    val xAnim =
                        async { tableStackAnimationOffsetX.animateTo(targetTopLeftPosition.x.toFloat() - startPosition.x.toFloat()) }
                    val yAnim = async {
                        tableStackAnimationOffsetY.animateTo(
                            (targetTopLeftPosition.y.toFloat() + state.game.tableStack(currentHint.to.tableStackEntry).size * solitairePlacer.individualTableStackYSeparation.toFloat()) -
                                    (startPosition.y.toFloat() + (tableStack.hiddenCards.size + cardIndex) * solitairePlacer.individualTableStackYSeparation.toFloat())
                        )
                    }
                    awaitAll(xAnim, yAnim)
                    onCancelHintAnimationChange(SolitaireHintAnimationCancellation.AnimateBack)
                }

            }
        }
    }

    tableStack.hiddenCards.forEach { card ->
        cardView(
            card,
            true,
            Modifier
                .size(cardTheme.cardSize)
                .layoutId(layoutId),
            false
        )
    }

    tableStack.revealedCards.forEachIndexed { index, card ->
        cardView(
            card,
            false,
            Modifier
                .size(cardTheme.cardSize)
                .layoutId(layoutId)
                .then(
                    when {
                        tableStackAnimatingIndex != null && tableStackAnimatingIndex!! <= index ->
                            Modifier.zIndex(DraggingZIndex)

                        tableStackDraggingIndex != null && tableStackDraggingIndex!! <= index ->
                            Modifier.zIndex(DraggingZIndex)

                        else -> Modifier
                    }
                )
                .then(
                    // the draggingIndex and animatingIndex is not null and the index is lower than or equal to the cards index
                    if (tableStackAnimatingIndex != null || tableStackDraggingIndex != null) {
                        if (tableStackAnimatingIndex != null && tableStackDraggingIndex != null) {
                            // This should not happen, however I cannot dismiss the possibility of it occurring especially during state changes
                            if (tableStackAnimatingIndex == tableStackDraggingIndex) {
                                tableStackAnimatingIndex = null

                                Modifier.offset {
                                    IntOffset(
                                        x = tableStackAnimationOffsetX.value.roundToInt(),
                                        y = tableStackAnimationOffsetY.value.roundToInt()
                                    )
                                }
                            } else {
                                tableStackAnimatingIndex = null
                                tableStackDraggingIndex = null

                                coroutineScope.launch {
                                    val x = async { tableStackAnimationOffsetX.snapTo(0f) }
                                    val y = async { tableStackAnimationOffsetY.snapTo(0f) }
                                    awaitAll(x, y)
                                }

                                Modifier
                            }
                        } else {
                            (tableStackAnimatingIndex ?: tableStackDraggingIndex)?.let { topIndex ->
                                if (index >= topIndex) {
                                    Modifier.offset {
                                        IntOffset(
                                            x = tableStackAnimationOffsetX.value.roundToInt(),
                                            y = tableStackAnimationOffsetY.value.roundToInt()
                                        )
                                    }
                                } else {
                                    Modifier
                                }
                            } ?: Modifier
                        }

                    } else
                        Modifier
                )
                .pointerInput(state.game) {
                    if (index == tableStack.revealedCards.size - 1) {
                        detectTapGestures(
                            onDoubleTap = {
                                val move = card move tableStackEntry to MoveDestination.ToFoundation
                                if (move.isValid(state.game)) {
                                    onAction(SolitaireAction.PlayMove(move))
                                }
                            }
                        )
                    }
                }
                .pointerInput(state.game) {
                    detectDragGestures(
                        onDragStart = {
                            tableStackAnimatingIndex?.let {
                                onCancelHintAnimationChange(SolitaireHintAnimationCancellation.StopInPlace)
                            }
                            tableStackDraggingIndex = index
                        },
                        onDragEnd = {
                            coroutineScope.launch {
                                solitairePlacer.processTableStackMove(
                                    game = state.game,
                                    tableStackEntry = tableStackEntry,
                                    index = index,
                                    offsetX = tableStackAnimationOffsetX.value,
                                    offsetY = tableStackAnimationOffsetY.value,
                                    onMoveSuccess = {
                                        onAction(SolitaireAction.PlayMove(it))
                                        tableStackDraggingIndex = null
                                        coroutineScope.launch {
                                            tableStackAnimationOffsetX.snapTo(0f)
                                            tableStackAnimationOffsetY.snapTo(0f)
                                        }
                                    },
                                    onMoveFailed = {
                                        tableStackDraggingIndex = null
                                        coroutineScope.launch {
                                            tableStackAnimationOffsetX.snapTo(0f)
                                            tableStackAnimationOffsetY.snapTo(0f)
                                        }
                                    }
                                )
                            }
                        }
                    ) { change, dragAmount ->
                        coroutineScope.launch {
                            tableStackAnimationOffsetX.snapTo(tableStackAnimationOffsetX.value + dragAmount.x)
                            tableStackAnimationOffsetY.snapTo(tableStackAnimationOffsetY.value + dragAmount.y)
                        }

                        change.consume()
                    }
                },
            false
        )
    }
}

private val HeightSpacing = 32.dp

private const val DraggingZIndex = 5.0f
private const val NormalZIndex = 1.0f

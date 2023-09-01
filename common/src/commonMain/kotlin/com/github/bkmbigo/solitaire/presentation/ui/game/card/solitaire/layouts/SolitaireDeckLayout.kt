package com.github.bkmbigo.solitaire.presentation.ui.game.card.solitaire.layouts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.*
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.zIndex
import com.github.bkmbigo.solitaire.models.core.Card
import com.github.bkmbigo.solitaire.presentation.ui.core.locals.cardtheme.LocalCardTheme
import com.github.bkmbigo.solitaire.presentation.ui.game.card.solitaire.components.deck.DeckOverlay
import com.github.bkmbigo.solitaire.presentation.ui.game.card.solitaire.components.deck.EmptyDeck
import kotlin.math.roundToInt

@Composable
fun SolitaireDeckLayout(
    deckPosition: Int,
    cards: List<Card>,
    cardView: @Composable (card: Card, isHidden:Boolean, modifier: Modifier, isSelected: Boolean) -> Unit,
    onDeal: () -> Unit,
    modifier: Modifier = Modifier
) {
    val cardTheme = LocalCardTheme.current

    Layout(
        modifier = modifier,
        content = {
            // Surface for empty deck.
            EmptyDeck(
                modifier = Modifier
                    .size(cardTheme.cardSize)
            )

            // Overlay on top of deck. Handles deal() events
            DeckOverlay(
                onDeal = onDeal,
                modifier = Modifier.size(cardTheme.cardSize)
            )

            cards.forEach { card ->

                var isDragging by remember { mutableStateOf(false) }
                var offsetX by remember { mutableStateOf(0f) }
                var offsetY by remember { mutableStateOf(0f) }

                cardView(
                    card,
                    false,
                    Modifier
                        .size(cardTheme.cardSize)
                        .then(
                            if (isDragging) {
                                Modifier.zIndex(5.0f)
                            } else Modifier
                        )
                        .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDragStart = {
                                    isDragging = true
                                },
                                onDragEnd = {
                                    isDragging = false
                                    offsetX = 0f
                                    offsetY = 0f
                                }
                            ) { change, dragAmount ->
                                offsetX += dragAmount.x
                                offsetY += dragAmount.y
                            }
                        },
                    false
                )
            }
        },
        measurePolicy = object : MeasurePolicy {
            override fun MeasureScope.measure(measurables: List<Measurable>, constraints: Constraints): MeasureResult {
                val cardWidth = cardTheme.cardSize.width.roundToPx()
                val cardHeight = cardTheme.cardSize.height.roundToPx()

                val deckSeparation = cardWidth * 3/4
                val cardStackSeparation = cardTheme.horizontalCardStackSeparation.roundToPx()

                val deckWidth = cardWidth * 2 + cardStackSeparation + deckSeparation

                val placeables = measurables.map { it.measure(constraints) }

                return layout(deckWidth, cardHeight) {

                    when {
                        deckPosition == 0 -> {
                            // All cards are hidden
                            placeables.forEachIndexed { index, placeable ->
                                placeable.place(0, 0, zIndex = if(index == 1) 2f else 0f)
                            }
                        }
                        deckPosition == 1 -> {
                            // One card is revealed
                            placeables.forEachIndexed { index, placeable ->
                                if (index == placeables.size - 1) {
                                    placeable.place(cardWidth + deckSeparation , 0, 1f)
                                } else {
                                    placeable.place(0, 0, zIndex = if(index == 1) 2f else 0f)
                                }
                            }
                        }
                        deckPosition == 2 -> {
                            // Two cards are revealed
                            placeables.forEachIndexed { index, placeable ->
                                when (index) {
                                    placeables.size - 1 -> {
                                        placeable.place(cardWidth + deckSeparation, 0, 0f)
                                    }
                                    placeables.size - 2 -> {
                                        placeable.place(cardWidth + deckSeparation + cardStackSeparation, 0, 1f)
                                    }
                                    1 -> {
                                        placeable.place(0, 0, 2f)
                                    }
                                    0 -> {
                                        placeable.place(0, 0, 0f)
                                    }
                                    else -> {
                                        placeable.place(0, 0, 1f)
                                    }
                                }
                            }
                        }
                        else -> {
                            // More than two cards are revealed.
                            val topCardIndex = placeables.size - deckPosition

                            placeables.forEachIndexed { index, placeable ->
                                when {
                                    index == topCardIndex + 2 -> {
                                        placeable.place(cardWidth + deckSeparation, 0, 0f)
                                    }
                                    index == topCardIndex + 1 -> {
                                        placeable.place(cardWidth + deckSeparation + cardStackSeparation, 0, 0.25f)
                                    }
                                    index == topCardIndex -> {
                                        placeable.place(cardWidth + deckSeparation + cardStackSeparation * 2, 0, 0.5f)
                                    }
                                    index == 1 -> {
                                        placeable.place(0, 0, 1f)
                                    }
                                    index == 0 -> {
                                        placeable.place(0, 0, 0.2f)
                                    }
                                    index > topCardIndex -> {
                                        placeable.place(0, 0, 0f)
                                    }
                                    else -> {
                                        placeable.place(0, 0, 0.5f)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            override fun IntrinsicMeasureScope.minIntrinsicWidth(
                measurables: List<IntrinsicMeasurable>,
                height: Int
            ): Int {
                val cardWidth = cardTheme.cardSize.width.roundToPx()

                val deckSeparation = cardWidth * 3/4
                val cardStackSeparation = cardTheme.horizontalCardStackSeparation.roundToPx()

                return cardWidth * 3 + deckSeparation + cardStackSeparation * 2
            }

            override fun IntrinsicMeasureScope.minIntrinsicHeight(
                measurables: List<IntrinsicMeasurable>,
                width: Int
            ): Int =  cardTheme.cardSize.height.roundToPx()
        }

    )
}

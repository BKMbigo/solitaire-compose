package com.github.bkmbigo.solitaireanimation.presentation.layouts

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.times
import com.github.bkmbigo.solitaireanimation.models.Card
import com.github.bkmbigo.solitaireanimation.presentation.components.card.CardView
import com.github.bkmbigo.solitaireanimation.presentation.locals.cardtheme.LocalCardTheme
import com.github.bkmbigo.solitaireanimation.presentation.screens.solitaire.state.TableStackState

@Composable
fun CardStackLayout(
    tableStackState: TableStackState,
    modifier: Modifier = Modifier
) {
    CardStackLayout(
        cards = tableStackState.cards,
        modifier = modifier,
        orientation = Orientation.Vertical,
        flippedCards = tableStackState.flippedCells,
        selectedCards = tableStackState.selectedCells
    )
}

@Composable
fun CardStackLayout(
    cards: List<Card>,
    modifier: Modifier = Modifier,
    orientation: Orientation = Orientation.Vertical,
    flippedCards: Int = cards.size,
    selectedCards: Int = 0
) {
    val cardTheme = LocalCardTheme.current

    Layout(
        modifier = modifier,
        content = {
            cards.forEachIndexed { index, card ->
                CardView(
                    card,
                    isFlipped = index >= (cards.size - flippedCards),
                    modifier = Modifier,
                    isSelected = index >= (cards.size - selectedCards),
                )
            }
        },
        measurePolicy = { measurables, constraints ->
            val placeables = measurables.map {
                it.measure(
                    Constraints.fixed(
                        cardTheme.cardSize.width.roundToPx(),
                        cardTheme.cardSize.height.roundToPx()
                    )
                )
            }

            layout(
                width = if (orientation == Orientation.Vertical) {
                    cardTheme.cardSize.width.roundToPx()
                } else {
                    ((measurables.size - 1) * cardTheme.horizontalCardStackSeparation + cardTheme.cardSize.width).roundToPx()
                },
                height = if (orientation == Orientation.Vertical) {
                    ((measurables.size - 1) * cardTheme.verticalCardStackSeparation + cardTheme.cardSize.height).roundToPx()
                } else {
                    cardTheme.cardSize.height.roundToPx()
                }
            ) {
                placeables.forEachIndexed { index, placeable ->
                    placeable.place(
                        x = if (orientation == Orientation.Vertical) 0 else (index * cardTheme.horizontalCardStackSeparation).roundToPx(),
                        y = if (orientation == Orientation.Horizontal) 0 else (index * cardTheme.verticalCardStackSeparation).roundToPx(),
                    )
                }
            }
        }
    )

}
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
    cardView: @Composable (Card, Boolean, Modifier, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    CardStackLayout(
        cards = tableStackState.cards,
        modifier = modifier,
        cardView = cardView,
        orientation = Orientation.Vertical,
        flippedCards = tableStackState.flippedCards,
        selectedCards = tableStackState.selectedCards
    )
}

@Composable
fun CardStackLayout(
    cards: List<Card>,
    modifier: Modifier = Modifier,
    cardView: @Composable (Card, Boolean, Modifier, Boolean) -> Unit,
    orientation: Orientation = Orientation.Vertical,
    flippedCards: Int = cards.size,
    selectedCards: Int = 0
) {
    val cardTheme = LocalCardTheme.current

    Layout(
        modifier = modifier,
        content = {
            cards.forEachIndexed { index, card ->
                cardView(
                    card,
                    index >= (cards.size - flippedCards),
                    Modifier,
                    index >= (cards.size - selectedCards),
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
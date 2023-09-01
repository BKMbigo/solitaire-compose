package com.github.bkmbigo.solitaire.presentation.ui.game.card.core.layouts

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.times
import com.github.bkmbigo.solitaire.models.core.Card
import com.github.bkmbigo.solitaire.presentation.ui.core.locals.cardtheme.LocalCardTheme

@Composable
fun LinearCardStackLayout(
    hiddenCards: List<Card>,
    revealedCards: List<Card>,
    modifier: Modifier = Modifier,
    cardView: @Composable (Card, Boolean, Modifier, Boolean) -> Unit,
    orientation: Orientation = Orientation.Vertical,
    selectedCards: Int = 0
) {
    val cardTheme = LocalCardTheme.current

    Layout(
        modifier = modifier,
        content = {
            hiddenCards.forEach { card ->
                cardView(
                    card,
                    true,
                    Modifier,
                    false
                )
            }
            revealedCards.forEachIndexed { index, card ->
                cardView(
                    card,
                    false,
                    Modifier,
                    index >= (revealedCards.size - selectedCards),
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

package com.github.bkmbigo.solitaireanimation.presentation.components.deck

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.dp
import com.github.bkmbigo.solitaireanimation.models.Card
import com.github.bkmbigo.solitaireanimation.presentation.locals.cardtheme.LocalCardTheme
import com.github.bkmbigo.solitaireanimation.utils.Logger

@Composable
fun SolitaireDeck(
    stock: List<Card>,
    cardView: @Composable (Card, Boolean, Modifier, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val cardTheme = LocalCardTheme.current
    val materialColorScheme = MaterialTheme.colorScheme

    val deckManager = rememberSolitaireDeckManager(stock)
    val deckState by deckManager.deckState.collectAsState()

    Row(modifier = modifier.height(cardTheme.cardSize.height)) {
        Box(
            modifier = Modifier
                .size(cardTheme.cardSize)
                .clip(RoundedCornerShape(5.dp))
                .clipToBounds()
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .drawBehind {
                        drawCircle(
                            color = materialColorScheme.primary,
                            radius = size.minDimension * 0.25f
                        )
                        drawCircle(
                            color = cardTheme.gameBackground,
                            radius = size.minDimension * 0.2f
                        )
                    },
                shape = RoundedCornerShape(5.dp),
                border = BorderStroke(1.dp, if (cardTheme.isDark) Color.White else Color.Black),
                color = Color.Transparent
            ) { }
            deckState.uncoveredStack.forEach { uncoveredCard ->
                cardView(
                    uncoveredCard,
                    false,
                    Modifier,
                    false
                )
            }
            Box(
                modifier = Modifier.fillMaxSize()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        Logger.LogInfo("SolitaireDeck", "DeckManagerClicked")
                        deckManager.deal()
                        Logger.LogInfo("SolitaireDeck", "DeckManagerClicked")
                    }
            )
        }

        Spacer(modifier = Modifier.width(4.dp))

        RevealedDeckLayout(
            revealedCards = deckState.openStack,
            cardView = cardView,
            modifier = Modifier
        )
    }
}

@Composable
private fun RevealedDeckLayout(
    revealedCards: List<Card>,
    cardView: @Composable (Card, Boolean, Modifier, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val cardTheme = LocalCardTheme.current

    Layout(
        modifier = modifier,
        content = {
            revealedCards.forEach { revealedCard ->
                cardView(
                    revealedCard,
                    true,
                    Modifier,
                    false
                )
            }
        }
    ) { measurables, constraints ->
        val placeables = measurables.map { it.measure(constraints) }

        layout(constraints.maxWidth, constraints.maxHeight) {
            when {
                placeables.isEmpty() -> {}
                placeables.size == 1 -> {
                    placeables.forEach { it.place(0, 0) }
                }

                placeables.size == 2 -> {
                    placeables.forEachIndexed { index, placeable ->
                        if (index == 0) {
                            placeable.place(0, 0)
                        } else {
                            placeable.place(cardTheme.horizontalCardStackSeparation.roundToPx(), 0)
                        }
                    }
                }

                else -> {
                    val lastPlaceables = placeables.takeLast(2)
                    val remainingPlaceables = placeables.dropLast(2)

                    remainingPlaceables.forEach { placeable ->
                        placeable.place(0, 0)
                    }

                    lastPlaceables.forEachIndexed { index, placeable ->
                        if (index == 0) {
                            placeable.place(cardTheme.horizontalCardStackSeparation.roundToPx(), 0)
                        } else {
                            placeable.place(
                                cardTheme.horizontalCardStackSeparation.roundToPx() * 2,
                                0
                            )
                        }
                    }
                }
            }
        }
    }

}
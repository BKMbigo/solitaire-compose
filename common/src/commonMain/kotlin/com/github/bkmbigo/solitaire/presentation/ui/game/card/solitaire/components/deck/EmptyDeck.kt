package com.github.bkmbigo.solitaire.presentation.ui.game.card.solitaire.components.deck

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.bkmbigo.solitaire.presentation.ui.core.locals.cardtheme.LocalCardTheme

@Composable
fun EmptyDeck(
    modifier: Modifier = Modifier
) {
    val cardTheme = LocalCardTheme.current
    val materialColorScheme = MaterialTheme.colorScheme

    Surface(
        modifier = modifier,
        border = BorderStroke(1.dp, Color.Black),
        shape = RoundedCornerShape(5.dp),
        color = cardTheme.gameBackground
    ) {
        Box(
            modifier = Modifier
                .drawBehind {
                    drawCircle(
                        color = materialColorScheme.primary,
                        radius = size.minDimension * 0.25f
                    )
                    drawCircle(
                        color = cardTheme.gameBackground,
                        radius = size.minDimension * 0.2f
                    )
                }
        )
    }
}

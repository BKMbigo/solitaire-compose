package com.github.bkmbigo.solitaireanimation.presentation.components.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.bkmbigo.solitaireanimation.models.Card
import com.github.bkmbigo.solitaireanimation.presentation.locals.cardtheme.LocalCardTheme
import com.github.bkmbigo.solitaireanimation.presentation.utils.images.vectorResourceCached

@Composable
fun CardView(
    card: Card,
    isFlipped: Boolean,
    modifier: Modifier
) {
    val cardTheme = LocalCardTheme.current

    if(cardTheme.useMiniCards) {
        MiniCard(
           card = card,
            isFlipped = isFlipped,
            modifier = modifier
        )
    } else {
        FullCard(
            card = card,
            isFlipped = isFlipped,
            modifier = modifier
        )
    }
}

@Composable
internal fun FullCard(
    card: Card,
    isFlipped: Boolean,
    modifier: Modifier = Modifier
) {
    val cardTheme = LocalCardTheme.current

    Surface(
        modifier = modifier,
        border = BorderStroke(1.dp, Color.Black),
        color = cardTheme.cardFrontBackground,
        shape = RoundedCornerShape(5.dp),
        shadowElevation = 8.dp
    ) {
        if(isFlipped) {
            Image(
                painter = vectorResourceCached(Card.cardBackFilename),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Image(
                painter = vectorResourceCached(if(cardTheme.isDark && card.darkImageFilename != null) card.darkImageFilename else card.imageFilename),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
package com.github.bkmbigo.solitaireanimation.presentation.components.card

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.bkmbigo.solitaireanimation.models.Card
import com.github.bkmbigo.solitaireanimation.presentation.locals.cardtheme.LocalCardTheme

@Composable
fun CardView(
    card: Card,
    isFlipped: Boolean,
    modifier: Modifier
) {
    val cardTheme = LocalCardTheme.current

    if (cardTheme.useMiniCards) {
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
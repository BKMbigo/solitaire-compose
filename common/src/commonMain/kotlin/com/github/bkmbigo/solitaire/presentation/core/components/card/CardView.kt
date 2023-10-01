package com.github.bkmbigo.solitaire.presentation.core.components.card

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.bkmbigo.solitaire.models.core.Card
import com.github.bkmbigo.solitaire.presentation.core.locals.cardtheme.LocalCardTheme

@Composable
fun CardView(
    card: Card,
    isHidden: Boolean,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false
) {
    val cardTheme = LocalCardTheme.current

    if (cardTheme.useMiniCards) {
        MiniCard(
            card = card,
            isHidden = isHidden,
            modifier = modifier,
            isSelected = isSelected
        )
    } else {
        FullCard(
            card = card,
            isHidden = isHidden,
            modifier = modifier,
            isSelected = isSelected
        )
    }
}

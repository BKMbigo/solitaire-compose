package com.github.bkmbigo.solitaire.presentation.ui.game.card.solitaire.layouts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import com.github.bkmbigo.solitaire.models.core.Card
import com.github.bkmbigo.solitaire.models.core.CardSuite
import com.github.bkmbigo.solitaire.presentation.ui.core.locals.cardtheme.LocalCardTheme
import com.github.bkmbigo.solitaire.presentation.ui.game.card.solitaire.components.foundation.EmptyFoundation

@Composable
fun SolitaireFoundationLayout(
    suite: CardSuite,
    cards: List<Card>,
    cardView: @Composable (card: Card, isHidden: Boolean, Modifier, isSelected: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val cardTheme = LocalCardTheme.current

    Box(
        modifier = modifier.size(cardTheme.cardSize)
    ) {
        EmptyFoundation(
            suite = suite,
            modifier = Modifier
                .fillMaxSize()
                .zIndex(1.0f)
        )
        cards.forEach { cardView(it, false, Modifier.size(cardTheme.cardSize), false) }
    }
}

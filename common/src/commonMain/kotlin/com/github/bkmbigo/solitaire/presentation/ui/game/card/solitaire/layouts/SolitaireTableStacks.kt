package com.github.bkmbigo.solitaire.presentation.ui.game.card.solitaire.layouts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.bkmbigo.solitaire.game.solitaire.SolitaireGame
import com.github.bkmbigo.solitaire.models.core.Card
import com.github.bkmbigo.solitaire.models.solitaire.TableStackEntry
import com.github.bkmbigo.solitaire.presentation.ui.game.card.core.layouts.LinearCardStackLayout

@Composable
internal fun SolitaireTableStacks(
    game: SolitaireGame,
    cardView: @Composable (Card, Boolean, Modifier, Boolean) -> Unit,
    modifier: Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        TableStackEntry.entries.forEach { entry ->
            LinearCardStackLayout(
                hiddenCards = game.tableStack(entry).hiddenCards,
                revealedCards = game.tableStack(entry).revealedCards,
                modifier = Modifier,
                cardView = cardView
            )
        }
    }
}

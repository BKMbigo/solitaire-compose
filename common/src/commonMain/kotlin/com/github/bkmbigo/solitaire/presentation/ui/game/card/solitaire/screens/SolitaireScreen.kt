package com.github.bkmbigo.solitaire.presentation.ui.game.card.solitaire.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.github.bkmbigo.solitaire.game.solitaire.SolitaireGame
import com.github.bkmbigo.solitaire.models.core.Card
import com.github.bkmbigo.solitaire.models.core.CardSuite
import com.github.bkmbigo.solitaire.models.solitaire.TableStackEntry
import com.github.bkmbigo.solitaire.presentation.ui.core.locals.cardtheme.LocalCardTheme
import com.github.bkmbigo.solitaire.presentation.ui.game.card.core.components.card.CardView
import com.github.bkmbigo.solitaire.presentation.ui.game.card.core.layouts.LinearCardStackLayout
import com.github.bkmbigo.solitaire.presentation.ui.game.card.solitaire.layouts.SolitaireDeckLayout
import com.github.bkmbigo.solitaire.presentation.ui.game.card.solitaire.layouts.SolitaireFoundationLayout
import com.github.bkmbigo.solitaire.presentation.ui.game.card.solitaire.layouts.SolitaireGameLayout


expect object SolitaireScreen

@Composable
fun SolitaireGameScreenContent(
    state: SolitaireState,
    onAction: (SolitaireAction) -> Unit,
    onNavigateBack: () -> Unit
) {
    val cardTheme = LocalCardTheme.current

    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = cardTheme.gameBackground
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = { onNavigateBack() }
                ) {
                    Icon(
                        imageVector = Icons.Filled.ChevronLeft,
                        contentDescription = null
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { onAction(SolitaireAction.StartNewGame) }
                    ) {
                        Text(
                            text = "Start New Game"
                        )
                    }

                    Button(
                        onClick = { /*onAction(SolitaireGameAction.UndoLastMove())*/ },
                        enabled = false
                    ) {
                        Text(
                            text = "Undo"
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            SolitaireGameLayout(
                state = state,
                onAction = onAction,
                cardView = { card, isHidden, modifier, isSelected ->
                   CardView(
                       card,
                       isHidden,
                       modifier,
                       isSelected
                   )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, true)
                    .padding(horizontal = 16.dp)
            )

            Row {

            }
        }
    }
}

@Composable
private fun SolitaireTableStacks(
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

@Composable
private fun SolitaireFoundations(
    game: SolitaireGame,
    cardView: @Composable (Card, Boolean, Modifier, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CardSuite.entries.forEach {
            SolitaireFoundationLayout(
                it,
                game.foundationStack(it),
                cardView,
                Modifier.zIndex(1.0f)
            )
        }
    }
}

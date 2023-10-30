package com.github.bkmbigo.solitaire.presentation.solitaire.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.bkmbigo.solitaire.game.solitaire.moves.SolitaireUserMove
import com.github.bkmbigo.solitaire.presentation.core.components.card.CardView
import com.github.bkmbigo.solitaire.presentation.core.layouts.DialogScreen
import com.github.bkmbigo.solitaire.presentation.core.locals.cardtheme.LocalCardTheme
import com.github.bkmbigo.solitaire.presentation.solitaire.components.dialog.SolitaireGameCreationDialog
import com.github.bkmbigo.solitaire.presentation.solitaire.components.dialog.SolitaireGameDrawnDialog
import com.github.bkmbigo.solitaire.presentation.solitaire.components.dialog.SolitaireGameWonDialog
import com.github.bkmbigo.solitaire.presentation.solitaire.layouts.SolitaireGameLayout
import kotlinx.coroutines.flow.Flow


expect object SolitaireScreen

@Composable
fun SolitaireGameScreenContent(
    state: SolitaireState,
    hint: Flow<SolitaireUserMove>,
    onAction: (SolitaireAction) -> Unit,
    onNavigateBack: () -> Unit
) {
    val cardTheme = LocalCardTheme.current

    var showGameCreationDialog by remember { mutableStateOf(true) }
    var showGameWonDialog by remember { mutableStateOf(false) }
    var showGameDrawnDialog by remember { mutableStateOf(false) }

    var ignoreDraw = remember { false }

    LaunchedEffect(state) {
        if (state.game.isWon()) {
            showGameWonDialog = true
        } else if (state.game.isDrawn() && !ignoreDraw) {
            showGameDrawnDialog = true
        }
    }

    DialogScreen(
        isDialogOpen = showGameCreationDialog || showGameWonDialog || showGameDrawnDialog,
        onDismissRequest = {
            showGameCreationDialog = false
            showGameWonDialog = false
            showGameDrawnDialog = false
        },
        dialog = {
            if (showGameCreationDialog) {
                SolitaireGameCreationDialog(
                    onConfigurationSet = { solitaireGameProvider, solitaireCardsPerDeal ->
                        onAction(SolitaireAction.StartNewGame(solitaireGameProvider, solitaireCardsPerDeal))
                        showGameCreationDialog = false
                    },
                    onDismissRequest = {
                        showGameCreationDialog = false
                    }
                )
            }

            if (showGameWonDialog) {
                SolitaireGameWonDialog(
                    onCreateNewGame = {
                        showGameWonDialog = false
                        showGameCreationDialog = true
                    },
                    onUndoLastMove = {
                        onAction(SolitaireAction.UndoLastMove)
                        showGameWonDialog = false
                    },
                    onDismissRequest = {
                        showGameWonDialog = false
                    }

                )
            }

            if (showGameDrawnDialog) {
                SolitaireGameDrawnDialog(
                    onContinue = {
                        ignoreDraw = true
                        showGameDrawnDialog = false
                    },
                    onCreateNewGame = {
                        ignoreDraw = true
                        showGameDrawnDialog = false
                        showGameCreationDialog = true
                    },
                    onUndoLastMove = {
                        ignoreDraw = true
                        onAction(SolitaireAction.UndoLastMove)
                        showGameDrawnDialog = false
                    },
                    onDismissRequest = {
                        ignoreDraw = true
                        showGameDrawnDialog = false
                    }

                )
            }
        }
    ) {
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
                            onClick = { showGameCreationDialog = true },
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "New Game"
                            )
                        }

                        Button(
                            onClick = { onAction(SolitaireAction.OfferHint) },
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "Offer Hint"
                            )
                        }

                        Button(
                            onClick = { onAction(SolitaireAction.UndoLastMove) },
                            enabled = state.canUndo,
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "Undo"
                            )
                        }

                        Button(
                            onClick = { onAction(SolitaireAction.RedoLastMove) },
                            enabled = state.canRedo,
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "Redo"
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                SolitaireGameLayout(
                    state = state,
                    hint = hint,
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

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "*The project is still in active development"
                    )
                }
            }
        }
    }
}

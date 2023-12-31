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
import com.github.bkmbigo.solitaire.presentation.solitaire.components.dialog.leaderboard.SolitaireLeaderboardDialog
import com.github.bkmbigo.solitaire.presentation.solitaire.components.dialog.leaderboard.SolitaireLeaderboardDialogAction
import com.github.bkmbigo.solitaire.presentation.solitaire.components.duration.SolitaireDurationText
import com.github.bkmbigo.solitaire.presentation.solitaire.components.points.SolitairePointText
import com.github.bkmbigo.solitaire.presentation.solitaire.layouts.SolitaireGameLayout
import com.github.bkmbigo.solitaire.presentation.solitaire.screens.state.SolitaireState
import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration


expect object SolitaireScreen

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SolitaireGameScreenContent(
    state: SolitaireState,
    gameTime: Duration,
    score: Int,
    hint: Flow<SolitaireUserMove>,
    onAction: (SolitaireAction) -> Unit,
    onNavigateBack: () -> Unit
) {
    val cardTheme = LocalCardTheme.current

    var showGameCreationDialog by remember { mutableStateOf(true) }
    var showGameWonDialog by remember { mutableStateOf(false) }
    var showGameDrawnDialog by remember { mutableStateOf(false) }

    var ignoreDraw by remember { mutableStateOf(false) }

    LaunchedEffect(state) {
        if (state.game.isWon()) {
            showGameWonDialog = true
        } else if (state.game.isDrawn() && !ignoreDraw) {
            showGameDrawnDialog = true
        }
    }

    DialogScreen(
        isDialogOpen = showGameCreationDialog || showGameWonDialog || showGameDrawnDialog || state.showLeaderboardDialog != null,
        onDismissRequest = {
            showGameCreationDialog = false
            showGameWonDialog = false
            showGameDrawnDialog = false
            onAction(SolitaireAction.HideLeaderboardDialog)
        },
        dialog = {
            if (showGameCreationDialog) {
                SolitaireGameCreationDialog(
                    onConfigurationSet = { solitaireGameProvider, solitaireCardsPerDeal ->
                        onAction(SolitaireAction.StartNewGame(solitaireGameProvider, solitaireCardsPerDeal))
                        ignoreDraw = false
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
                    },
                    onSubmitToLeaderboard = {
                        showGameWonDialog = false
                        onAction(SolitaireAction.ShowLeaderboardDialogAfterWin(platform = cardTheme.platform))
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
                        showGameDrawnDialog = false
                        showGameCreationDialog = true
                    },
                    onUndoLastMove = {
                        onAction(SolitaireAction.UndoLastMove)
                        showGameDrawnDialog = false
                    },
                    onDismissRequest = {
                        ignoreDraw = true
                        showGameDrawnDialog = false
                    }

                )
            }

            if (state.showLeaderboardDialog != null) {
                SolitaireLeaderboardDialog(
                    state = state.showLeaderboardDialog,
                    onAction = { action ->
                        when (action) {
                            SolitaireLeaderboardDialogAction.DismissDialog -> onAction(SolitaireAction.HideLeaderboardDialog)
                            is SolitaireLeaderboardDialogAction.FilterByLeaderboard -> {
                                onAction(
                                    SolitaireAction.FilterLeaderboard(
                                        leaderboard = action.leaderboardName
                                    )
                                )
                            }

                            SolitaireLeaderboardDialogAction.GoToNextPage -> {}
                            is SolitaireLeaderboardDialogAction.SubmitLeaderboardScore ->
                                onAction(
                                    SolitaireAction.SubmitLeaderboardScore(
                                        playerName = action.playerName,
                                        leaderboard = action.customLeaderboardName,
                                        platform = action.platform
                                    )
                                )
                        }
                    },
                    modifier = Modifier
                        .fillMaxHeight(0.75f)
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
                        onClick = {
                            onNavigateBack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ChevronLeft,
                            contentDescription = null
                        )
                    }

                    FlowRow(
                        horizontalArrangement = Arrangement.End,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = { onAction(SolitaireAction.ShowLeaderboardOnlyDialog) },
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier.padding(horizontal = 4.dp)
                        ) {
                            Text(
                                text = "Show Leaderboard"
                            )
                        }

                        Button(
                            onClick = { showGameCreationDialog = true },
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier.padding(horizontal = 4.dp)
                        ) {
                            Text(
                                text = "New Game"
                            )
                        }

                        Button(
                            onClick = { onAction(SolitaireAction.OfferHint) },
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier.padding(horizontal = 4.dp)
                        ) {
                            Text(
                                text = "Offer Hint"
                            )
                        }

                        Button(
                            onClick = { onAction(SolitaireAction.UndoLastMove) },
                            enabled = state.canUndo,
                            modifier = Modifier.padding(horizontal = 4.dp),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "Undo"
                            )
                        }

                        Button(
                            onClick = { onAction(SolitaireAction.RedoLastMove) },
                            enabled = state.canRedo,
                            modifier = Modifier.padding(horizontal = 4.dp),
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

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "*The project is still in active development"
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        SolitaireDurationText(
                            duration = gameTime
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        SolitairePointText(
                            points = score
                        )
                    }
                }
            }
        }
    }
}

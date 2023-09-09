package com.github.bkmbigo.solitaire.presentation.ui.game.card.solitaire.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.github.bkmbigo.solitaire.game.solitaire.moves.dsl.move
import com.github.bkmbigo.solitaire.game.solitaire.providers.VeryEasySolitaireGameProvider

actual object SolitaireScreen: Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val screenModel = rememberScreenModel { SolitaireScreenModel() }

        val state by screenModel.state.collectAsState()

        SolitaireGameScreenContent(
            state = state,
            onNavigateBack = { navigator?.pop() },
            onAction = { action ->
                when (action) {
                    is SolitaireAction.PlayMove -> { screenModel.play(action.move) }
                    SolitaireAction.StartNewGame -> { screenModel.createGame(VeryEasySolitaireGameProvider) }
                    SolitaireAction.Deal -> { screenModel.deal() }
                    SolitaireAction.RedoLastMove -> { screenModel.redo() }
                    SolitaireAction.UndoLastMove -> { screenModel.undo() }
                }
            }
        )

    }
}

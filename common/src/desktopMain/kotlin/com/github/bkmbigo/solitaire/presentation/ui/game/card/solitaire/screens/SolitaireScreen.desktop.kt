package com.github.bkmbigo.solitaire.presentation.ui.game.card.solitaire.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.github.bkmbigo.solitaire.game.solitaire.providers.VeryEasySolitaireGameProvider
import com.github.bkmbigo.solitaire.models.core.CardRank
import com.github.bkmbigo.solitaire.models.core.CardSuite
import com.github.bkmbigo.solitaire.models.core.utils.of
import com.github.bkmbigo.solitaire.presentation.ui.game.card.core.components.card.CardView

actual object SolitaireScreen: Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val coroutineScope = rememberCoroutineScope()

        val screenModel = rememberScreenModel { SolitaireScreenModel(coroutineScope) }

        val state by screenModel.state.collectAsState()

        SolitaireGameScreenContent(
            state = state,
            hint = screenModel.hint,
            onNavigateBack = { navigator?.pop() },
            onAction = { action ->
                when (action) {
                    is SolitaireAction.PlayMove -> { screenModel.play(action.move) }
                    SolitaireAction.StartNewGame -> { screenModel.createGame(VeryEasySolitaireGameProvider) }
                    SolitaireAction.Deal -> { screenModel.deal() }
                    SolitaireAction.RedoLastMove -> { screenModel.redo() }
                    SolitaireAction.UndoLastMove -> { screenModel.undo() }
                    SolitaireAction.OfferHint -> { screenModel.offerHint() }
                }
            }
        )

    }
}

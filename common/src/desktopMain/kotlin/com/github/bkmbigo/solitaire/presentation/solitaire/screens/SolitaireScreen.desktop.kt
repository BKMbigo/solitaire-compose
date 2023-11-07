package com.github.bkmbigo.solitaire.presentation.solitaire.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator

actual object SolitaireScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val coroutineScope = rememberCoroutineScope()

        val screenModel = rememberScreenModel { SolitaireScreenModel(coroutineScope) }

        val state by screenModel.state.collectAsState()
        val gameTime by screenModel.gameTime.collectAsState()
        val score by screenModel.score.collectAsState()

        SolitaireGameScreenContent(
            state = state,
            gameTime = gameTime,
            score = score,
            hint = screenModel.hint,
            onNavigateBack = { navigator?.pop() },
            onAction = { action ->
                when (action) {
                    is SolitaireAction.PlayMove -> {
                        screenModel.play(action.move)
                    }

                    is SolitaireAction.StartNewGame -> {
                        screenModel.createGame(action.provider, action.cardsPerDeal)
                    }

                    SolitaireAction.Deal -> {
                        screenModel.deal()
                    }

                    SolitaireAction.RedoLastMove -> {
                        screenModel.redo()
                    }

                    SolitaireAction.UndoLastMove -> {
                        screenModel.undo()
                    }

                    SolitaireAction.OfferHint -> {
                        screenModel.offerHint()
                    }
                }
            }
        )

    }
}

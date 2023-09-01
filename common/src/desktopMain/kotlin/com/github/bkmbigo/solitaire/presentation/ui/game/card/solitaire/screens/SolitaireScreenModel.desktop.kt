package com.github.bkmbigo.solitaire.presentation.ui.game.card.solitaire.screens

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.github.bkmbigo.solitaire.game.solitaire.moves.SolitaireUserMove
import com.github.bkmbigo.solitaire.game.solitaire.providers.SolitaireGameProvider
import com.github.bkmbigo.solitaire.game.solitaire.providers.VeryEasySolitaireGameProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

actual class SolitaireScreenModel(
    private val coroutineScope: CoroutineScope
): ScreenModel, AbstractSolitaireScreenModel() {
    init {
        createGame(VeryEasySolitaireGameProvider)
    }

    actual fun createGame(provider: SolitaireGameProvider) {
        coroutineScope.launch {
            performCreateGame(provider)
        }
    }

    actual fun deal() {
        coroutineScope.launch {
            performDeal()
        }
    }

    actual fun play(move: SolitaireUserMove) {
        coroutineScope.launch {
            performPlay(move)
        }
    }
}

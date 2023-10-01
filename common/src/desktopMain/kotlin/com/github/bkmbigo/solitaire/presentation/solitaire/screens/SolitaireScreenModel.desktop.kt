package com.github.bkmbigo.solitaire.presentation.solitaire.screens

import cafe.adriel.voyager.core.model.ScreenModel
import com.github.bkmbigo.solitaire.game.solitaire.configuration.SolitaireCardsPerDeal
import com.github.bkmbigo.solitaire.game.solitaire.configuration.SolitaireGameConfiguration
import com.github.bkmbigo.solitaire.game.solitaire.moves.SolitaireUserMove
import com.github.bkmbigo.solitaire.game.solitaire.providers.SolitaireGameProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

actual class SolitaireScreenModel(
    private val coroutineScope: CoroutineScope
) : ScreenModel, AbstractSolitaireScreenModel() {

    actual fun createGame(provider: SolitaireGameProvider, cardsPerDeal: SolitaireCardsPerDeal) {
        coroutineScope.launch {
            performCreateGame(provider, SolitaireGameConfiguration(cardsPerDeal))
        }
    }

    actual fun deal() = performDeal()

    actual fun play(move: SolitaireUserMove) = performPlay(move)

    actual fun undo() = performUndo()

    actual fun redo() = performRedo()

    actual fun offerHint() = performHint()

}

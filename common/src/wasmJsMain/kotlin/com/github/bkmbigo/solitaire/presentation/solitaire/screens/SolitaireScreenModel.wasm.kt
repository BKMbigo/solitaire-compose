package com.github.bkmbigo.solitaire.presentation.solitaire.screens

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.github.bkmbigo.solitaire.game.solitaire.configuration.SolitaireCardsPerDeal
import com.github.bkmbigo.solitaire.game.solitaire.configuration.SolitaireGameConfiguration
import com.github.bkmbigo.solitaire.game.solitaire.moves.SolitaireUserMove
import com.github.bkmbigo.solitaire.game.solitaire.providers.SolitaireGameProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

actual class SolitaireScreenModel : ScreenModel, AbstractSolitaireScreenModel() {
    override val scope: CoroutineScope = coroutineScope

    actual fun createGame(
        provider: SolitaireGameProvider,
        cardsPerDeal: SolitaireCardsPerDeal
    ) {
        scope.launch {
            performCreateGame(provider, SolitaireGameConfiguration(cardsPerDeal))
        }
    }

    actual fun deal() = performDeal()

    actual fun play(move: SolitaireUserMove.CardMove) = performPlay(move)

    actual fun undo() = performUndo()

    actual fun redo() = performRedo()

    actual fun offerHint() = performHint()

}

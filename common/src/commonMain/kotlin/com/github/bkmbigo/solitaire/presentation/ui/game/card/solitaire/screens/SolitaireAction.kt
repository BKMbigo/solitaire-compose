package com.github.bkmbigo.solitaire.presentation.ui.game.card.solitaire.screens

import com.github.bkmbigo.solitaire.game.solitaire.moves.SolitaireUserMove
import com.github.bkmbigo.solitaire.models.core.Card

sealed class SolitaireAction {

    data object StartNewGame: SolitaireAction()

//    data object UndoLastMove: SolitaireGameAction()

//    data object RedoLastMove: SolitaireGameAction()

//    data object OfferHint: SolitaireGameAction()

    data object Deal: SolitaireAction()

    data class PlayMove(val move: SolitaireUserMove): SolitaireAction()

}

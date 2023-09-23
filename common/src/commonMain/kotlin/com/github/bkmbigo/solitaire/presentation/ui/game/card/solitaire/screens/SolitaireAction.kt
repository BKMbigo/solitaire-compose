package com.github.bkmbigo.solitaire.presentation.ui.game.card.solitaire.screens

import com.github.bkmbigo.solitaire.game.solitaire.moves.SolitaireUserMove
import com.github.bkmbigo.solitaire.models.core.Card

sealed class SolitaireAction {

    data object StartNewGame: SolitaireAction()

    data object UndoLastMove: SolitaireAction()

    data object RedoLastMove: SolitaireAction()

    data object OfferHint: SolitaireAction()

    data object CancelHint: SolitaireAction()

    data object Deal: SolitaireAction()

    data class PlayMove(val move: SolitaireUserMove): SolitaireAction()

}

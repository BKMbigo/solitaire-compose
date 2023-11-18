package com.github.bkmbigo.solitaire.presentation.solitaire.screens

import com.github.bkmbigo.solitaire.game.solitaire.configuration.SolitaireCardsPerDeal
import com.github.bkmbigo.solitaire.game.solitaire.moves.SolitaireUserMove
import com.github.bkmbigo.solitaire.game.solitaire.providers.SolitaireGameProvider
import com.github.bkmbigo.solitaire.utils.Platform

/** An enumeration of all possible actions a user can make */
sealed class SolitaireAction {
    /** The user wishes to start a new game */
    data class StartNewGame(
        val provider: SolitaireGameProvider,
        val cardsPerDeal: SolitaireCardsPerDeal
    ) : SolitaireAction()

    /** The user wishes to undo their last move */
    data object UndoLastMove : SolitaireAction()

    /** The user wishes to redo their last undone move */
    data object RedoLastMove : SolitaireAction()

    /** The user wishes to be provided with a hint */
    data object OfferHint : SolitaireAction()

    /** The user wishes to deal new cards */
    data object Deal : SolitaireAction()

    /** The user has played a [move] */
    data class PlayMove(val move: SolitaireUserMove.CardMove) : SolitaireAction()

    /** The User wishes to submit a score to the leaderboard */
    data class SubmitLeaderboardScore(
        val playerName: String,
        val leaderboard: String?,
        val platform: Platform
    ) : SolitaireAction()

}

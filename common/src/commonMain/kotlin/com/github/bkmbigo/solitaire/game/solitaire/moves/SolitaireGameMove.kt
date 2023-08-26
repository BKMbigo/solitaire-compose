package com.github.bkmbigo.solitaire.game.solitaire.moves

import com.github.bkmbigo.solitaire.game.GameMove
import com.github.bkmbigo.solitaire.game.solitaire.SolitaireGame
import com.github.bkmbigo.solitaire.models.core.Card
import com.github.bkmbigo.solitaire.models.solitaire.TableStackEntry

/** A Solitaire game move.*/
sealed class SolitaireGameMove : GameMove<SolitaireGame, SolitaireGameMove> {

    /** Reverses a [SolitaireUserMove.Deal] move
     * <p> The move should only be called by the system, not the user. </p>*/
    data object Undeal: SolitaireGameMove() {
        override fun isValid(game: SolitaireGame): Boolean = true
    }

    /** Reveals the top-most hidden card on a table stack.*/
    data class RevealCard(
        val tableStackEntry: TableStackEntry
    ): SolitaireGameMove() {
        override fun isValid(game: SolitaireGame): Boolean = true
    }

    /** Hides the bottom-most revealed card on a table stack.*/
    data class HideCard(
        val tableStackEntry: TableStackEntry
    ): SolitaireGameMove() {
        override fun isValid(game: SolitaireGame): Boolean = true
    }

    /** Returns a card to the deck*/
    data class ReturnToDeck(
        val card: Card,
        val from: ReturnToDeckSource,
        val index: Int
    ): SolitaireGameMove() {
        override fun isValid(game: SolitaireGame): Boolean = true
    }
}

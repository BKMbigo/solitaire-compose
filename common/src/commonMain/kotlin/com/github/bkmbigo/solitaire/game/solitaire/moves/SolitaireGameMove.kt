package com.github.bkmbigo.solitaire.game.solitaire.moves

import com.github.bkmbigo.solitaire.game.GameMove
import com.github.bkmbigo.solitaire.game.solitaire.SolitaireGame
import com.github.bkmbigo.solitaire.game.solitaire.moves.dsl.move
import com.github.bkmbigo.solitaire.game.solitaire.moves.dsl.to
import com.github.bkmbigo.solitaire.game.solitaire.utils.SolitaireDealOffset
import com.github.bkmbigo.solitaire.models.core.Card
import com.github.bkmbigo.solitaire.models.solitaire.TableStackEntry
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

/** A Solitaire game move.*/
sealed class SolitaireGameMove : GameMove<SolitaireGame, SolitaireGameMove> {

    override val time: Instant = Clock.System.now()

    /** Reverses a [SolitaireUserMove.Deal] move
     * <p> The move should only be called by the system, not the user. </p>*/
    data class Undeal(
        val offset: SolitaireDealOffset
    ): SolitaireGameMove() {
        override fun isValid(game: SolitaireGame): Boolean = true

        override fun reversed(): SolitaireGameMove = SolitaireUserMove.Deal(offset)
    }

    /** Reveals the top-most hidden card on a table stack.*/
    data class RevealCard(
        val tableStackEntry: TableStackEntry
    ): SolitaireGameMove() {
        override fun isValid(game: SolitaireGame): Boolean = true

        override fun reversed(): SolitaireGameMove = HideCard(tableStackEntry)
    }

    /** Hides the bottom-most revealed card on a table stack.*/
    data class HideCard(
        val tableStackEntry: TableStackEntry
    ): SolitaireGameMove() {
        override fun isValid(game: SolitaireGame): Boolean = true

        override fun reversed(): SolitaireGameMove = RevealCard(tableStackEntry)
    }

    /** Returns a card to the deck*/
    data class ReturnToDeck(
        val card: Card,
        val from: ReturnToDeckSource,
        val index: Int
    ): SolitaireGameMove() {
        override fun isValid(game: SolitaireGame): Boolean = true

        override fun reversed(): SolitaireGameMove {
            val reverseTo = when (from) {
                ReturnToDeckSource.FromFoundation -> MoveDestination.ToFoundation
                is ReturnToDeckSource.FromTable -> MoveDestination.ToTable(from.tableStackEntry)
            }

            return card move MoveSource.FromDeck(index) to reverseTo
        }
    }
}

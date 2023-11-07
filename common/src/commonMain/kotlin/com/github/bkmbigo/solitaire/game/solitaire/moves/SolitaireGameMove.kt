package com.github.bkmbigo.solitaire.game.solitaire.moves

import com.github.bkmbigo.solitaire.game.GameMove
import com.github.bkmbigo.solitaire.game.solitaire.SolitaireGame
import com.github.bkmbigo.solitaire.game.solitaire.moves.dsl.moveSolitaireInstantlyFrom
import com.github.bkmbigo.solitaire.game.solitaire.moves.dsl.moveTo
import com.github.bkmbigo.solitaire.game.solitaire.utils.SolitaireDealOffset
import com.github.bkmbigo.solitaire.models.core.Card
import com.github.bkmbigo.solitaire.models.solitaire.TableStackEntry
import kotlin.time.Duration

/** A Solitaire game move.*/
sealed class SolitaireGameMove : GameMove<SolitaireGame, SolitaireGameMove> {

    /** Reverses a [SolitaireUserMove.Deal] move
     * <p> The move should only be called by the system, not the user. </p>*/
    data class Undeal(
        override val timeSinceStart: Duration,
        val offset: SolitaireDealOffset
    ): SolitaireGameMove() {
        override fun isValid(game: SolitaireGame): Boolean = true

        override fun reversed(timeSinceStart: Duration): SolitaireGameMove =
            SolitaireUserMove.Deal(timeSinceStart, offset)
    }

    /** Reveals the top-most hidden card on a table stack.*/
    data class RevealCard(
        override val timeSinceStart: Duration,
        val tableStackEntry: TableStackEntry
    ): SolitaireGameMove() {
        override fun isValid(game: SolitaireGame): Boolean = true

        override fun reversed(timeSinceStart: Duration): SolitaireGameMove = HideCard(timeSinceStart, tableStackEntry)
    }

    /** Hides the bottom-most revealed card on a table stack.*/
    data class HideCard(
        override val timeSinceStart: Duration,
        val tableStackEntry: TableStackEntry
    ): SolitaireGameMove() {
        override fun isValid(game: SolitaireGame): Boolean = true

        override fun reversed(timeSinceStart: Duration): SolitaireGameMove = RevealCard(timeSinceStart, tableStackEntry)
    }

    /** Returns a card to the deck*/
    data class ReturnToDeck(
        override val timeSinceStart: Duration,
        val card: Card,
        val from: ReturnToDeckSource,
        val index: Int
    ): SolitaireGameMove() {
        override fun isValid(game: SolitaireGame): Boolean = true

        override fun reversed(timeSinceStart: Duration): SolitaireGameMove {
            val reverseTo = when (from) {
                ReturnToDeckSource.FromFoundation -> MoveDestination.ToFoundation
                is ReturnToDeckSource.FromTable -> MoveDestination.ToTable(from.tableStackEntry)
            }

            return card moveSolitaireInstantlyFrom MoveSource.FromDeck(index) moveTo reverseTo
        }
    }
}

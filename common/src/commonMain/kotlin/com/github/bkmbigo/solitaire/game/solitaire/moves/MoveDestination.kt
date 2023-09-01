package com.github.bkmbigo.solitaire.game.solitaire.moves

import com.github.bkmbigo.solitaire.models.solitaire.TableStackEntry

/** The destination of the card(s) during a [SolitaireGameMove]
 * <p> A card can only move to its respective foundation stack or to a table stack </p>.*/
sealed class MoveDestination {
    /** The card(s) has been move towards the game's foundation. */
    data object ToFoundation: MoveDestination()

    /** The card(s) has been move towards a table's stack. */
    data class ToTable(
        val tableStackEntry: TableStackEntry
    ): MoveDestination()
}

package com.github.bkmbigo.solitaire.game.solitaire.moves

import com.github.bkmbigo.solitaire.models.solitaire.utils.TableStackEntry

/** The destination of the card(s) during a [SolitaireGameMove]
 * <p> A card can only move to its respective foundation stack or to a table stack </p>.*/
sealed class MoveDestination {
    object ToFoundation: MoveDestination()

    data class ToTable(
        val tableStackEntry: TableStackEntry
    ): MoveDestination()
}
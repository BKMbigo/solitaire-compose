package com.github.bkmbigo.solitaire.game.solitaire.moves

import com.github.bkmbigo.solitaire.models.solitaire.TableStackEntry

/** Represents the source of the card(s) involved in a [SolitaireGameMove]
 * <p> Typically storing the source during the move might seem unnecessary,
 *          but is preserved in need of undo/redo actions where the card needs to know where is was moved from.</p>*/
sealed class MoveSource {

    /** The card has been obtained from its respective foundation stack*/
    data object FromFoundation : MoveSource()

    /** The card has been obtained from the deck.
     * @param index The index of the card on the deck.*/
    data class FromDeck(
        val index: Int
    ) : MoveSource()

    /** The card has been obtained from a table stack.
     * @param tableStackEntry -> The identity of the Table Stack (Usually represented by numbers from One to Eight).*/
    data class FromTable(val tableStackEntry: TableStackEntry) : MoveSource()

    companion object {
        /** returns the source from respective destination */
        fun fromDestination(to: MoveDestination): MoveSource = when (to) {
            MoveDestination.ToFoundation -> FromFoundation
            is MoveDestination.ToTable -> FromTable(to.tableStackEntry)
        }
    }
}

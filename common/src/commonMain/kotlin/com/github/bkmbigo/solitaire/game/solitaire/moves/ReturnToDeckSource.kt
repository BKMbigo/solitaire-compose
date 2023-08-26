package com.github.bkmbigo.solitaire.game.solitaire.moves

import com.github.bkmbigo.solitaire.models.solitaire.TableStackEntry

/** Wrapper for move that returns a card to the deck.*/
sealed class ReturnToDeckSource{
    /** The card being returned to the deck is obtained from its foundation stack.*/
    data object FromFoundation: ReturnToDeckSource()

    /** The card being returned to the deck is obtained from a table stack. */
    data class FromTable(
        val tableStackEntry: TableStackEntry
    ): ReturnToDeckSource()
}

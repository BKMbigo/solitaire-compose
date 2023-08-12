package com.github.bkmbigo.solitaire.game

import kotlinx.datetime.Instant

/* A mechanism helping in
*       - providing hints.
*       - storing moves (for player to undo/redo).
*       - scoring.
* */
interface GameMove {

    val time: Instant

    /** The move is valid in the context of the game.*/
    fun isValid(): Boolean
}
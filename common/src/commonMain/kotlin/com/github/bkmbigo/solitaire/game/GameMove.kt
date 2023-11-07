package com.github.bkmbigo.solitaire.game

import kotlin.time.Duration

/* A mechanism helping in
*       - providing hints.
*       - storing moves (for player to undo/redo).
*       - scoring.
* */
interface GameMove<
        G : Game<G, M>,
        M : GameMove<G, M>> {

    val timeSinceStart: Duration

    /** The move is valid in the context of a game.*/
    fun isValid(game: G): Boolean

    fun reversed(timeSinceStart: Duration): M?
}

package com.github.bkmbigo.solitaire.game

/* A mechanism helping in
*       - providing hints.
*       - storing moves (for player to undo/redo).
*       - scoring.
* */
interface GameMove<
        G : Game<G, M>,
        M : GameMove<G, M>> {

//    val time: Instant   /* Time will be added with upgrade to 1.9.20 */

    /** The move is valid in the context of a game.*/
    fun isValid(game: G): Boolean

    fun reversed(): M?
}

package com.github.bkmbigo.solitaire.game

interface Game< G: Game<G, M>, M : GameMove<G, M>> {

    /** Return the resulting [Game] after playing a [GameMove] */
    fun play(move: M): G

    /** Check to verify that the current iteration of the game is valid*/
    fun isValid(): Boolean

    /** Check if the game is already won */
    fun isWon(): Boolean

    /** Check if a win is not possible from the given game */
    fun isDrawn(): Boolean
}

package com.github.bkmbigo.solitaire.game

interface Game<M : GameMove> {

    fun play(move: M): Game<M>

    /* Check to verify that the current iteration of the game is valid*/
    fun isValid(): Boolean

    fun isWon(): Boolean

    fun isDrawn(): Boolean
}
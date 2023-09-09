package com.github.bkmbigo.solitaire.game

interface Game< G: Game<G, M>, M : GameMove<G, M>> {

    abstract fun play(move: M): G

    //  UNDO moves
/*    *//** The player wants to undo a move they had previously made *//*
    abstract fun undo(move: M): Game<M>

    *//** The player wants to undo a move they had previously made *//*
    abstract fun redo(move: M): Game<M>*/

    /** Check to verify that the current iteration of the game is valid*/
    abstract fun isValid(): Boolean

    abstract fun isWon(): Boolean

    abstract fun isDrawn(): Boolean
}

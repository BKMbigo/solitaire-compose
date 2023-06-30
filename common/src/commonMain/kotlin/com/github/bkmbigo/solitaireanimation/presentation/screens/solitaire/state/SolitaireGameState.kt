package com.github.bkmbigo.solitaireanimation.presentation.screens.solitaire.state

import com.github.bkmbigo.solitaireanimation.domain.SolitaireGame

sealed class SolitaireGameState(open val game: SolitaireGame?) {
    /**The user has just opened the Game Screen*/
    object New: SolitaireGameState(null)
    /**The game is playing the entry animation of the game*/
    data class EntryAnimation(
        override val game: SolitaireGame
    ): SolitaireGameState(game)
    /**The user is currently playing the game*/
    data class Playing(
        override val game: SolitaireGame
    ): SolitaireGameState(game)
    /**The user has paused the current game*/
    data class Paused(
        override val game: SolitaireGame
    ): SolitaireGameState(game)
    /**The game is currently playing the exit animation*/
    data class ExitAnimation(
        override val game: SolitaireGame
    ): SolitaireGameState(game)
    /**The user has Successfully completed the game*/
    object Completed: SolitaireGameState(null)
}

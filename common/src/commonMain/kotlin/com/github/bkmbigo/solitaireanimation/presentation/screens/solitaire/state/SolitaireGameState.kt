package com.github.bkmbigo.solitaireanimation.presentation.screens.solitaire.state

import com.github.bkmbigo.solitaireanimation.domain.SolitaireGame

sealed class SolitaireGameState {
    /**The user has just opened the Game Screen*/
    object New: SolitaireGameState()
    /**The game is playing the entry animation of the game*/
    data class EntryAnimation(
        val game: SolitaireGame
    ): SolitaireGameState()
    /**The user is currently playing the game*/
    data class Playing(
        val game: SolitaireGame
    ): SolitaireGameState()
    /**The user has paused the current game*/
    data class Paused(
        val game: SolitaireGame
    ): SolitaireGameState()
    /**The game is currently playing the exit animation*/
    data class ExitAnimation(
        val game: SolitaireGame
    ): SolitaireGameState()
    /**The user has Successfully completed the game*/
    object Completed: SolitaireGameState()
}

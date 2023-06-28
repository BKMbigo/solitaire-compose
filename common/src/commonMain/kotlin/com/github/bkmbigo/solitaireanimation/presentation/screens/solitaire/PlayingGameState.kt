package com.github.bkmbigo.solitaireanimation.presentation.screens.solitaire

/**
 * State of the Solitaire Game
 */
enum class PlayingGameState {
    /**The game is playing the entry animation of the game*/
    ENTRY_ANIMATION,
    /**The user is currently playing the game*/
    PLAYING,
    /**The user has paused the current game*/
    PAUSED,
    /**The game is currently playing the exit animation*/
    EXIT_ANIMATION
}
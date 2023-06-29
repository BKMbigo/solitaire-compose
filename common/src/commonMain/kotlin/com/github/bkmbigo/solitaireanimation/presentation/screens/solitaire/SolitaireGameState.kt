package com.github.bkmbigo.solitaireanimation.presentation.screens.solitaire

/**
 * State of the Solitaire Game
 */
enum class SolitaireGameState {
    /**The user has just opened the Game Screen*/
    NEW,
    /**The game is playing the entry animation of the game*/
    ENTRY_ANIMATION,
    /**The user is currently playing the game*/
    PLAYING,
    /**The user has paused the current game*/
    PAUSED,
    /**The game is currently playing the exit animation*/
    EXIT_ANIMATION,
    /**The user has Successfully completed the game*/
    WON
}
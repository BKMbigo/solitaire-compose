package com.github.bkmbigo.solitaireanimation.presentation.screens

/**
 * State of the Solitaire Game
 */
enum class GameState {
    /**The game is new and the user has not yet initiated the game.*/
    NEW,
    /**The game is playing the entry animation of the game*/
    ENTRY_ANIMATION,
    /**The user is currently playing the game*/
    PLAYING,
    /**The user has paused the current game*/
    PAUSED,
    /**The game is currently playing the exit animation*/
    EXIT_ANIMATION,
    /**The user has completed the game. It can occur when the user has won a game or when the game cannot end*/
    FINISHED_GAME
}
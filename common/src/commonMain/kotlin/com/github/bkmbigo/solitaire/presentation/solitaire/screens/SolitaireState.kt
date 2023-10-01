package com.github.bkmbigo.solitaire.presentation.solitaire.screens

import com.github.bkmbigo.solitaire.game.solitaire.SolitaireGame

data class SolitaireState(
    val game: SolitaireGame = SolitaireGame.EMPTY_GAME,
    val canUndo: Boolean = false,
    val canRedo: Boolean = false,
    val isWon: Boolean = false,
    val isDrawn: Boolean = false
)

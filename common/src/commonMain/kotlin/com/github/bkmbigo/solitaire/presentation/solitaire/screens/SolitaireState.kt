package com.github.bkmbigo.solitaire.presentation.solitaire.screens

import com.github.bkmbigo.solitaire.data.SolitaireScore
import com.github.bkmbigo.solitaire.game.solitaire.SolitaireGame

data class SolitaireState(
    val game: SolitaireGame = SolitaireGame.EMPTY_GAME,
    val canUndo: Boolean = false,
    val canRedo: Boolean = false,
    val showLeaderboardDialog: Boolean = false,
    val latestLeaderboardStatus: List<SolitaireScore> = emptyList()
)

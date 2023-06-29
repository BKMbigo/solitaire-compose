package com.github.bkmbigo.solitaireanimation.presentation.screens.solitaire.state

import com.github.bkmbigo.solitaireanimation.domain.SolitaireGame
import com.github.bkmbigo.solitaireanimation.presentation.screens.solitaire.SolitaireGameState

data class GameScreenState(
    val state: SolitaireGameState = SolitaireGameState.PLAYING,
    val game: SolitaireGame
)

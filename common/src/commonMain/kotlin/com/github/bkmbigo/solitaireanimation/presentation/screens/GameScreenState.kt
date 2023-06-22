package com.github.bkmbigo.solitaireanimation.presentation.screens

import com.github.bkmbigo.solitaireanimation.domain.SolitaireGame

sealed class GameScreenState {
    object Loading: GameScreenState()
    data class Playing(val game: SolitaireGame): GameScreenState()
}

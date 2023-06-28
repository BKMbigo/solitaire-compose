package com.github.bkmbigo.solitaireanimation.presentation.screens.solitaire.state

import com.github.bkmbigo.solitaireanimation.domain.SolitaireGame
import com.github.bkmbigo.solitaireanimation.presentation.screens.solitaire.PlayingGameState

sealed class GameScreenState(open val game: SolitaireGame?) {
    object NewGame: GameScreenState(null)
    data class PlayingGame(
        val state: PlayingGameState,
        override val game: SolitaireGame
    ): GameScreenState(game)
}

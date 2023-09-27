package com.github.bkmbigo.solitaire.presentation.ui.game.card.solitaire.screens

import com.github.bkmbigo.solitaire.game.solitaire.moves.SolitaireUserMove
import com.github.bkmbigo.solitaire.game.solitaire.providers.SolitaireGameProvider
import kotlinx.coroutines.flow.StateFlow

expect class SolitaireScreenModel {
    val state: StateFlow<SolitaireState>

    fun createGame(provider: SolitaireGameProvider)

    fun deal()

    fun play(move: SolitaireUserMove)

    fun undo()

    fun redo()

    fun offerHint()
}

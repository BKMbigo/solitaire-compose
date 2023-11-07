package com.github.bkmbigo.solitaire.presentation.solitaire.screens

import com.github.bkmbigo.solitaire.game.solitaire.configuration.SolitaireCardsPerDeal
import com.github.bkmbigo.solitaire.game.solitaire.moves.SolitaireUserMove
import com.github.bkmbigo.solitaire.game.solitaire.providers.SolitaireGameProvider
import kotlinx.coroutines.flow.StateFlow
import kotlin.time.Duration

expect class SolitaireScreenModel {
    val state: StateFlow<SolitaireState>
    val gameTime: StateFlow<Duration>
    val score: StateFlow<Int>

    fun createGame(provider: SolitaireGameProvider, cardsPerDeal: SolitaireCardsPerDeal)

    fun deal()

    fun play(move: SolitaireUserMove.CardMove)

    fun undo()

    fun redo()

    fun offerHint()
}

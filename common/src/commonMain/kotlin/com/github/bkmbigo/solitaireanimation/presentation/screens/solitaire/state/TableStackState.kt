package com.github.bkmbigo.solitaireanimation.presentation.screens.solitaire.state

import com.github.bkmbigo.solitaireanimation.models.Card

data class TableStackState(
    val cards: List<Card> = emptyList(),
    val flippedCards: Int = 0,
    val selectedCards: Int = 0
)

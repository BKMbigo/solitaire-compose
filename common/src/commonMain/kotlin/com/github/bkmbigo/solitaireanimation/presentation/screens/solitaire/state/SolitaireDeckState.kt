package com.github.bkmbigo.solitaireanimation.presentation.screens.solitaire.state

import com.github.bkmbigo.solitaireanimation.models.Card

data class SolitaireDeckState(
    val openStack: List<Card> = emptyList(),
    val uncoveredStack: List<Card> = emptyList(),
    val hiddenCards: List<Card> = emptyList()
)

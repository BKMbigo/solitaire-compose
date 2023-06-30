package com.github.bkmbigo.solitaireanimation.presentation.components.deck

import com.github.bkmbigo.solitaireanimation.models.Card

data class SolitaireDeckState(
    val openStack: List<Card> = emptyList(),
    val uncoveredStack: List<Card> = emptyList()
)

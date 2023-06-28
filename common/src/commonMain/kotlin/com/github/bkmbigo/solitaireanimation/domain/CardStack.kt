package com.github.bkmbigo.solitaireanimation.domain

import com.github.bkmbigo.solitaireanimation.models.Card

data class CardStack(
    val cells: List<Card> = emptyList(),
    val flippedCells: Int = 0,
    val selectedCells: Int = 0
)

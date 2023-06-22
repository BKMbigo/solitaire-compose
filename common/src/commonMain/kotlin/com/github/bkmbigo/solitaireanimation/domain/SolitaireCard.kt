package com.github.bkmbigo.solitaireanimation.domain

import com.github.bkmbigo.solitaireanimation.models.Card

data class SolitaireCard(
    val card: Card,
    val isFlipped: Boolean,
    val isSelected: Boolean
)

package com.github.bkmbigo.solitaireanimation.domain

import com.github.bkmbigo.solitaireanimation.models.Card

data class SolitaireGame(
    val stock: List<Card>, // Cards left on top

    val spadesFoundation: SolitaireFoundationStack,
    val cloversFoundation: SolitaireFoundationStack,
    val diamondsFoundation: SolitaireFoundationStack,
    val heartsFoundation: SolitaireFoundationStack,

    val tableStack: List<SolitaireTableStack>
)
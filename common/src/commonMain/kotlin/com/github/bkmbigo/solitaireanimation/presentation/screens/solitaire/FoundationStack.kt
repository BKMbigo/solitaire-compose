package com.github.bkmbigo.solitaireanimation.presentation.screens.solitaire

import com.github.bkmbigo.solitaireanimation.models.CardSuite

/**A typesafe collection of Solitaire Foundation Stacks*/
enum class FoundationStack(val suite: CardSuite) {
    SPADE(CardSuite.SPADE),
    CLOVER(CardSuite.CLOVER),
    HEARTS(CardSuite.HEARTS),
    DIAMOND(CardSuite.DIAMOND)
}
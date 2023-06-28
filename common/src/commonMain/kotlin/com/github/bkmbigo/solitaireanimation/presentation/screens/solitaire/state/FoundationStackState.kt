package com.github.bkmbigo.solitaireanimation.presentation.screens.solitaire.state

import com.github.bkmbigo.solitaireanimation.models.Card
import com.github.bkmbigo.solitaireanimation.models.CardRank

data class FoundationStackState(
    val cards: List<Card> = emptyList()
) {

    val highestRank: CardRank
        get() = cards.maxOf { it.rank }
}
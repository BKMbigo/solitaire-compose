package com.github.bkmbigo.solitaireanimation.domain

import com.github.bkmbigo.solitaireanimation.models.Card
import com.github.bkmbigo.solitaireanimation.models.CardRank

data class SolitaireFoundationStack(
    val cards: List<Card> = emptyList()
) {

    val highestRank: CardRank
        get() = cards.maxOf { it.rank }
}
package com.github.bkmbigo.solitaireanimation.models

import com.github.bkmbigo.solitaireanimation.presentation.utils.SvgImage

expect enum class CardSuite {
    SPADE,
    CLOVER,
    HEARTS,
    DIAMOND;

    val color: CardColor;
    val image: SvgImage
}
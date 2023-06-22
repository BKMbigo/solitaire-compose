package com.github.bkmbigo.solitaireanimation.models

import androidx.annotation.DrawableRes
import com.github.bkmbigo.solitaireanimation.R
import com.github.bkmbigo.solitaireanimation.presentation.utils.SvgImage

actual enum class CardSuite(
    actual val color: CardColor,
    actual val image: SvgImage
) {
    SPADE(
        color = CardColor.BLACK,
        image = SvgImage()
    ),
    CLOVER(
        color = CardColor.BLACK,
        image = SvgImage()
    ),
    HEARTS(
        color = CardColor.RED,
        image = SvgImage()
    ),
    DIAMOND(
        color = CardColor.RED,
        image = SvgImage()
    );
}
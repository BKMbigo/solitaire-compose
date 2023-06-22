package com.github.bkmbigo.solitaireanimation.models

import com.github.bkmbigo.solitaireanimation.presentation.utils.SvgImage

actual enum class CardSuite(
    actual val color: CardColor,
    actual val image: SvgImage
) {
    SPADE(
        color = CardColor.BLACK,
        image = SvgImage(filename = "suite_spade.xml")
    ),
    CLOVER(
        color = CardColor.BLACK,
        image = SvgImage(filename = "suite_clover.xml"),
    ),
    HEARTS(
        color = CardColor.RED,
        image = SvgImage(filename = "suite_hearts.xml")
    ),
    DIAMOND(
        color = CardColor.RED,
        image = SvgImage(filename = "suite_diamond.xml")
    );
}
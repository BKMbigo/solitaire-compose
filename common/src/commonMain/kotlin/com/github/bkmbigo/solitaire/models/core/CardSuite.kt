package com.github.bkmbigo.solitaire.models.core

enum class CardSuite(
    val color: CardColor,
    val imageFilename: String
) {
    SPADE(
        color = CardColor.BLACK,
        imageFilename = "suite_spade.xml"
    ),
    CLOVER(
        color = CardColor.BLACK,
        imageFilename = "suite_clover.xml"
    ),
    HEARTS(
        color = CardColor.RED,
        imageFilename = "suite_hearts.xml"
    ),
    DIAMOND(
        color = CardColor.RED,
        imageFilename = "suite_diamond.xml"
    );
}
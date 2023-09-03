package com.github.bkmbigo.solitaire.models.core

enum class CardColor {
    BLACK,
    RED;

    /** Alternate color */
    val alternate: CardColor
        get() = if(this == BLACK) RED else BLACK
}

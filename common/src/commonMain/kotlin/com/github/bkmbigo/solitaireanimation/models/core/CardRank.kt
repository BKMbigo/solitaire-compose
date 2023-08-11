package com.github.bkmbigo.solitaireanimation.models.core

enum class CardRank(val symbol: String) {
    ACE("A"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    TEN("10"),
    JUDGE("J"),
    QUEEN("Q"),
    KING("K");
    operator fun minus(other: CardRank) = ordinal - other.ordinal

}
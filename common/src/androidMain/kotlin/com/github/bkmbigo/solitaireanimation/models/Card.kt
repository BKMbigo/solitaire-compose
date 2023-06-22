package com.github.bkmbigo.solitaireanimation.models

import com.github.bkmbigo.solitaireanimation.presentation.utils.SvgImage

actual enum class Card {
    // ACE
    ACE_OF_SPADES, ACE_OF_CLOVER, ACE_OF_DIAMOND, ACE_OF_HEARTS,

    // TWO
    TWO_OF_SPADES, TWO_OF_CLOVER, TWO_OF_DIAMOND, TWO_OF_HEARTS,

    // THREE
    THREE_OF_SPADES, THREE_OF_CLOVER, THREE_OF_DIAMOND, THREE_OF_HEARTS,

    // FOUR
    FOUR_OF_SPADES, FOUR_OF_CLOVER, FOUR_OF_DIAMOND, FOUR_OF_HEARTS,

    // FIVE
    FIVE_OF_SPADES, FIVE_OF_CLOVER, FIVE_OF_DIAMOND, FIVE_OF_HEARTS,

    // SIX
    SIX_OF_SPADES, SIX_OF_CLOVER, SIX_OF_DIAMOND, SIX_OF_HEARTS,

    // SEVEN
    SEVEN_OF_SPADES, SEVEN_OF_CLOVER, SEVEN_OF_DIAMOND, SEVEN_OF_HEARTS,

    // EIGHT
    EIGHT_OF_SPADES, EIGHT_OF_CLOVER, EIGHT_OF_DIAMOND, EIGHT_OF_HEARTS,

    // NINE
    NINE_OF_SPADES, NINE_OF_CLOVER, NINE_OF_DIAMOND, NINE_OF_HEARTS,

    // TEN
    TEN_OF_SPADES, TEN_OF_CLOVER, TEN_OF_DIAMOND, TEN_OF_HEARTS,

    // JUDGE
    JUDGE_OF_SPADES, JUDGE_OF_CLOVER, JUDGE_OF_DIAMOND, JUDGE_OF_HEARTS,

    // QUEEN
    QUEEN_OF_SPADES, QUEEN_OF_CLOVER, QUEEN_OF_DIAMOND, QUEEN_OF_HEARTS,

    // KING
    KING_OF_SPADES, KING_OF_CLOVER, KING_OF_DIAMOND, KING_OF_HEARTS;

    actual val rank: CardRank
        get() = TODO("Not yet implemented")
    actual val suite: CardSuite
        get() = TODO("Not yet implemented")
    actual val image: SvgImage
        get() = TODO("Not yet implemented")
    actual val darkImage: SvgImage?
        get() = TODO("Not yet implemented")

}
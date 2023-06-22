package com.github.bkmbigo.solitaireanimation.models

import com.github.bkmbigo.solitaireanimation.presentation.utils.SvgImage

actual enum class Card(
    actual val rank: CardRank,
    actual val suite: CardSuite,
    actual val image: SvgImage,
    actual val darkImage: SvgImage?,
) {

    // ACE
    ACE_OF_SPADES(
        rank = CardRank.ACE,
        suite = CardSuite.SPADE,
        image = SvgImage("card_ace_spade.svg"),
        darkImage = SvgImage("card_ace_spade_dark.svg"),

        ),
    ACE_OF_CLOVER(
        rank = CardRank.ACE,
        suite = CardSuite.CLOVER,
        image = SvgImage("card_ace_clover.svg"),
        darkImage = SvgImage("card_ace_clover_dark.svg"),

        ),
    ACE_OF_DIAMOND(
        rank = CardRank.ACE,
        suite = CardSuite.DIAMOND,
        image = SvgImage("card_ace_diamond.svg"),
        darkImage = null,

        ),
    ACE_OF_HEARTS(
        rank = CardRank.ACE,
        suite = CardSuite.HEARTS,
        image = SvgImage("card_ace_hearts.svg"),
        darkImage = null,
    ),

    // TWO
    TWO_OF_SPADES(
        rank = CardRank.TWO,
        suite = CardSuite.SPADE,
        image = SvgImage("card_two_spades.svg"),
        darkImage = SvgImage("card_two_spades_dark.svg")
    ),
    TWO_OF_CLOVER(
        rank = CardRank.TWO,
        suite = CardSuite.CLOVER,
        image = SvgImage("card_two_clover.svg"),
        darkImage = SvgImage("card_two_clover_dark.svg")
    ),
    TWO_OF_DIAMOND(
        rank = CardRank.TWO,
        suite = CardSuite.DIAMOND,
        image = SvgImage("card_two_diamond.svg"),
        darkImage = null
    ),
    TWO_OF_HEARTS(
        rank = CardRank.TWO,
        suite = CardSuite.HEARTS,
        image = SvgImage("card_two_hearts.svg"),
        darkImage = null
    ),

    // THREE
    THREE_OF_SPADES(
        rank = CardRank.THREE,
        suite = CardSuite.SPADE,
        image = SvgImage("card_three_spade.svg"),
        darkImage = SvgImage("card_three_spade_dark.svg")
    ),
    THREE_OF_CLOVER(
        rank = CardRank.THREE,
        suite = CardSuite.CLOVER,
        image = SvgImage("card_three_clover.svg"),
        darkImage = SvgImage("card_three_clover_dark.svg")
    ),
    THREE_OF_DIAMOND(
        rank = CardRank.THREE,
        suite = CardSuite.DIAMOND,
        image = SvgImage("card_three_diamond.svg"),
        darkImage = null
    ),
    THREE_OF_HEARTS(
        rank = CardRank.THREE,
        suite = CardSuite.HEARTS,
        image = SvgImage("card_three_hearts.svg"),
        darkImage = null
    ),

    // FOUR
    FOUR_OF_SPADES(
        rank = CardRank.FOUR,
        suite = CardSuite.SPADE,
        image = SvgImage("card_four_spade.svg"),
        darkImage = SvgImage("card_four_spade_dark.svg")
    ),
    FOUR_OF_CLOVER(
        rank = CardRank.FOUR,
        suite = CardSuite.CLOVER,
        image = SvgImage("card_four_clover.svg"),
        darkImage = SvgImage("card_four_clover_dark.svg")
    ),
    FOUR_OF_DIAMOND(
        rank = CardRank.FOUR,
        suite = CardSuite.DIAMOND,
        image = SvgImage("card_four_diamond.svg"),
        darkImage = null
    ),
    FOUR_OF_HEARTS(
        rank = CardRank.FOUR,
        suite = CardSuite.HEARTS,
        image = SvgImage("card_four_hearts.svg"),
        darkImage = null
    ),

    // FIVE
    FIVE_OF_SPADES(
        rank = CardRank.FIVE,
        suite = CardSuite.SPADE,
        image = SvgImage("card_five_spade.svg"),
        darkImage = SvgImage("card_five_spade_dark.svg")
    ),
    FIVE_OF_CLOVER(
        rank = CardRank.FIVE,
        suite = CardSuite.CLOVER,
        image = SvgImage("card_five_clover.svg"),
        darkImage = SvgImage("card_five_clover_dark.svg")
    ),
    FIVE_OF_DIAMOND(
        rank = CardRank.FIVE,
        suite = CardSuite.DIAMOND,
        image = SvgImage("card_five_diamond.svg"),
        darkImage = null
    ),
    FIVE_OF_HEARTS(
        rank = CardRank.FIVE,
        suite = CardSuite.HEARTS,
        image = SvgImage("card_five_hearts.svg"),
        darkImage = null
    ),

    // SIX
    SIX_OF_SPADES(
        rank = CardRank.SIX,
        suite = CardSuite.SPADE,
        image = SvgImage("card_six_spade.svg"),
        darkImage = SvgImage("card_six_spade_dark.svg")
    ),
    SIX_OF_CLOVER(
        rank = CardRank.SIX,
        suite = CardSuite.CLOVER,
        image = SvgImage("card_six_clover.svg"),
        darkImage = SvgImage("card_six_clover_dark.svg")
    ),
    SIX_OF_DIAMOND(
        rank = CardRank.SIX,
        suite = CardSuite.DIAMOND,
        image = SvgImage("card_six_diamond.svg"),
        darkImage = null
    ),
    SIX_OF_HEARTS(
        rank = CardRank.SIX,
        suite = CardSuite.HEARTS,
        image = SvgImage("card_six_hearts.svg"),
        darkImage = null
    ),

    // SEVEN
    SEVEN_OF_SPADES(
        rank = CardRank.SEVEN,
        suite = CardSuite.SPADE,
        image = SvgImage("card_seven_spade.svg"),
        darkImage = SvgImage("card_seven_spade_dark.svg")
    ),
    SEVEN_OF_CLOVER(
        rank = CardRank.SEVEN,
        suite = CardSuite.CLOVER,
        image = SvgImage("card_seven_clover.svg"),
        darkImage = SvgImage("card_seven_clover_dark.svg")
    ),
    SEVEN_OF_DIAMOND(
        rank = CardRank.SEVEN,
        suite = CardSuite.DIAMOND,
        image = SvgImage("card_seven_diamond.svg"),
        darkImage = null
    ),
    SEVEN_OF_HEARTS(
        rank = CardRank.SEVEN,
        suite = CardSuite.HEARTS,
        image = SvgImage("card_seven_hearts.svg"),
        darkImage = null
    ),

    // EIGHT
    EIGHT_OF_SPADES(
        rank = CardRank.EIGHT,
        suite = CardSuite.SPADE,
        image = SvgImage("card_eight_spade.svg"),
        darkImage = SvgImage("card_eight_spade_dark.svg")
    ),
    EIGHT_OF_CLOVER(
        rank = CardRank.EIGHT,
        suite = CardSuite.CLOVER,
        image = SvgImage("card_eight_clover.svg"),
        darkImage = SvgImage("card_eight_clover_dark.svg")
    ),
    EIGHT_OF_DIAMOND(
        rank = CardRank.EIGHT,
        suite = CardSuite.DIAMOND,
        image = SvgImage("card_eight_diamond.svg"),
        darkImage = null
    ),
    EIGHT_OF_HEARTS(
        rank = CardRank.EIGHT,
        suite = CardSuite.HEARTS,
        image = SvgImage("card_eight_hearts.svg"),
        darkImage = null
    ),

    // NINE
    NINE_OF_SPADES(
        rank = CardRank.NINE,
        suite = CardSuite.SPADE,
        image = SvgImage("card_nine_spade.svg"),
        darkImage = SvgImage("card_nine_spade_dark.svg")
    ),
    NINE_OF_CLOVER(
        rank = CardRank.NINE,
        suite = CardSuite.CLOVER,
        image = SvgImage("card_nine_clover.svg"),
        darkImage = SvgImage("card_nine_clover_dark.svg")
    ),
    NINE_OF_DIAMOND(
        rank = CardRank.NINE,
        suite = CardSuite.DIAMOND,
        image = SvgImage("card_nine_diamond.svg"),
        darkImage = null
    ),
    NINE_OF_HEARTS(
        rank = CardRank.NINE,
        suite = CardSuite.HEARTS,
        image = SvgImage("card_nine_hearts.svg"),
        darkImage = null
    ),

    // TEN
    TEN_OF_SPADES(
        rank = CardRank.TEN,
        suite = CardSuite.SPADE,
        image = SvgImage("card_ten_spade.svg"),
        darkImage = SvgImage("card_ten_spade_dark.svg")
    ),
    TEN_OF_CLOVER(
        rank = CardRank.TEN,
        suite = CardSuite.CLOVER,
        image = SvgImage("card_ten_clover.svg"),
        darkImage = SvgImage("card_ten_clover_dark.svg")
    ),
    TEN_OF_DIAMOND(
        rank = CardRank.TEN,
        suite = CardSuite.DIAMOND,
        image = SvgImage("card_ten_diamond.svg"),
        darkImage = null
    ),
    TEN_OF_HEARTS(
        rank = CardRank.TEN,
        suite = CardSuite.HEARTS,
        image = SvgImage("card_ten_hearts.svg"),
        darkImage = null
    ),

    // JUDGE
    JUDGE_OF_SPADES(
        rank = CardRank.JUDGE,
        suite = CardSuite.SPADE,
        image = SvgImage("card_judge_spade.svg"),
        darkImage = SvgImage("card_judge_spade_dark.svg")
    ),
    JUDGE_OF_CLOVER(
        rank = CardRank.JUDGE,
        suite = CardSuite.CLOVER,
        image = SvgImage("card_judge_clover.svg"),
        darkImage = SvgImage("card_judge_spade_dark.svg")
    ),
    JUDGE_OF_DIAMOND(
        rank = CardRank.JUDGE,
        suite = CardSuite.DIAMOND,
        image = SvgImage("card_judge_diamond.svg"),
        darkImage = SvgImage("card_judge_diamond_dark.svg")
    ),
    JUDGE_OF_HEARTS(
        rank = CardRank.JUDGE,
        suite = CardSuite.HEARTS,
        image = SvgImage("card_judge_hearts.svg"),
        darkImage = SvgImage("card_judge_hearts_dark.svg")
    ),

    // QUEEN
    QUEEN_OF_SPADES(
        rank = CardRank.QUEEN,
        suite = CardSuite.SPADE,
        image = SvgImage("card_queen_spade.svg"),
        darkImage = SvgImage("card_queen_spade_dark.svg")
    ),
    QUEEN_OF_CLOVER(
        rank = CardRank.QUEEN,
        suite = CardSuite.CLOVER,
        image = SvgImage("card_queen_clover.svg"),
        darkImage = SvgImage("card_queen_clover_dark.svg")
    ),
    QUEEN_OF_DIAMOND(
        rank = CardRank.QUEEN,
        suite = CardSuite.DIAMOND,
        image = SvgImage("card_queen_diamond.svg"),
        darkImage = SvgImage("card_queen_diamond_dark.svg")
    ),
    QUEEN_OF_HEARTS(
        rank = CardRank.QUEEN,
        suite = CardSuite.HEARTS,
        image = SvgImage("card_queen_hearts.svg"),
        darkImage = SvgImage("card_queen_hearts_dark.svg")
    ),

    // KING
    KING_OF_SPADES(
        rank = CardRank.KING,
        suite = CardSuite.SPADE,
        image = SvgImage("card_king_spade.svg"),
        darkImage = SvgImage("card_king_spade_dark.svg")
    ),
    KING_OF_CLOVER(
        rank = CardRank.KING,
        suite = CardSuite.CLOVER,
        image = SvgImage("card_king_clover.svg"),
        darkImage = SvgImage("card_king_clover_dark.svg")
    ),
    KING_OF_DIAMOND(
        rank = CardRank.KING,
        suite = CardSuite.DIAMOND,
        image = SvgImage("card_king_diamond.svg"),
        darkImage = SvgImage("card_king_diamond_dark.svg")
    ),
    KING_OF_HEARTS(
        rank = CardRank.KING,
        suite = CardSuite.HEARTS,
        image = SvgImage("card_king_hearts.svg"),
        darkImage = SvgImage("card_king_hearts_dark.svg")
    );

}
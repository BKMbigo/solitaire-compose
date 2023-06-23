package com.github.bkmbigo.solitaireanimation.models

enum class Card(
    val rank: CardRank,
    val suite: CardSuite,
    val imageFilename: String,
    val darkImageFilename: String?
) {

    // ACE
    ACE_OF_SPADES(
        rank = CardRank.ACE,
        suite = CardSuite.SPADE,
        imageFilename = "card_ace_spade.svg",
        darkImageFilename = "card_ace_spade_dark.svg"
    ),
    ACE_OF_CLOVER(
        rank = CardRank.ACE,
        suite = CardSuite.CLOVER,
        imageFilename = "card_ace_clover.svg",
        darkImageFilename = "card_ace_clover_dark.svg"
        ),
    ACE_OF_DIAMOND(
        rank = CardRank.ACE,
        suite = CardSuite.DIAMOND,
        imageFilename = "card_ace_diamond.svg",
        darkImageFilename = null
    ),
    ACE_OF_HEARTS(
        rank = CardRank.ACE,
        suite = CardSuite.HEARTS,
        imageFilename = "card_ace_hearts.svg",
        darkImageFilename = null
    ),

    // TWO
    TWO_OF_SPADES(
        rank = CardRank.TWO,
        suite = CardSuite.SPADE,
        imageFilename = "card_two_spades.svg",
        darkImageFilename = "card_two_spades_dark.svg"
    ),
    TWO_OF_CLOVER(
        rank = CardRank.TWO,
        suite = CardSuite.CLOVER,
        imageFilename = "card_two_clover.svg",
        darkImageFilename = "card_two_clover_dark.svg"
    ),
    TWO_OF_DIAMOND(
        rank = CardRank.TWO,
        suite = CardSuite.DIAMOND,
        imageFilename = "card_two_diamond.svg",
        darkImageFilename = null
    ),
    TWO_OF_HEARTS(
        rank = CardRank.TWO,
        suite = CardSuite.HEARTS,
        imageFilename = "card_two_hearts.svg",
        darkImageFilename = null
    ),

    // THREE
    THREE_OF_SPADES(
        rank = CardRank.THREE,
        suite = CardSuite.SPADE,
        imageFilename = "card_three_spade.svg",
        darkImageFilename = "card_three_spade_dark.svg"
    ),
    THREE_OF_CLOVER(
        rank = CardRank.THREE,
        suite = CardSuite.CLOVER,
        imageFilename = "card_three_clover.svg",
        darkImageFilename = "card_three_clover_dark.svg"
    ),
    THREE_OF_DIAMOND(
        rank = CardRank.THREE,
        suite = CardSuite.DIAMOND,
        imageFilename = "card_three_diamond.svg",
        darkImageFilename = null
    ),
    THREE_OF_HEARTS(
        rank = CardRank.THREE,
        suite = CardSuite.HEARTS,
        imageFilename = "card_three_hearts.svg",
        darkImageFilename = null
    ),

    // FOUR
    FOUR_OF_SPADES(
        rank = CardRank.FOUR,
        suite = CardSuite.SPADE,
        imageFilename = "card_four_spade.svg",
        darkImageFilename = "card_four_spade_dark.svg"
    ),
    FOUR_OF_CLOVER(
        rank = CardRank.FOUR,
        suite = CardSuite.CLOVER,
        imageFilename = "card_four_clover.svg",
        darkImageFilename = "card_four_clover_dark.svg"
    ),
    FOUR_OF_DIAMOND(
        rank = CardRank.FOUR,
        suite = CardSuite.DIAMOND,
        imageFilename = "card_four_diamond.svg",
        darkImageFilename = null
    ),
    FOUR_OF_HEARTS(
        rank = CardRank.FOUR,
        suite = CardSuite.HEARTS,
        imageFilename = "card_four_hearts.svg",
        darkImageFilename = null
    ),

    // FIVE
    FIVE_OF_SPADES(
        rank = CardRank.FIVE,
        suite = CardSuite.SPADE,
        imageFilename = "card_five_spade.svg",
        darkImageFilename = "card_five_spade_dark.svg"
    ),
    FIVE_OF_CLOVER(
        rank = CardRank.FIVE,
        suite = CardSuite.CLOVER,
        imageFilename = "card_five_clover.svg",
        darkImageFilename = "card_five_clover_dark.svg"
    ),
    FIVE_OF_DIAMOND(
        rank = CardRank.FIVE,
        suite = CardSuite.DIAMOND,
        imageFilename = "card_five_diamond.svg",
        darkImageFilename = null
    ),
    FIVE_OF_HEARTS(
        rank = CardRank.FIVE,
        suite = CardSuite.HEARTS,
        imageFilename = "card_five_hearts.svg",
        darkImageFilename = null
    ),

    // SIX
    SIX_OF_SPADES(
        rank = CardRank.SIX,
        suite = CardSuite.SPADE,
        imageFilename = "card_six_spade.svg",
        darkImageFilename = "card_six_spade_dark.svg"
    ),
    SIX_OF_CLOVER(
        rank = CardRank.SIX,
        suite = CardSuite.CLOVER,
        imageFilename = "card_six_clover.svg",
        darkImageFilename = "card_six_clover_dark.svg"
    ),
    SIX_OF_DIAMOND(
        rank = CardRank.SIX,
        suite = CardSuite.DIAMOND,
        imageFilename = "card_six_diamond.svg",
        darkImageFilename = null
    ),
    SIX_OF_HEARTS(
        rank = CardRank.SIX,
        suite = CardSuite.HEARTS,
        imageFilename = "card_six_hearts.svg",
        darkImageFilename = null
    ),

    // SEVEN
    SEVEN_OF_SPADES(
        rank = CardRank.SEVEN,
        suite = CardSuite.SPADE,
        imageFilename = "card_seven_spade.svg",
        darkImageFilename = "card_seven_spade_dark.svg"
    ),
    SEVEN_OF_CLOVER(
        rank = CardRank.SEVEN,
        suite = CardSuite.CLOVER,
        imageFilename = "card_seven_clover.svg",
        darkImageFilename = "card_seven_clover_dark.svg"
    ),
    SEVEN_OF_DIAMOND(
        rank = CardRank.SEVEN,
        suite = CardSuite.DIAMOND,
        imageFilename = "card_seven_diamond.svg",
        darkImageFilename = null
    ),
    SEVEN_OF_HEARTS(
        rank = CardRank.SEVEN,
        suite = CardSuite.HEARTS,
        imageFilename = "card_seven_hearts.svg",
        darkImageFilename = null
    ),

    // EIGHT
    EIGHT_OF_SPADES(
        rank = CardRank.EIGHT,
        suite = CardSuite.SPADE,
        imageFilename = "card_eight_spade.svg",
        darkImageFilename = "card_eight_spade_dark.svg"
    ),
    EIGHT_OF_CLOVER(
        rank = CardRank.EIGHT,
        suite = CardSuite.CLOVER,
        imageFilename = "card_eight_clover.svg",
        darkImageFilename = "card_eight_clover_dark.svg"
    ),
    EIGHT_OF_DIAMOND(
        rank = CardRank.EIGHT,
        suite = CardSuite.DIAMOND,
        imageFilename = "card_eight_diamond.svg",
        darkImageFilename = null
    ),
    EIGHT_OF_HEARTS(
        rank = CardRank.EIGHT,
        suite = CardSuite.HEARTS,
        imageFilename = "card_eight_hearts.svg",
        darkImageFilename = null
    ),

    // NINE
    NINE_OF_SPADES(
        rank = CardRank.NINE,
        suite = CardSuite.SPADE,
        imageFilename = "card_nine_spade.svg",
        darkImageFilename = "card_nine_spade_dark.svg"
    ),
    NINE_OF_CLOVER(
        rank = CardRank.NINE,
        suite = CardSuite.CLOVER,
        imageFilename = "card_nine_clover.svg",
        darkImageFilename = "card_nine_clover_dark.svg"
    ),
    NINE_OF_DIAMOND(
        rank = CardRank.NINE,
        suite = CardSuite.DIAMOND,
        imageFilename = "card_nine_diamond.svg",
        darkImageFilename = null
    ),
    NINE_OF_HEARTS(
        rank = CardRank.NINE,
        suite = CardSuite.HEARTS,
        imageFilename = "card_nine_hearts.svg",
        darkImageFilename = null
    ),

    // TEN
    TEN_OF_SPADES(
        rank = CardRank.TEN,
        suite = CardSuite.SPADE,
        imageFilename = "card_ten_spade.svg",
        darkImageFilename = "card_ten_spade_dark.svg"
    ),
    TEN_OF_CLOVER(
        rank = CardRank.TEN,
        suite = CardSuite.CLOVER,
        imageFilename = "card_ten_clover.svg",
        darkImageFilename = "card_ten_clover_dark.svg"
    ),
    TEN_OF_DIAMOND(
        rank = CardRank.TEN,
        suite = CardSuite.DIAMOND,
        imageFilename = "card_ten_diamond.svg",
        darkImageFilename = null
    ),
    TEN_OF_HEARTS(
        rank = CardRank.TEN,
        suite = CardSuite.HEARTS,
        imageFilename = "card_ten_hearts.svg",
        darkImageFilename = null
    ),

    // JUDGE
    JUDGE_OF_SPADES(
        rank = CardRank.JUDGE,
        suite = CardSuite.SPADE,
        imageFilename = "card_judge_spade.svg",
        darkImageFilename = "card_judge_spade_dark.svg"
    ),
    JUDGE_OF_CLOVER(
        rank = CardRank.JUDGE,
        suite = CardSuite.CLOVER,
        imageFilename = "card_judge_clover.svg",
        darkImageFilename = "card_judge_spade_dark.svg"
    ),
    JUDGE_OF_DIAMOND(
        rank = CardRank.JUDGE,
        suite = CardSuite.DIAMOND,
        imageFilename = "card_judge_diamond.svg",
        darkImageFilename = "card_judge_diamond_dark.svg"
    ),
    JUDGE_OF_HEARTS(
        rank = CardRank.JUDGE,
        suite = CardSuite.HEARTS,
        imageFilename = "card_judge_hearts.svg",
        darkImageFilename = "card_judge_hearts_dark.svg"
    ),

    // QUEEN
    QUEEN_OF_SPADES(
        rank = CardRank.QUEEN,
        suite = CardSuite.SPADE,
        imageFilename = "card_queen_spade.svg",
        darkImageFilename = "card_queen_spade_dark.svg"
    ),
    QUEEN_OF_CLOVER(
        rank = CardRank.QUEEN,
        suite = CardSuite.CLOVER,
        imageFilename = "card_queen_clover.svg",
        darkImageFilename = "card_queen_clover_dark.svg"
    ),
    QUEEN_OF_DIAMOND(
        rank = CardRank.QUEEN,
        suite = CardSuite.DIAMOND,
        imageFilename = "card_queen_diamond.svg",
        darkImageFilename = "card_queen_diamond_dark.svg"
    ),
    QUEEN_OF_HEARTS(
        rank = CardRank.QUEEN,
        suite = CardSuite.HEARTS,
        imageFilename = "card_queen_hearts.svg",
        darkImageFilename = "card_queen_hearts_dark.svg"
    ),

    // KING
    KING_OF_SPADES(
        rank = CardRank.KING,
        suite = CardSuite.SPADE,
        imageFilename = "card_king_spade.svg",
        darkImageFilename = "card_king_spade_dark.svg"
    ),
    KING_OF_CLOVER(
        rank = CardRank.KING,
        suite = CardSuite.CLOVER,
        imageFilename = "card_king_clover.svg",
        darkImageFilename = "card_king_clover_dark.svg"
    ),
    KING_OF_DIAMOND(
        rank = CardRank.KING,
        suite = CardSuite.DIAMOND,
        imageFilename = "card_king_diamond.svg",
        darkImageFilename = "card_king_diamond_dark.svg"
    ),
    KING_OF_HEARTS(
        rank = CardRank.KING,
        suite = CardSuite.HEARTS,
        imageFilename = "card_king_hearts.svg",
        darkImageFilename = "card_king_hearts_dark.svg"
    );
}
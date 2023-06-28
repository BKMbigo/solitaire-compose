package com.github.bkmbigo.solitaireanimation.models

import androidx.compose.ui.graphics.Color

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
        imageFilename = "card_ace_spade.xml",
        darkImageFilename = "card_ace_spade_dark.xml"
    ),
    ACE_OF_CLOVER(
        rank = CardRank.ACE,
        suite = CardSuite.CLOVER,
        imageFilename = "card_ace_clover.xml",
        darkImageFilename = "card_ace_clover_dark.xml"
        ),
    ACE_OF_DIAMOND(
        rank = CardRank.ACE,
        suite = CardSuite.DIAMOND,
        imageFilename = "card_ace_diamond.xml",
        darkImageFilename = null
    ),
    ACE_OF_HEARTS(
        rank = CardRank.ACE,
        suite = CardSuite.HEARTS,
        imageFilename = "card_ace_hearts.xml",
        darkImageFilename = null
    ),

    // TWO
    TWO_OF_SPADES(
        rank = CardRank.TWO,
        suite = CardSuite.SPADE,
        imageFilename = "card_two_spades.xml",
        darkImageFilename = "card_two_spades_dark.xml"
    ),
    TWO_OF_CLOVER(
        rank = CardRank.TWO,
        suite = CardSuite.CLOVER,
        imageFilename = "card_two_clover.xml",
        darkImageFilename = "card_two_clover_dark.xml"
    ),
    TWO_OF_DIAMOND(
        rank = CardRank.TWO,
        suite = CardSuite.DIAMOND,
        imageFilename = "card_two_diamond.xml",
        darkImageFilename = null
    ),
    TWO_OF_HEARTS(
        rank = CardRank.TWO,
        suite = CardSuite.HEARTS,
        imageFilename = "card_two_hearts.xml",
        darkImageFilename = null
    ),

    // THREE
    THREE_OF_SPADES(
        rank = CardRank.THREE,
        suite = CardSuite.SPADE,
        imageFilename = "card_three_spade.xml",
        darkImageFilename = "card_three_spade_dark.xml"
    ),
    THREE_OF_CLOVER(
        rank = CardRank.THREE,
        suite = CardSuite.CLOVER,
        imageFilename = "card_three_clover.xml",
        darkImageFilename = "card_three_clover_dark.xml"
    ),
    THREE_OF_DIAMOND(
        rank = CardRank.THREE,
        suite = CardSuite.DIAMOND,
        imageFilename = "card_three_diamond.xml",
        darkImageFilename = null
    ),
    THREE_OF_HEARTS(
        rank = CardRank.THREE,
        suite = CardSuite.HEARTS,
        imageFilename = "card_three_hearts.xml",
        darkImageFilename = null
    ),

    // FOUR
    FOUR_OF_SPADES(
        rank = CardRank.FOUR,
        suite = CardSuite.SPADE,
        imageFilename = "card_four_spade.xml",
        darkImageFilename = "card_four_spade_dark.xml"
    ),
    FOUR_OF_CLOVER(
        rank = CardRank.FOUR,
        suite = CardSuite.CLOVER,
        imageFilename = "card_four_clover.xml",
        darkImageFilename = "card_four_clover_dark.xml"
    ),
    FOUR_OF_DIAMOND(
        rank = CardRank.FOUR,
        suite = CardSuite.DIAMOND,
        imageFilename = "card_four_diamond.xml",
        darkImageFilename = null
    ),
    FOUR_OF_HEARTS(
        rank = CardRank.FOUR,
        suite = CardSuite.HEARTS,
        imageFilename = "card_four_hearts.xml",
        darkImageFilename = null
    ),

    // FIVE
    FIVE_OF_SPADES(
        rank = CardRank.FIVE,
        suite = CardSuite.SPADE,
        imageFilename = "card_five_spade.xml",
        darkImageFilename = "card_five_spade_dark.xml"
    ),
    FIVE_OF_CLOVER(
        rank = CardRank.FIVE,
        suite = CardSuite.CLOVER,
        imageFilename = "card_five_clover.xml",
        darkImageFilename = "card_five_clover_dark.xml"
    ),
    FIVE_OF_DIAMOND(
        rank = CardRank.FIVE,
        suite = CardSuite.DIAMOND,
        imageFilename = "card_five_diamond.xml",
        darkImageFilename = null
    ),
    FIVE_OF_HEARTS(
        rank = CardRank.FIVE,
        suite = CardSuite.HEARTS,
        imageFilename = "card_five_hearts.xml",
        darkImageFilename = null
    ),

    // SIX
    SIX_OF_SPADES(
        rank = CardRank.SIX,
        suite = CardSuite.SPADE,
        imageFilename = "card_six_spade.xml",
        darkImageFilename = "card_six_spade_dark.xml"
    ),
    SIX_OF_CLOVER(
        rank = CardRank.SIX,
        suite = CardSuite.CLOVER,
        imageFilename = "card_six_clover.xml",
        darkImageFilename = "card_six_clover_dark.xml"
    ),
    SIX_OF_DIAMOND(
        rank = CardRank.SIX,
        suite = CardSuite.DIAMOND,
        imageFilename = "card_six_diamond.xml",
        darkImageFilename = null
    ),
    SIX_OF_HEARTS(
        rank = CardRank.SIX,
        suite = CardSuite.HEARTS,
        imageFilename = "card_six_hearts.xml",
        darkImageFilename = null
    ),

    // SEVEN
    SEVEN_OF_SPADES(
        rank = CardRank.SEVEN,
        suite = CardSuite.SPADE,
        imageFilename = "card_seven_spade.xml",
        darkImageFilename = "card_seven_spade_dark.xml"
    ),
    SEVEN_OF_CLOVER(
        rank = CardRank.SEVEN,
        suite = CardSuite.CLOVER,
        imageFilename = "card_seven_clover.xml",
        darkImageFilename = "card_seven_clover_dark.xml"
    ),
    SEVEN_OF_DIAMOND(
        rank = CardRank.SEVEN,
        suite = CardSuite.DIAMOND,
        imageFilename = "card_seven_diamond.xml",
        darkImageFilename = null
    ),
    SEVEN_OF_HEARTS(
        rank = CardRank.SEVEN,
        suite = CardSuite.HEARTS,
        imageFilename = "card_seven_hearts.xml",
        darkImageFilename = null
    ),

    // EIGHT
    EIGHT_OF_SPADES(
        rank = CardRank.EIGHT,
        suite = CardSuite.SPADE,
        imageFilename = "card_eight_spade.xml",
        darkImageFilename = "card_eight_spade_dark.xml"
    ),
    EIGHT_OF_CLOVER(
        rank = CardRank.EIGHT,
        suite = CardSuite.CLOVER,
        imageFilename = "card_eight_clover.xml",
        darkImageFilename = "card_eight_clover_dark.xml"
    ),
    EIGHT_OF_DIAMOND(
        rank = CardRank.EIGHT,
        suite = CardSuite.DIAMOND,
        imageFilename = "card_eight_diamond.xml",
        darkImageFilename = null
    ),
    EIGHT_OF_HEARTS(
        rank = CardRank.EIGHT,
        suite = CardSuite.HEARTS,
        imageFilename = "card_eight_hearts.xml",
        darkImageFilename = null
    ),

    // NINE
    NINE_OF_SPADES(
        rank = CardRank.NINE,
        suite = CardSuite.SPADE,
        imageFilename = "card_nine_spade.xml",
        darkImageFilename = "card_nine_spade_dark.xml"
    ),
    NINE_OF_CLOVER(
        rank = CardRank.NINE,
        suite = CardSuite.CLOVER,
        imageFilename = "card_nine_clover.xml",
        darkImageFilename = "card_nine_clover_dark.xml"
    ),
    NINE_OF_DIAMOND(
        rank = CardRank.NINE,
        suite = CardSuite.DIAMOND,
        imageFilename = "card_nine_diamond.xml",
        darkImageFilename = null
    ),
    NINE_OF_HEARTS(
        rank = CardRank.NINE,
        suite = CardSuite.HEARTS,
        imageFilename = "card_nine_hearts.xml",
        darkImageFilename = null
    ),

    // TEN
    TEN_OF_SPADES(
        rank = CardRank.TEN,
        suite = CardSuite.SPADE,
        imageFilename = "card_ten_spade.xml",
        darkImageFilename = "card_ten_spade_dark.xml"
    ),
    TEN_OF_CLOVER(
        rank = CardRank.TEN,
        suite = CardSuite.CLOVER,
        imageFilename = "card_ten_clover.xml",
        darkImageFilename = "card_ten_clover_dark.xml"
    ),
    TEN_OF_DIAMOND(
        rank = CardRank.TEN,
        suite = CardSuite.DIAMOND,
        imageFilename = "card_ten_diamond.xml",
        darkImageFilename = null
    ),
    TEN_OF_HEARTS(
        rank = CardRank.TEN,
        suite = CardSuite.HEARTS,
        imageFilename = "card_ten_hearts.xml",
        darkImageFilename = null
    ),

    // JUDGE
    JUDGE_OF_SPADES(
        rank = CardRank.JUDGE,
        suite = CardSuite.SPADE,
        imageFilename = "card_judge_spade.xml",
        darkImageFilename = "card_judge_spade_dark.xml"
    ),
    JUDGE_OF_CLOVER(
        rank = CardRank.JUDGE,
        suite = CardSuite.CLOVER,
        imageFilename = "card_judge_clover.xml",
        darkImageFilename = "card_judge_spade_dark.xml"
    ),
    JUDGE_OF_DIAMOND(
        rank = CardRank.JUDGE,
        suite = CardSuite.DIAMOND,
        imageFilename = "card_judge_diamond.xml",
        darkImageFilename = "card_judge_diamond_dark.xml"
    ),
    JUDGE_OF_HEARTS(
        rank = CardRank.JUDGE,
        suite = CardSuite.HEARTS,
        imageFilename = "card_judge_hearts.xml",
        darkImageFilename = "card_judge_hearts_dark.xml"
    ),

    // QUEEN
    QUEEN_OF_SPADES(
        rank = CardRank.QUEEN,
        suite = CardSuite.SPADE,
        imageFilename = "card_queen_spade.xml",
        darkImageFilename = "card_queen_spade_dark.xml"
    ),
    QUEEN_OF_CLOVER(
        rank = CardRank.QUEEN,
        suite = CardSuite.CLOVER,
        imageFilename = "card_queen_clover.xml",
        darkImageFilename = "card_queen_clover_dark.xml"
    ),
    QUEEN_OF_DIAMOND(
        rank = CardRank.QUEEN,
        suite = CardSuite.DIAMOND,
        imageFilename = "card_queen_diamond.xml",
        darkImageFilename = "card_queen_diamond_dark.xml"
    ),
    QUEEN_OF_HEARTS(
        rank = CardRank.QUEEN,
        suite = CardSuite.HEARTS,
        imageFilename = "card_queen_hearts.xml",
        darkImageFilename = "card_queen_hearts_dark.xml"
    ),

    // KING
    KING_OF_SPADES(
        rank = CardRank.KING,
        suite = CardSuite.SPADE,
        imageFilename = "card_king_spade.xml",
        darkImageFilename = "card_king_spade_dark.xml"
    ),
    KING_OF_CLOVER(
        rank = CardRank.KING,
        suite = CardSuite.CLOVER,
        imageFilename = "card_king_clover.xml",
        darkImageFilename = "card_king_clover_dark.xml"
    ),
    KING_OF_DIAMOND(
        rank = CardRank.KING,
        suite = CardSuite.DIAMOND,
        imageFilename = "card_king_diamond.xml",
        darkImageFilename = "card_king_diamond_dark.xml"
    ),
    KING_OF_HEARTS(
        rank = CardRank.KING,
        suite = CardSuite.HEARTS,
        imageFilename = "card_king_hearts.xml",
        darkImageFilename = "card_king_hearts_dark.xml"
    );
    
    companion object {
        val cardBackFilename = "card_back.xml"
        val redColor = Color(0xFFDF0000)
    }
}
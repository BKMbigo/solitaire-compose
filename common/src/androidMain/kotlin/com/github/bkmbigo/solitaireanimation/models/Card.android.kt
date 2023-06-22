package com.github.bkmbigo.solitaireanimation.models

import androidx.annotation.DrawableRes
import com.github.bkmbigo.solitaireanimation.R
import com.github.bkmbigo.solitaireanimation.presentation.utils.SvgImage

//actual enum class Card(
//    actual val rank: CardRank,
//    actual val suite: CardSuite,
//    actual val image: SvgImage,
//    actual val darkImage: SvgImage?
//) {
//
//    // ACE
//    ACE_OF_SPADES(
//        rank = CardRank.ACE,
//        suite = CardSuite.SPADE,
//        image = SvgImage(R.drawable.card_ace_spade),
//        darkImage = SvgImage(R.drawable.card_ace_spade_dark)
//    ),
//    ACE_OF_CLOVER(
//        rank = CardRank.ACE,
//        suite = CardSuite.CLOVER,
//        image = SvgImage(R.drawable.card_ace_clover),
//        darkImage = SvgImage(R.drawable.card_ace_clover_dark)
//    ),
//    ACE_OF_DIAMOND(
//        rank = CardRank.ACE,
//        suite = CardSuite.DIAMOND,
//        image = SvgImage(R.drawable.card_ace_diamond),
//        darkImage = null
//    ),
//    ACE_OF_HEARTS(
//        rank = CardRank.ACE,
//        suite = CardSuite.HEARTS,
//        image = SvgImage(R.drawable.card_ace_hearts),
//        darkImage = null
//    ),
//
//    // TWO
//    TWO_OF_SPADES(
//        rank = CardRank.TWO,
//        suite = CardSuite.SPADE,
//        image = SvgImage(R.drawable.card_two_spade),
//        darkImage = SvgImage(R.drawable.card_two_spade_dark)
//    ),
//    TWO_OF_CLOVER(
//        rank = CardRank.TWO,
//        suite = CardSuite.CLOVER,
//        image = SvgImage(R.drawable.card_two_clover),
//        darkImage = SvgImage(R.drawable.card_two_clover_dark)
//    ),
//    TWO_OF_DIAMOND(
//        rank = CardRank.TWO,
//        suite = CardSuite.DIAMOND,
//        image = SvgImage(R.drawable.card_two_diamond),
//        darkImage = null
//    ),
//    TWO_OF_HEARTS(
//        rank = CardRank.TWO,
//        suite = CardSuite.HEARTS,
//        image = SvgImage(R.drawable.card_two_hearts),
//        darkImage = null
//    ),
//
//    // THREE
//    THREE_OF_SPADES(
//        rank = CardRank.THREE,
//        suite = CardSuite.SPADE,
//        image = SvgImage(R.drawable.card_three_spade),
//        darkImage = SvgImage(R.drawable.card_three_spade_dark)
//    ),
//    THREE_OF_CLOVER(
//        rank = CardRank.THREE,
//        suite = CardSuite.CLOVER,
//        image = SvgImage(R.drawable.card_three_clover),
//        darkImage = SvgImage(R.drawable.card_three_clover_dark)
//    ),
//    THREE_OF_DIAMOND(
//        rank = CardRank.THREE,
//        suite = CardSuite.DIAMOND,
//        image = SvgImage(R.drawable.card_three_diamond),
//        darkImage = null
//    ),
//    THREE_OF_HEARTS(
//        rank = CardRank.THREE,
//        suite = CardSuite.HEARTS,
//        image = SvgImage(R.drawable.card_three_hearts),
//        darkImage = null
//    ),
//
//    // FOUR
//    FOUR_OF_SPADES(
//        rank = CardRank.FOUR,
//        suite = CardSuite.SPADE,
//        image = SvgImage(R.drawable.card_four_spade),
//        darkImage = SvgImage(R.drawable.card_four_spade_dark)
//    ),
//    FOUR_OF_CLOVER(
//        rank = CardRank.FOUR,
//        suite = CardSuite.CLOVER,
//        image = SvgImage(R.drawable.card_four_clover),
//        darkImage = SvgImage(R.drawable.card_four_clover_dark)
//    ),
//    FOUR_OF_DIAMOND(
//        rank = CardRank.FOUR,
//        suite = CardSuite.DIAMOND,
//        image = SvgImage(R.drawable.card_four_diamond),
//        darkImage = null
//    ),
//    FOUR_OF_HEARTS(
//        rank = CardRank.FOUR,
//        suite = CardSuite.HEARTS,
//        image = SvgImage(R.drawable.card_four_hearts),
//        darkImage =null
//    ),
//
//    // FIVE
//    FIVE_OF_SPADES(
//        rank = CardRank.FIVE,
//        suite = CardSuite.SPADE,
//        image = SvgImage(R.drawable.card_five_spade),
//        darkImage = SvgImage(R.drawable.card_five_spade_dark)
//    ),
//    FIVE_OF_CLOVER(
//        rank = CardRank.FIVE,
//        suite = CardSuite.CLOVER,
//        image = SvgImage(R.drawable.card_five_clover),
//        darkImage = SvgImage(R.drawable.card_five_clover_dark)
//    ),
//    FIVE_OF_DIAMOND(
//        rank = CardRank.FIVE,
//        suite = CardSuite.DIAMOND,
//        image = SvgImage(R.drawable.card_five_diamond),
//        darkImage = null
//    ),
//    FIVE_OF_HEARTS(
//        rank = CardRank.FIVE,
//        suite = CardSuite.HEARTS,
//        image = SvgImage(R.drawable.card_five_hearts),
//        darkImage = null
//    ),
//
//    // SIX
//    SIX_OF_SPADES(
//        rank = CardRank.SIX,
//        suite = CardSuite.SPADE,
//        image = SvgImage(R.drawable.card_six_spade),
//        darkImage = SvgImage(R.drawable.card_six_spade_dark)
//    ),
//    SIX_OF_CLOVER(
//        rank = CardRank.SIX,
//        suite = CardSuite.CLOVER,
//        image = SvgImage(R.drawable.card_six_clover),
//        darkImage = SvgImage(R.drawable.card_five_clover_dark)
//    ),
//    SIX_OF_DIAMOND(
//        rank = CardRank.SIX,
//        suite = CardSuite.DIAMOND,
//        image = SvgImage(R.drawable.card_six_diamond),
//        darkImage = null
//    ),
//    SIX_OF_HEARTS(
//        rank = CardRank.SIX,
//        suite = CardSuite.HEARTS,
//        image = SvgImage(R.drawable.card_six_hearts),
//        darkImage = null
//    ),
//
//    // SEVEN
//    SEVEN_OF_SPADES(
//        rank = CardRank.SEVEN,
//        suite = CardSuite.SPADE,
//        image = SvgImage(R.drawable.card_seven_spade),
//        darkImage = SvgImage(R.drawable.card_seven_spade_dark)
//    ),
//    SEVEN_OF_CLOVER(
//        rank = CardRank.SEVEN,
//        suite = CardSuite.CLOVER,
//        image = SvgImage(R.drawable.card_seven_clover),
//        darkImage = SvgImage(R.drawable.card_seven_clover_dark)
//    ),
//    SEVEN_OF_DIAMOND(
//        rank = CardRank.SEVEN,
//        suite = CardSuite.DIAMOND,
//        image = SvgImage(R.drawable.card_seven_diamond),
//        darkImage = null
//    ),
//    SEVEN_OF_HEARTS(
//        rank = CardRank.SEVEN,
//        suite = CardSuite.HEARTS,
//        image = SvgImage(R.drawable.card_seven_hearts),
//        darkImage = null
//    ),
//
//    // EIGHT
//    EIGHT_OF_SPADES(
//        rank = CardRank.EIGHT,
//        suite = CardSuite.SPADE,
//        image = SvgImage(R.drawable.card_eight_spade),
//        darkImage = SvgImage(R.drawable.card_eight_spade_dark)
//    ),
//    EIGHT_OF_CLOVER(
//        rank = CardRank.EIGHT,
//        suite = CardSuite.CLOVER,
//        image = SvgImage(R.drawable.card_eight_clover),
//        darkImage = SvgImage(R.drawable.card_eight_clover_dark)
//    ),
//    EIGHT_OF_DIAMOND(
//        rank = CardRank.EIGHT,
//        suite = CardSuite.DIAMOND,
//        image = SvgImage(R.drawable.card_eight_diamond),
//        darkImage = null
//    ),
//    EIGHT_OF_HEARTS(
//        rank = CardRank.EIGHT,
//        suite = CardSuite.HEARTS,
//        image = SvgImage(R.drawable.card_eight_hearts),
//        darkImage = null
//    ),
//
//    // NINE
//    NINE_OF_SPADES(
//        rank = CardRank.NINE,
//        suite = CardSuite.SPADE,
//        image = SvgImage(R.drawable.card_nine_spade),
//        darkImage = SvgImage(R.drawable.card_nine_spade_dark)
//    ),
//    NINE_OF_CLOVER(
//        rank = CardRank.NINE,
//        suite = CardSuite.CLOVER,
//        image = SvgImage(R.drawable.card_nine_clover),
//        darkImage = SvgImage(R.drawable.card_nine_clover_dark)
//    ),
//    NINE_OF_DIAMOND(
//        rank = CardRank.NINE,
//        suite = CardSuite.DIAMOND,
//        image = SvgImage(R.drawable.card_nine_diamond),
//        darkImage = null
//    ),
//    NINE_OF_HEARTS(
//        rank = CardRank.NINE,
//        suite = CardSuite.HEARTS,
//        image = SvgImage(R.drawable.card_nine_hearts),
//        darkImage = null
//    ),
//
//    // TEN
//    TEN_OF_SPADES(
//        rank = CardRank.TEN,
//        suite = CardSuite.SPADE,
//        image = SvgImage(R.drawable.card_ten_spade),
//        darkImage = SvgImage(R.drawable.card_ten_spade_dark)
//    ),
//    TEN_OF_CLOVER(
//        rank = CardRank.TEN,
//        suite = CardSuite.CLOVER,
//        image = SvgImage(R.drawable.card_ten_clover),
//        darkImage = SvgImage(R.drawable.card_ten_clover_dark)
//    ),
//    TEN_OF_DIAMOND(
//        rank = CardRank.TEN,
//        suite = CardSuite.DIAMOND,
//        image = SvgImage(R.drawable.card_ten_diamond),
//        darkImage = null
//    ),
//    TEN_OF_HEARTS(
//        rank = CardRank.TEN,
//        suite = CardSuite.HEARTS,
//        image = SvgImage(R.drawable.card_ten_hearts),
//        darkImage = null
//    ),
//
//    // JUDGE
//    JUDGE_OF_SPADES(
//        rank = CardRank.JUDGE,
//        suite = CardSuite.SPADE,
//        image = SvgImage(R.drawable.card_judge_spade),
//        darkImage = SvgImage(R.drawable.card_judge_spade_dark)
//    ),
//    JUDGE_OF_CLOVER(
//        rank = CardRank.JUDGE,
//        suite = CardSuite.CLOVER,
//        image = SvgImage(R.drawable.card_judge_clover),
//        darkImage = SvgImage(R.drawable.card_judge_clover_dark)
//    ),
//    JUDGE_OF_DIAMOND(
//        rank = CardRank.JUDGE,
//        suite = CardSuite.DIAMOND,
//        image = SvgImage(R.drawable.card_judge_diamond),
//        darkImage = SvgImage(R.drawable.card_judge_diamond_dark)
//    ),
//    JUDGE_OF_HEARTS(
//        rank = CardRank.JUDGE,
//        suite = CardSuite.HEARTS,
//        image = SvgImage(R.drawable.card_judge_hearts),
//        darkImage = SvgImage(R.drawable.card_judge_hearts_dark)
//    ),
//
//    // QUEEN
//    QUEEN_OF_SPADES(
//        rank = CardRank.QUEEN,
//        suite = CardSuite.SPADE,
//        image = SvgImage(R.drawable.card_queen_spade),
//        darkImage = SvgImage(R.drawable.card_queen_spade_dark)
//    ),
//    QUEEN_OF_CLOVER(
//        rank = CardRank.QUEEN,
//        suite = CardSuite.CLOVER,
//        image = SvgImage(R.drawable.card_queen_clover),
//        darkImage = SvgImage(R.drawable.card_queen_clover_dark)
//    ),
//    QUEEN_OF_DIAMOND(
//        rank = CardRank.QUEEN,
//        suite = CardSuite.DIAMOND,
//        image = SvgImage(R.drawable.card_queen_diamond),
//        darkImage = SvgImage(R.drawable.card_queen_diamond_dark)
//    ),
//    QUEEN_OF_HEARTS(
//        rank = CardRank.QUEEN,
//        suite = CardSuite.HEARTS,
//        image = SvgImage(R.drawable.card_queen_hearts),
//        darkImage = SvgImage(R.drawable.card_queen_hearts_dark)
//    ),
//
//    // KING
//    KING_OF_SPADES(
//        rank = CardRank.KING,
//        suite = CardSuite.SPADE,
//        image = SvgImage(R.drawable.card_king_spade),
//        darkImage = SvgImage(R.drawable.card_king_spade_dark)
//    ),
//    KING_OF_CLOVER(
//        rank = CardRank.KING,
//        suite = CardSuite.CLOVER,
//        image = SvgImage(R.drawable.card_king_clover),
//        darkImage = SvgImage(R.drawable.card_king_clover_dark)
//    ),
//    KING_OF_DIAMOND(
//        rank = CardRank.KING,
//        suite = CardSuite.DIAMOND,
//        image = SvgImage(R.drawable.card_king_diamond),
//        darkImage = SvgImage(R.drawable.card_king_diamond_dark)
//    ),
//    KING_OF_HEARTS(
//        rank = CardRank.KING,
//        suite = CardSuite.HEARTS,
//        image = SvgImage(R.drawable.card_king_hearts),
//        darkImage = SvgImage(R.drawable.card_king_hearts_dark)
//    )
//}
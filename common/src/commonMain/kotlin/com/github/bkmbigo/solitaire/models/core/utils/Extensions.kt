package com.github.bkmbigo.solitaire.models.core.utils

import com.github.bkmbigo.solitaire.models.core.Card
import com.github.bkmbigo.solitaire.models.core.CardRank
import com.github.bkmbigo.solitaire.models.core.CardSuite

// Having fun with some DSL
/**
 * A DSL for picking a card
 * <p>
 *     ACE of SPADE returns Card.ACE_OF_SPADES
 * </p>*/
infix fun CardRank.of(suite: CardSuite): Card {
    return when (this) {
        CardRank.ACE -> when (suite) {
            CardSuite.SPADE -> Card.ACE_OF_SPADES
            CardSuite.CLOVER -> Card.ACE_OF_CLOVER
            CardSuite.HEARTS -> Card.ACE_OF_HEARTS
            CardSuite.DIAMOND -> Card.ACE_OF_DIAMOND
        }

        CardRank.TWO -> when (suite) {
            CardSuite.SPADE -> Card.TWO_OF_SPADES
            CardSuite.CLOVER -> Card.TWO_OF_CLOVER
            CardSuite.HEARTS -> Card.TWO_OF_HEARTS
            CardSuite.DIAMOND -> Card.TWO_OF_DIAMOND
        }

        CardRank.THREE -> when (suite) {
            CardSuite.SPADE -> Card.THREE_OF_SPADES
            CardSuite.CLOVER -> Card.THREE_OF_CLOVER
            CardSuite.HEARTS -> Card.THREE_OF_HEARTS
            CardSuite.DIAMOND -> Card.THREE_OF_DIAMOND
        }

        CardRank.FOUR -> when (suite) {
            CardSuite.SPADE -> Card.FOUR_OF_SPADES
            CardSuite.CLOVER -> Card.FOUR_OF_CLOVER
            CardSuite.HEARTS -> Card.FOUR_OF_HEARTS
            CardSuite.DIAMOND -> Card.FOUR_OF_DIAMOND
        }

        CardRank.FIVE -> when (suite) {
            CardSuite.SPADE -> Card.FIVE_OF_SPADES
            CardSuite.CLOVER -> Card.FIVE_OF_CLOVER
            CardSuite.HEARTS -> Card.FIVE_OF_HEARTS
            CardSuite.DIAMOND -> Card.FIVE_OF_DIAMOND
        }

        CardRank.SIX -> when (suite) {
            CardSuite.SPADE -> Card.SIX_OF_SPADES
            CardSuite.CLOVER -> Card.SIX_OF_CLOVER
            CardSuite.HEARTS -> Card.SIX_OF_HEARTS
            CardSuite.DIAMOND -> Card.SIX_OF_DIAMOND
        }

        CardRank.SEVEN -> when (suite) {
            CardSuite.SPADE -> Card.SEVEN_OF_SPADES
            CardSuite.CLOVER -> Card.SEVEN_OF_CLOVER
            CardSuite.HEARTS -> Card.SEVEN_OF_HEARTS
            CardSuite.DIAMOND -> Card.SEVEN_OF_DIAMOND
        }

        CardRank.EIGHT -> when (suite) {
            CardSuite.SPADE -> Card.EIGHT_OF_SPADES
            CardSuite.CLOVER -> Card.EIGHT_OF_CLOVER
            CardSuite.HEARTS -> Card.EIGHT_OF_HEARTS
            CardSuite.DIAMOND -> Card.EIGHT_OF_DIAMOND
        }

        CardRank.NINE -> when (suite) {
            CardSuite.SPADE -> Card.NINE_OF_SPADES
            CardSuite.CLOVER -> Card.NINE_OF_CLOVER
            CardSuite.HEARTS -> Card.NINE_OF_HEARTS
            CardSuite.DIAMOND -> Card.NINE_OF_DIAMOND
        }

        CardRank.TEN -> when (suite) {
            CardSuite.SPADE -> Card.TEN_OF_SPADES
            CardSuite.CLOVER -> Card.TEN_OF_CLOVER
            CardSuite.HEARTS -> Card.TEN_OF_HEARTS
            CardSuite.DIAMOND -> Card.TEN_OF_DIAMOND
        }

        CardRank.JUDGE -> when (suite) {
            CardSuite.SPADE -> Card.JUDGE_OF_SPADES
            CardSuite.CLOVER -> Card.JUDGE_OF_CLOVER
            CardSuite.HEARTS -> Card.JUDGE_OF_HEARTS
            CardSuite.DIAMOND -> Card.JUDGE_OF_DIAMOND
        }

        CardRank.QUEEN -> when (suite) {
            CardSuite.SPADE -> Card.QUEEN_OF_SPADES
            CardSuite.CLOVER -> Card.QUEEN_OF_CLOVER
            CardSuite.HEARTS -> Card.QUEEN_OF_HEARTS
            CardSuite.DIAMOND -> Card.QUEEN_OF_DIAMOND
        }

        CardRank.KING -> when (suite) {
            CardSuite.SPADE -> Card.KING_OF_SPADES
            CardSuite.CLOVER -> Card.KING_OF_CLOVER
            CardSuite.HEARTS -> Card.KING_OF_HEARTS
            CardSuite.DIAMOND -> Card.KING_OF_DIAMOND
        }
    }
}
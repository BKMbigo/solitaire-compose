package com.github.bkmbigo.solitaire.game.solitaire.fakes

import com.github.bkmbigo.solitaire.game.solitaire.SolitaireGame
import com.github.bkmbigo.solitaire.game.solitaire.SolitaireGameTestObject
import com.github.bkmbigo.solitaire.game.solitaire.TableStack
import com.github.bkmbigo.solitaire.models.core.CardRank.ACE
import com.github.bkmbigo.solitaire.models.core.CardRank.EIGHT
import com.github.bkmbigo.solitaire.models.core.CardRank.FIVE
import com.github.bkmbigo.solitaire.models.core.CardRank.FOUR
import com.github.bkmbigo.solitaire.models.core.CardRank.JUDGE
import com.github.bkmbigo.solitaire.models.core.CardRank.KING
import com.github.bkmbigo.solitaire.models.core.CardRank.NINE
import com.github.bkmbigo.solitaire.models.core.CardRank.QUEEN
import com.github.bkmbigo.solitaire.models.core.CardRank.SEVEN
import com.github.bkmbigo.solitaire.models.core.CardRank.SIX
import com.github.bkmbigo.solitaire.models.core.CardRank.TEN
import com.github.bkmbigo.solitaire.models.core.CardRank.THREE
import com.github.bkmbigo.solitaire.models.core.CardRank.TWO
import com.github.bkmbigo.solitaire.models.core.CardSuite.CLOVER
import com.github.bkmbigo.solitaire.models.core.CardSuite.DIAMOND
import com.github.bkmbigo.solitaire.models.core.CardSuite.HEARTS
import com.github.bkmbigo.solitaire.models.core.CardSuite.SPADE
import com.github.bkmbigo.solitaire.models.core.utils.of

/** Represents a game where the user has managed to arrange all cards on the table in four stacks. */
val SolitaireGameThree = SolitaireGameTestObject(
    game = SolitaireGame(
        deck = emptyList(),
        spadeFoundationStack = emptyList(),
        cloverFoundationStack = emptyList(),
        heartsFoundationStack = emptyList(),
        diamondFoundationStack = emptyList(),
        firstTableStackState = TableStack.EMPTY,
        secondTableStackState = TableStack.EMPTY,
        thirdTableStackState = TableStack.EMPTY,
        fourthTableStackState = TableStack(
            revealedCards = listOf(
                KING of SPADE,
                QUEEN of HEARTS,
                JUDGE of CLOVER,
                TEN of DIAMOND,
                NINE of CLOVER,
                EIGHT of HEARTS,
                SEVEN of SPADE,
                SIX of DIAMOND,
                FIVE of SPADE,
                FOUR of HEARTS,
                THREE of CLOVER,
                TWO of DIAMOND,
                ACE of CLOVER
            )
        ),
        fifthTableStackState = TableStack(
            revealedCards = listOf(
                KING of CLOVER,
                QUEEN of DIAMOND,
                JUDGE of SPADE,
                TEN of HEARTS,
                NINE of SPADE,
                EIGHT of DIAMOND,
                SEVEN of CLOVER,
                SIX of HEARTS,
                FIVE of CLOVER,
                FOUR of DIAMOND,
                THREE of SPADE,
                TWO of HEARTS,
                ACE of SPADE
            )
        ),
        sixthTableStackState = TableStack(
            revealedCards = listOf(
                KING of HEARTS,
                QUEEN of SPADE,
                JUDGE of DIAMOND,
                TEN of CLOVER,
                NINE of DIAMOND,
                EIGHT of SPADE,
                SEVEN of HEARTS,
                SIX of CLOVER,
                FIVE of HEARTS,
                FOUR of SPADE,
                THREE of DIAMOND,
                TWO of CLOVER,
                ACE of DIAMOND
            )
        ),
        seventhTableStackState = TableStack(
            revealedCards = listOf(
                KING of DIAMOND,
                QUEEN of CLOVER,
                JUDGE of HEARTS,
                TEN of SPADE,
                NINE of HEARTS,
                EIGHT of CLOVER,
                SEVEN of DIAMOND,
                SIX of SPADE,
                FIVE of DIAMOND,
                FOUR of CLOVER,
                THREE of HEARTS,
                TWO of SPADE,
                ACE of HEARTS
            )
        ),
    ),
    isWon = true,
    isDrawn = false,
    isValid = true,
    moveTests = emptyList()
)

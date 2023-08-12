package com.github.bkmbigo.solitaire.game.solitaire.fakes

import com.github.bkmbigo.solitaire.game.solitaire.SolitaireGameTestObject
import com.github.bkmbigo.solitaire.game.solitaire.SolitaireGame
import com.github.bkmbigo.solitaire.game.solitaire.TableStack
import com.github.bkmbigo.solitaire.models.core.CardRank.*
import com.github.bkmbigo.solitaire.models.core.CardSuite.*
import com.github.bkmbigo.solitaire.models.core.utils.of

/** Tests for invalidity. Represents a game where the user has managed to arrange all cards on the table in four stacks but a stack is repeated. */
val SolitaireGameSix = SolitaireGameTestObject(
    game = SolitaireGame(
        deck = emptyList(),
        spadeFoundationStack = emptyList(),
        cloverFoundationStack = emptyList(),
        heartsFoundationStack = emptyList(),
        diamondFoundationStack = emptyList(),
        firstTableStackState = TableStack.EMPTY,
        secondTableStackState = TableStack.EMPTY,
        thirdTableStackState = TableStack.EMPTY,
        fourthTableStackState = TableStack.EMPTY,
        fifthTableStackState = TableStack(
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
        sixthTableStackState = TableStack(      // INVALID: stack is repeated
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
        seventhTableStackState = TableStack(
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
        eighthTableStackState = TableStack(
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
    isWon = false,
    isDrawn = false,
    isValid = false,
    moveTests = emptyList()
)
package com.github.bkmbigo.solitaire.game.solitaire.fakes

import com.github.bkmbigo.solitaire.game.solitaire.SolitaireGame
import com.github.bkmbigo.solitaire.game.solitaire.SolitaireGameTestObject
import com.github.bkmbigo.solitaire.game.solitaire.TableStack
import com.github.bkmbigo.solitaire.models.core.Card
import com.github.bkmbigo.solitaire.models.core.CardSuite

/** Tests for validity. Has invalid foundation stack */
val SolitaireGameFour = SolitaireGameTestObject(
    game = SolitaireGame(
        deck = emptyList(),
        spadeFoundationStack = Card.getAllCards(CardSuite.SPADE).reversed(),
        cloverFoundationStack = Card.getAllCards(CardSuite.CLOVER),
        heartsFoundationStack = Card.getAllCards(CardSuite.HEARTS),
        diamondFoundationStack = Card.getAllCards(CardSuite.DIAMOND),
        firstTableStackState = TableStack.EMPTY,
        secondTableStackState = TableStack.EMPTY,
        thirdTableStackState = TableStack.EMPTY,
        fourthTableStackState = TableStack.EMPTY,
        fifthTableStackState = TableStack.EMPTY,
        sixthTableStackState = TableStack.EMPTY,
        seventhTableStackState = TableStack.EMPTY
    ),
    isValid = false,
    isWon = false,
    isDrawn = false,
    moveTests = emptyList()
)
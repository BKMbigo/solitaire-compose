package com.github.bkmbigo.solitaire.game.solitaire.fakes

import com.github.bkmbigo.solitaire.game.solitaire.SolitaireGame
import com.github.bkmbigo.solitaire.game.solitaire.SolitaireGameTestObject
import com.github.bkmbigo.solitaire.game.solitaire.TableStack
import com.github.bkmbigo.solitaire.models.core.Card
import com.github.bkmbigo.solitaire.models.core.CardSuite


/** Represents a game where the user has managed to arrange all cards on the foundation stacks. */
val SolitaireGameTwo = SolitaireGameTestObject(
    game = SolitaireGame(
        deck = emptyList(),
        spadeFoundationStack = Card.getAllCards(CardSuite.SPADE),
        cloverFoundationStack = Card.getAllCards(CardSuite.CLOVER),
        heartsFoundationStack = Card.getAllCards(CardSuite.HEARTS),
        diamondFoundationStack = Card.getAllCards(CardSuite.DIAMOND),
        firstTableStackState = TableStack.EMPTY,
        secondTableStackState = TableStack.EMPTY,
        thirdTableStackState = TableStack.EMPTY,
        fourthTableStackState = TableStack.EMPTY,
        fifthTableStackState = TableStack.EMPTY,
        sixthTableStackState = TableStack.EMPTY,
        seventhTableStackState = TableStack.EMPTY,
        eighthTableStackState = TableStack.EMPTY,
    ),
    isValid = true,
    isWon = true,
    isDrawn = false,
    moveTests = emptyList()
)
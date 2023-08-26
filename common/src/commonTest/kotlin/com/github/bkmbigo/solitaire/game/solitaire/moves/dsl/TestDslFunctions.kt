package com.github.bkmbigo.solitaire.game.solitaire.moves.dsl

import com.github.bkmbigo.solitaire.game.solitaire.SolitaireGame
import com.github.bkmbigo.solitaire.game.solitaire.moves.SolitaireGameMove
import com.github.bkmbigo.solitaire.game.solitaire.moves.SolitaireGameMoveTest
import com.github.bkmbigo.solitaire.models.core.Card

infix fun SolitaireGameMove.testIsValid(game: SolitaireGame): SolitaireGameMoveTest =
    SolitaireGameMoveTest(
        this,
        game,
        true
    )
infix fun SolitaireGameMove.testIsNotValid(game: SolitaireGame): SolitaireGameMoveTest =
    SolitaireGameMoveTest(
        this,
        game,
        false
    )

infix fun SolitaireGameMoveTest.withMessage(message: String) = this.copy(message = message)

infix fun SolitaireGameMoveTest.expectGame(game: SolitaireGame) = this.copy(expectedGame = game)


fun SolitaireGame.withRemoveFromDeck(card: Card): SolitaireGame =
    copy(
        deck = deck.toMutableList().apply {
            remove(card)
        }
    )

fun List<Card>.withAddedCard(card: Card): List<Card> =
    toMutableList().apply { add(card) }

fun List<Card>.withRemovedCard(card: Card): List<Card> =
    toMutableList().apply { remove(card) }

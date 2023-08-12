package com.github.bkmbigo.solitaire.game.solitaire.moves.dsl

import com.github.bkmbigo.solitaire.game.solitaire.SolitaireGame
import com.github.bkmbigo.solitaire.game.solitaire.moves.MoveSource
import com.github.bkmbigo.solitaire.game.solitaire.moves.SolitaireGameMove
import com.github.bkmbigo.solitaire.game.solitaire.moves.SolitaireGameMoveTest
import com.github.bkmbigo.solitaire.models.core.Card
import com.github.bkmbigo.solitaire.models.solitaire.utils.TableStackEntry

infix fun SolitaireGameMove.CardMove.test(actual: Boolean): SolitaireGameMoveTest =
    SolitaireGameMoveTest(
        this.game,
        this,
        actual
    )

infix fun SolitaireGameMoveTest.withMessage(message: String) = this.copy(message = message)

infix fun SolitaireGameMoveTest.expectGame(game: SolitaireGame) = this.copy(expectedGame = game)

infix fun SolitaireMoveGameCard.from(
    table: TableStackEntry
): SolitaireMoveGameCardFrom = SolitaireMoveGameCardFrom(
    game,
    cards,
    MoveSource.FromTable(table)
)

infix fun SolitaireMoveGameCardFrom.to(
    table: TableStackEntry
): SolitaireGameMove.CardMove =
    SolitaireGameMove.CardMove(
        game,
        cards,
        from,
        com.github.bkmbigo.solitaire.game.solitaire.moves.MoveDestination.ToTable(table)
    )


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
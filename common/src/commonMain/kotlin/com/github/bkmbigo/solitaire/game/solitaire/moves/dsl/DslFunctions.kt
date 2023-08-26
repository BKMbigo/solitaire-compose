package com.github.bkmbigo.solitaire.game.solitaire.moves.dsl

/*  This file might be moved to the test source set to reduce conflict and confusion in main source set.*/

import com.github.bkmbigo.solitaire.game.solitaire.SolitaireGame
import com.github.bkmbigo.solitaire.game.solitaire.moves.*
import com.github.bkmbigo.solitaire.models.core.Card
import com.github.bkmbigo.solitaire.models.solitaire.TableStackEntry

data class SolitaireMoveGameCard(
    val cards: List<Card>,
    val from: MoveSource
) {
    constructor(
        card: Card,
        from: MoveSource
    ) : this(
        listOf(card),
        from
    )
}

infix fun Card.move(
    from: MoveSource
): SolitaireMoveGameCard = SolitaireMoveGameCard(
    this,
    from
)

infix fun List<Card>.move(
    from: MoveSource
): SolitaireMoveGameCard = SolitaireMoveGameCard(
    this,
    from
)

infix fun Card.move(
    tableStackEntry: TableStackEntry
): SolitaireMoveGameCard = SolitaireMoveGameCard(
    this,
    MoveSource.FromTable(tableStackEntry)
)

infix fun List<Card>.move(
    tableStackEntry: TableStackEntry
): SolitaireMoveGameCard = SolitaireMoveGameCard(
    this,
    MoveSource.FromTable(tableStackEntry)
)

infix fun SolitaireMoveGameCard.to(
    to: MoveDestination
): SolitaireUserMove.CardMove = SolitaireUserMove.CardMove(
    cards,
    from,
    to
)

infix fun SolitaireMoveGameCard.to(
    tableStackEntry: TableStackEntry
): SolitaireUserMove.CardMove = SolitaireUserMove.CardMove(
    cards,
    from,
    MoveDestination.ToTable(tableStackEntry)
)

data class SolitaireMoveGameCardReturnFrom(
    val card: Card,
    val from: ReturnToDeckSource,
)

infix fun Card.returnToDeck(
    from: ReturnToDeckSource
): SolitaireMoveGameCardReturnFrom = SolitaireMoveGameCardReturnFrom(this, from)

infix fun SolitaireMoveGameCardReturnFrom.atIndex(
    index: Int
): SolitaireGameMove.ReturnToDeck = SolitaireGameMove.ReturnToDeck(
    card,
    from,
    index
)

infix fun SolitaireGameMove.isValid(game: SolitaireGame): Boolean = this.isValid(game)

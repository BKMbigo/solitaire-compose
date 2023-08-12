package com.github.bkmbigo.solitaire.game.solitaire.moves.dsl

/*  This file might be moved to the test source set to reduce conflict and confusion in main source set.*/

import com.github.bkmbigo.solitaire.game.solitaire.SolitaireGame
import com.github.bkmbigo.solitaire.game.solitaire.moves.MoveDestination
import com.github.bkmbigo.solitaire.game.solitaire.moves.MoveSource
import com.github.bkmbigo.solitaire.game.solitaire.moves.SolitaireGameMove
import com.github.bkmbigo.solitaire.models.core.Card

data class SolitaireMoveGameCard(
    val game: SolitaireGame,
    val cards: List<Card>
) {
    constructor(
        game: SolitaireGame,
        card: Card
    ) : this(
        game,
        listOf(card)
    )
}

data class SolitaireMoveGameCardFrom(
    val game: SolitaireGame,
    val cards: List<Card>,
    val from: MoveSource
)

infix fun SolitaireGame.move(
    card: Card
): SolitaireMoveGameCard = SolitaireMoveGameCard(
    this,
    card
)

infix fun SolitaireGame.move(
    cards: List<Card>
): SolitaireMoveGameCard = SolitaireMoveGameCard(
    this,
    cards
)

infix fun SolitaireMoveGameCard.from(
    from: MoveSource
): SolitaireMoveGameCardFrom = SolitaireMoveGameCardFrom(
    game,
    cards,
    from
)

infix fun SolitaireMoveGameCardFrom.to(
    to: MoveDestination
): SolitaireGameMove.CardMove = SolitaireGameMove.CardMove(
    game,
    cards,
    from,
    to
)
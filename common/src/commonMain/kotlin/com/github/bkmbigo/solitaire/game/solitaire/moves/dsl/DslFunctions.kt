package com.github.bkmbigo.solitaire.game.solitaire.moves.dsl

/*  This file might be moved to the test source set to reduce conflict and confusion in main source set.*/

import com.github.bkmbigo.solitaire.game.solitaire.SolitaireGame
import com.github.bkmbigo.solitaire.game.solitaire.moves.MoveDestination
import com.github.bkmbigo.solitaire.game.solitaire.moves.MoveSource
import com.github.bkmbigo.solitaire.game.solitaire.moves.SolitaireGameMove
import com.github.bkmbigo.solitaire.game.solitaire.moves.SolitaireUserMove
import com.github.bkmbigo.solitaire.models.core.Card
import com.github.bkmbigo.solitaire.models.solitaire.TableStackEntry
import kotlin.time.Duration

data class SolitaireMoveAtTime(
    val cards: List<Card>,
    val timeSinceStart: Duration
) {
    constructor(
        card: Card,
        timeSinceStart: Duration
    ) : this(
        cards = listOf(card),
        timeSinceStart = timeSinceStart
    )
}

data class SolitaireMoveGameCardAtTime(
    val cards: List<Card>,
    val timeSinceStart: Duration,
    val from: MoveSource
) {
    constructor(
        card: Card,
        timeSinceStart: Duration,
        from: MoveSource
    ) : this(
        cards = listOf(card),
        timeSinceStart = timeSinceStart,
        from = from
    )
}

infix fun Card.moveSolitaireAtTime(
    timeSinceStart: Duration
): SolitaireMoveAtTime = SolitaireMoveAtTime(
    card = this,
    timeSinceStart = timeSinceStart
)

infix fun List<Card>.moveSolitaireAtTime(
    timeSinceStart: Duration
): SolitaireMoveAtTime = SolitaireMoveAtTime(
    cards = this,
    timeSinceStart = timeSinceStart
)

infix fun Card.moveSolitaireInstantlyFrom(
    from: MoveSource
): SolitaireMoveGameCardAtTime = SolitaireMoveGameCardAtTime(
    card = this,
    timeSinceStart = Duration.ZERO,
    from = from
)

infix fun Card.moveSolitaireInstantlyFrom(
    tableStackEntry: TableStackEntry
): SolitaireMoveGameCardAtTime = SolitaireMoveGameCardAtTime(
    card = this,
    timeSinceStart = Duration.ZERO,
    from = MoveSource.FromTable(tableStackEntry)
)

infix fun List<Card>.moveSolitaireInstantlyFrom(
    from: MoveSource
): SolitaireMoveGameCardAtTime = SolitaireMoveGameCardAtTime(
    cards = this,
    timeSinceStart = Duration.ZERO,
    from = from
)

infix fun List<Card>.moveSolitaireInstantlyFrom(
    tableStackEntry: TableStackEntry
): SolitaireMoveGameCardAtTime = SolitaireMoveGameCardAtTime(
    cards = this,
    timeSinceStart = Duration.ZERO,
    from = MoveSource.FromTable(tableStackEntry)
)

infix fun SolitaireMoveAtTime.moveFrom(
    from: MoveSource
): SolitaireMoveGameCardAtTime = SolitaireMoveGameCardAtTime(
    timeSinceStart = timeSinceStart,
    cards = cards,
    from = from
)

infix fun SolitaireMoveAtTime.moveFrom(
    tableStackEntry: TableStackEntry
): SolitaireMoveGameCardAtTime = SolitaireMoveGameCardAtTime(
    timeSinceStart = timeSinceStart,
    cards = cards,
    from = MoveSource.FromTable(tableStackEntry)
)


infix fun SolitaireMoveGameCardAtTime.moveTo(
    to: MoveDestination
): SolitaireUserMove.CardMove = SolitaireUserMove.CardMove(
    timeSinceStart = timeSinceStart,
    cards = cards,
    from = from,
    to = to
)

infix fun SolitaireMoveGameCardAtTime.moveTo(
    tableStackEntry: TableStackEntry
): SolitaireUserMove.CardMove = SolitaireUserMove.CardMove(
    timeSinceStart = timeSinceStart,
    cards = cards,
    from = from,
    to = MoveDestination.ToTable(tableStackEntry)
)

infix fun SolitaireGameMove.isValid(game: SolitaireGame): Boolean = this.isValid(game)

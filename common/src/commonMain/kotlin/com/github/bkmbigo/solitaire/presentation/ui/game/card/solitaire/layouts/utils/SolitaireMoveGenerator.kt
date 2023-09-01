package com.github.bkmbigo.solitaire.presentation.ui.game.card.solitaire.layouts.utils

import androidx.compose.ui.unit.IntOffset
import com.github.bkmbigo.solitaire.game.solitaire.SolitaireGame
import com.github.bkmbigo.solitaire.game.solitaire.moves.MoveSource
import com.github.bkmbigo.solitaire.game.solitaire.moves.SolitaireUserMove
import com.github.bkmbigo.solitaire.game.solitaire.moves.dsl.move
import com.github.bkmbigo.solitaire.game.solitaire.moves.dsl.to
import com.github.bkmbigo.solitaire.models.core.Card
import com.github.bkmbigo.solitaire.models.solitaire.TableStackEntry

internal fun SolitairePlacer.processDeckMove(
    game: SolitaireGame,
    card: Card,
    deckPosition: Int,
    offsetX: Float,
    offsetY: Float,
    onMoveSuccess: (SolitaireUserMove) -> Unit,
    onMoveFailed: () -> Unit
) {

    val cardX = when (deckPosition) {
        0 -> null
        1 -> cardWidth + deckSeparation
        2 -> cardWidth + deckSeparation + cardOnDeckSeparation
        else -> cardWidth + deckSeparation + cardOnDeckSeparation * 2
    }

    if(cardX != null) {
        val destination = generateMoveDestination(
            dragStart = IntOffset(
                x = cardX,
                y = 0
            ),
            offsetX = offsetX,
            offsetY = offsetY
        )

        if (destination != null) {
            val move = card move MoveSource.FromDeck(game.deck.size - deckPosition) to destination
            if (move.isValid(game)) {
                onMoveSuccess(move)
            } else {
                onMoveFailed()
            }
        } else {
            onMoveFailed()
        }
    } else {
        onMoveFailed()
    }
}

internal fun SolitairePlacer.processFoundationMove(
    game: SolitaireGame,
    card: Card,
    offsetX: Float,
    offsetY: Float,
    onMoveSuccess: (SolitaireUserMove) -> Unit,
    onMoveFailed: () -> Unit
) {
    val dragStart = IntOffset(
        x = calculateFoundationXPosition(card.suite),
        y = 0
    )

    val destination = generateMoveDestination(
        dragStart = dragStart,
        offsetX = offsetX,
        offsetY = offsetY
    )

    if (destination == null) {
        onMoveFailed()
    } else {
        val move = card move MoveSource.FromFoundation to destination

        if (move.isValid(game)) {
            onMoveSuccess(move)
        } else {
            onMoveFailed()
        }
    }
}

internal fun SolitairePlacer.processTableStackMove(
    game: SolitaireGame,
    tableStackEntry: TableStackEntry,
    index: Int,
    offsetX: Float,
    offsetY: Float,
    onMoveSuccess: (SolitaireUserMove) -> Unit,
    onMoveFailed: () -> Unit
) {
    val tableTopLeft = calculateTableStackPosition(tableStackEntry)
    val cards = game.tableStack(tableStackEntry).revealedCards.filterIndexed { revealedIndex, card ->
        revealedIndex >= index
    }

    val dragStart = tableTopLeft.copy(
        y = tableTopLeft.y + game.tableStack(tableStackEntry).hiddenCards.size * individualTableStackYSeparation + index * individualTableStackYSeparation
    )

    val destination = generateMoveDestination(
        dragStart = dragStart,
        offsetX = offsetX,
        offsetY = offsetY
    )

    if (destination == null) {
        onMoveFailed()
    } else {
        val move = cards move MoveSource.FromTable(tableStackEntry) to destination

        if (move.isValid(game)) {
            onMoveSuccess(move)
        } else {
            onMoveFailed()
        }
    }
}

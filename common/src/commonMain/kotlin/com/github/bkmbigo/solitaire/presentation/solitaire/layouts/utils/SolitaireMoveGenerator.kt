package com.github.bkmbigo.solitaire.presentation.solitaire.layouts.utils

import androidx.compose.ui.unit.IntOffset
import com.github.bkmbigo.solitaire.game.solitaire.SolitaireGame
import com.github.bkmbigo.solitaire.game.solitaire.moves.MoveSource
import com.github.bkmbigo.solitaire.game.solitaire.moves.SolitaireUserMove
import com.github.bkmbigo.solitaire.game.solitaire.moves.dsl.moveSolitaireInstantlyFrom
import com.github.bkmbigo.solitaire.game.solitaire.moves.dsl.moveTo
import com.github.bkmbigo.solitaire.models.core.Card
import com.github.bkmbigo.solitaire.models.solitaire.TableStackEntry

/** Processes a move from the deck.
 * @param game The current playing game.
 * @param card The card being moved.
 *      <p> A Single card since a player can only move one card from the deck </p>
 * @param offsetX The horizontal drag offset of the card
 * @param offsetY The vertical drag offset of the card
 * @param onMoveSuccess Callback called when the move is valid
 * @param onMoveFailed Callback called when the move is invalid
 * @return Unit */
internal fun SolitairePlacer.processDeckMove(
    game: SolitaireGame,
    card: Card,
    offsetX: Float,
    offsetY: Float,
    onMoveSuccess: (SolitaireUserMove) -> Unit,
    onMoveFailed: () -> Unit
) {

    val cardX = when (game.deckPositions.size) {
        0 -> null   // No valid move can occur when deckPosition is 0
        1 -> cardWidth + deckSeparation
        2 -> cardWidth + deckSeparation + cardOnDeckSeparation
        else -> cardWidth + deckSeparation + cardOnDeckSeparation * 2
    }

    if (cardX != null) {
        val destination = generateMoveDestination(
            dragStart = IntOffset(
                x = cardX,
                y = 0
            ),
            offsetX = offsetX,
            offsetY = offsetY
        )

        if (destination != null) {
            val move = card moveSolitaireInstantlyFrom
                    MoveSource.FromDeck(game.deck.size - game.deckPositions.last()) moveTo destination
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

/** Processes a move from the deck.
 * @param game The current playing game.
 * @param card The card being moved.
 *      <p> A Single card since a player can only move a single card from the foundation </p>
 * @param offsetX The horizontal drag offset of the card
 * @param offsetY The vertical drag offset of the card
 * @param onMoveSuccess Callback called when the move is valid
 * @param onMoveFailed Callback called when the move is invalid
 * @return Unit */
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
        val move = card moveSolitaireInstantlyFrom MoveSource.FromFoundation moveTo destination

        if (move.isValid(game)) {
            onMoveSuccess(move)
        } else {
            onMoveFailed()
        }
    }
}

/** Processes a move from the deck.
 * @param game The current playing game.
 * @param tableStackEntry The [TableStackEntry] of the stack where the card is originating from.
 * @param index The position of the top-most card being moved
 * @param offsetX The horizontal drag offset of the card
 * @param offsetY The vertical drag offset of the card
 * @param onMoveSuccess Callback called when the move is valid
 * @param onMoveFailed Callback called when the move is invalid
 * @return Unit */
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
    val cards = game.tableStack(tableStackEntry).revealedCards.filterIndexed { revealedIndex, _ ->
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
        val move = cards moveSolitaireInstantlyFrom tableStackEntry moveTo destination

        if (move.isValid(game)) {
            onMoveSuccess(move)
        } else {
            onMoveFailed()
        }
    }
}

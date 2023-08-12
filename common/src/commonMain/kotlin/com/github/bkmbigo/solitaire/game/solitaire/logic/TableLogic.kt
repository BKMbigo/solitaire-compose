package com.github.bkmbigo.solitaire.game.solitaire.logic

import com.github.bkmbigo.solitaire.game.utils.isImmediatelyUpperTo
import com.github.bkmbigo.solitaire.models.core.Card
import com.github.bkmbigo.solitaire.models.core.CardRank
import com.github.bkmbigo.solitaire.game.solitaire.TableStack

/**
 * Checks whether a table stack list is fully valid.
 * <p>  - Checks that all cards are revealed
 *      - Checks that cards are alternating colors
 *      - Checks that card rank decrements from K -> A </p>
 *  @return true - if cards have alternating colors and their rank decrements from K -> A
 */
fun TableStack.isFullyValid(): Boolean = isFullyRevealed && isValidTableStack()

/**
 * Checks whether a card list is valid.
 * <p>  - Checks that cards are alternating colors
 *      - Checks that card rank decrements from K -> A </p>
 *  @return true - if cards have alternating colors and their rank decrements from K -> A
 */
fun List<Card>.isValidTableStack() = allIndexed { index, card ->
    if (index == 0) {
        true
    } else {
        val previousCard = get(index - 1)
        previousCard.color != card.color && previousCard.rank.isImmediatelyUpperTo(card.rank)
    }
}

fun TableStack.canFit(card: Card): Boolean {
    /* Fit only occurs when:
    *   Only a King card can fit into an empty Table Stack
    *   Otherwise check if last card:
    *       i) has varying color
    *       ii) has an immediate higher rank*/
    return if (isEmpty()) {
        card.rank == CardRank.KING
    } else {
        val lastCard = last()
        lastCard.color != card.color &&
                lastCard.rank.isImmediatelyUpperTo(card.rank)
    }
}



// TODO: make allIndexed pre-emptive (escape on first encounter of false)
fun <T> Collection<T>.allIndexed(transform: (index: Int, t: T) -> Boolean) =
    mapIndexed(transform).all { it }
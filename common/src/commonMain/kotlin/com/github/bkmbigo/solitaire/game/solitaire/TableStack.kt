package com.github.bkmbigo.solitaire.game.solitaire

import com.github.bkmbigo.solitaire.models.core.Card

// Not sure about the delegation
///     Does it add operations to construction of the class?????
data class TableStack(
    val hiddenCards: List<Card> = emptyList(),
    val revealedCards: List<Card> = emptyList()
): List<Card> by listOf(hiddenCards, revealedCards).flatten() {

    val isFullyRevealed
        get() = hiddenCards.isEmpty()

    val lastCard: Card?
        get() = revealedCards.lastOrNull()

    /** The bottom-most revealed card */
    val firstRevealedCard: Card?
        get() = revealedCards.firstOrNull()

    /** The top card in the hidden cards pile */
    val topHiddenCard: Card?
        get() = hiddenCards.lastOrNull()

    companion object {
        val EMPTY = TableStack()
    }

    fun withAddedCard(card: Card): TableStack =
        copy(
            revealedCards = revealedCards.toMutableList().apply {
                add(card)
            }
        )
    fun withAddedCards(cards: List<Card>): TableStack =
        copy(
            revealedCards = revealedCards.toMutableList().apply {
                addAll(cards)
            }
        )

    fun withRemovedCard(card: Card): TableStack =
        copy(
            revealedCards = revealedCards.toMutableList().apply {
                remove(card)
            }
        )

    fun withRemovedCards(cards: List<Card>): TableStack =
        copy(
            revealedCards = revealedCards.toMutableList().apply {
                removeAll(cards)
            }
        )

    fun withHideCard(): TableStack {
        val card = revealedCards.firstOrNull()

        return if (card != null) {
            TableStack(
                revealedCards = revealedCards.toMutableList().apply {
                    remove(card)
                },
                hiddenCards = hiddenCards.toMutableList().apply {
                    add(card)
                }
            )
        } else this
    }

    fun withRevealCard(): TableStack {
        val card = hiddenCards.lastOrNull()

        return if (card != null) {
            TableStack(
                revealedCards = revealedCards.toMutableList().apply {
                    add(card)
                },
                hiddenCards = hiddenCards.toMutableList().apply {
                    remove(card)
                }
            )
        } else this
    }

}

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

}
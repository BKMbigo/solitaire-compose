package com.github.bkmbigo.solitaire.game.solitaire.providers

import com.github.bkmbigo.solitaire.models.core.Card

fun <T> MutableList<T>.takeAndRemove(n: Int): List<T> {
    val list = take(n)
    removeAll(list)
    return list
}

fun takeFromEach(vararg lists: MutableList<Card>): List<Card> =
    lists
        .reversed()
        .map { it.takeAndRemove(1) }
        .flatten()

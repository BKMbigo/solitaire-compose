package com.github.bkmbigo.solitaire.game.solitaire.providers

import com.github.bkmbigo.solitaire.game.solitaire.SolitaireGame
import com.github.bkmbigo.solitaire.game.solitaire.TableStack
import com.github.bkmbigo.solitaire.models.core.Card

data object RandomSolitaireGameProvider: SolitaireGameProvider() {
    override suspend fun createGame(): SolitaireGame {
        val cards = Card.entries.shuffled().toMutableList()

        return SolitaireGame(
            deck = cards.takeAndRemove(24),
            spadeFoundationStack = emptyList(),
            cloverFoundationStack = emptyList(),
            heartsFoundationStack = emptyList(),
            diamondFoundationStack = emptyList(),
            firstTableStackState = TableStack(hiddenCards = cards.takeAndRemove(1)),
            secondTableStackState = TableStack(hiddenCards = cards.takeAndRemove(2)),
            thirdTableStackState = TableStack(hiddenCards = cards.takeAndRemove(3)),
            fourthTableStackState = TableStack(hiddenCards = cards.takeAndRemove(4)),
            fifthTableStackState = TableStack(hiddenCards = cards.takeAndRemove(5)),
            sixthTableStackState = TableStack(hiddenCards = cards.takeAndRemove(6)),
            seventhTableStackState = TableStack(hiddenCards = cards.takeAndRemove(7)),
        )
    }


}

package com.github.bkmbigo.solitaire.game.solitaire.providers

import com.github.bkmbigo.solitaire.game.solitaire.SolitaireGame
import com.github.bkmbigo.solitaire.game.solitaire.TableStack
import com.github.bkmbigo.solitaire.models.core.Card

/** A game provider that  */
data object VeryEasySolitaireGameProvider: SolitaireGameProvider() {
    override suspend fun createGame(): SolitaireGame {
        /* The game provided should be easy to win if the player places all cards on their respective foundation stacks in order.
        *  Therefore, the game will place the lower ranked cards (Aces, Twos and Threes) in easily accessible slots such as the deck and the lowest card on all table stacks. The highest card should contain highest ranking cards (Kings, Queens and Judges)
        *
        * Implementation:
        *        - The cards are divided into 8 lists according to ease of access to a player:
        *           1. (Contains 31 cards) - cards are readily available to the player at the start of the game. The  cards will be placed on the deck and on the top-most slots on the table.
        *           2. (Contains 6 cards)
        *           3. (Contains 5 cards)
        *           4. (Contains 4 cards)
        *           6. (Contains remaining 6 cards)
        *
        *       -  Shuffle the lists
        *
        *       - Place cards on respective areas:
        *           - The deck has 16 from list (1).
        *           - The first table slot has one card from list(1). (the 17th card).
        *           - The second table slot has one card from list(2) and one card from list(1)...
        * */


        val cardList = Card.entries.toMutableList()

        val list1 = cardList.takeAndRemove(31).shuffled().toMutableList()
        val list2 = cardList.takeAndRemove(6).shuffled().toMutableList()
        val list3 = cardList.takeAndRemove(5).shuffled().toMutableList()
        val list4 = cardList.takeAndRemove(4).shuffled().toMutableList()
        val list5 = cardList.takeAndRemove(6).shuffled().toMutableList()

        return SolitaireGame(
            deck = list1.takeAndRemove(24),
            spadeFoundationStack = emptyList(),
            cloverFoundationStack = emptyList(),
            heartsFoundationStack = emptyList(),
            diamondFoundationStack = emptyList(),
            firstTableStackState = TableStack(hiddenCards = takeFromEach(list1)),
            secondTableStackState = TableStack(hiddenCards = takeFromEach(list1, list2)),
            thirdTableStackState = TableStack(hiddenCards = takeFromEach(list1, list2, list3)),
            fourthTableStackState = TableStack(hiddenCards = takeFromEach(list1, list2, list3, list4)),
            fifthTableStackState = TableStack(hiddenCards = takeFromEach(list1, list2, list3, list4, list5)),
            sixthTableStackState = TableStack(hiddenCards = takeFromEach(list1, list2, list3, list4, list5, list5)),
            seventhTableStackState = TableStack(hiddenCards = takeFromEach(list1, list2, list3, list4, list5, list5, list5))
        )

    }
}

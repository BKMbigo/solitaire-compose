package com.github.bkmbigo.solitaireanimation.domain

import com.github.bkmbigo.solitaireanimation.models.Card

data class SolitaireGame(
    val stock: List<Card>, // Cards left on top

    val spadesFoundation: SolitaireFoundationStack,
    val cloversFoundation: SolitaireFoundationStack,
    val diamondsFoundation: SolitaireFoundationStack,
    val heartsFoundation: SolitaireFoundationStack,

    val firstStack: SolitaireTableStack,
    val secondStack: SolitaireTableStack,
    val thirdStack: SolitaireTableStack,
    val fourthStack: SolitaireTableStack,
    val fifthStack: SolitaireTableStack,
    val sixthStack: SolitaireTableStack,
    val seventhStack: SolitaireTableStack
) {


    companion object {
        private val stackSizes = listOf(1, 2, 3, 4, 5, 6, 7)
        fun generateNewGame(): SolitaireGame {
            /*We need to create a stack list of */
            val randomizedList = Card.values().toList().shuffled()
                .fold(List(8) { mutableListOf<Card>() }) { gameStock, card ->
                    if (gameStock[0].size < 1) {
                        gameStock[0].add(card)
                    } else if (gameStock[1].size < 2) {
                        gameStock[1].add(card)
                    } else if (gameStock[2].size < 3) {
                        gameStock[2].add(card)
                    } else if (gameStock[3].size < 4) {
                        gameStock[3].add(card)
                    } else if (gameStock[4].size < 5) {
                        gameStock[4].add(card)
                    } else if (gameStock[5].size < 6) {
                        gameStock[5].add(card)
                    } else if (gameStock[6].size < 7) {
                        gameStock[6].add(card)
                    } else {
                        gameStock[7].add(card)
                    }

                    gameStock
                }

            return SolitaireGame(
                stock = randomizedList[7],
                spadesFoundation = SolitaireFoundationStack(cards = listOf()),
                cloversFoundation = SolitaireFoundationStack(cards = listOf()),
                diamondsFoundation = SolitaireFoundationStack(cards = listOf()),
                heartsFoundation = SolitaireFoundationStack(cards = listOf()),
                firstStack = SolitaireTableStack(
                    cells = randomizedList[0],
                    flippedCells = 1
                ),
                secondStack = SolitaireTableStack(
                    cells = randomizedList[1],
                    flippedCells = 1
                ),
                thirdStack = SolitaireTableStack(
                    cells = randomizedList[2],
                    flippedCells = 1
                ),
                fourthStack = SolitaireTableStack(
                    cells = randomizedList[3],
                    flippedCells = 1
                ),
                fifthStack = SolitaireTableStack(
                    cells = randomizedList[4],
                    flippedCells = 1
                ),
                sixthStack = SolitaireTableStack(
                    cells = randomizedList[5],
                    flippedCells = 1
                ),
                seventhStack = SolitaireTableStack(
                    cells = randomizedList[6],
                    flippedCells = 1
                )
            )
        }
    }
}
package com.github.bkmbigo.solitaireanimation.domain

import com.github.bkmbigo.solitaireanimation.models.Card
import com.github.bkmbigo.solitaireanimation.presentation.screens.solitaire.FoundationStack
import com.github.bkmbigo.solitaireanimation.presentation.screens.solitaire.TableStack
import com.github.bkmbigo.solitaireanimation.presentation.screens.solitaire.state.FoundationStackState
import com.github.bkmbigo.solitaireanimation.presentation.screens.solitaire.state.TableStackState

data class SolitaireGame(
    val stock: List<Card>, // Cards left on top

    val foundationStacks: Map<FoundationStack, FoundationStackState> = FoundationStack.values().associateWith { FoundationStackState() },
    val tableStacks: Map<TableStack, TableStackState> = TableStack.values().associateWith { TableStackState() }
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
                foundationStacks = mapOf(),
                tableStacks = mapOf(
                    TableStack.FIRST to TableStackState(randomizedList[0]),
                    TableStack.SECOND to TableStackState(randomizedList[1]),
                    TableStack.THIRD to TableStackState(randomizedList[2]),
                    TableStack.FOURTH to TableStackState(randomizedList[3]),
                    TableStack.FIFTH to TableStackState(randomizedList[4]),
                    TableStack.SIXTH to TableStackState(randomizedList[5]),
                    TableStack.SEVENTH to TableStackState(randomizedList[6])
                ),

            )
        }
    }
}
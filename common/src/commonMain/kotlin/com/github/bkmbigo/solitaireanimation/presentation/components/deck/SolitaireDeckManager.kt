package com.github.bkmbigo.solitaireanimation.presentation.components.deck

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.github.bkmbigo.solitaireanimation.models.Card
import com.github.bkmbigo.solitaireanimation.presentation.locals.cardtheme.LocalCardTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface SolitaireDeckManager {
    val deckState: StateFlow<SolitaireDeckState>
    fun deal()
}

@Composable
fun rememberSolitaireDeckManager(stock: List<Card>): SolitaireDeckManager {
    val cardTheme = LocalCardTheme.current

    val deckState = remember { MutableStateFlow(SolitaireDeckState()) }

    /** Current index of current remaining Cards in stock*/
    var currentRemainingIndex by remember { mutableStateOf(stock.size) }

    LaunchedEffect(stock, currentRemainingIndex) {
        if (currentRemainingIndex > stock.size) {
            currentRemainingIndex = stock.size
        }

        val openStack = stock.subList(0, stock.size - currentRemainingIndex)
        val remainingStack = stock.subList(stock.size - currentRemainingIndex, stock.size)

        deckState.value = SolitaireDeckState(
            openStack = openStack,
            uncoveredStack = remainingStack
        )
    }

    return remember {
        object : SolitaireDeckManager {
            override val deckState: StateFlow<SolitaireDeckState> = deckState

            override fun deal() {
                if (currentRemainingIndex == 0) {
                    currentRemainingIndex = stock.size
                } else {
                    currentRemainingIndex -= cardTheme.SolitaireGameOptions.cardsUnveiledPerDeal
                }
            }

        }
    }
}
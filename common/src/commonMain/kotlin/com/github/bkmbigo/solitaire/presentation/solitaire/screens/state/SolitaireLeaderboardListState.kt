package com.github.bkmbigo.solitaire.presentation.solitaire.screens.state

import com.github.bkmbigo.solitaire.data.SolitaireScore

sealed class SolitaireLeaderboardListState {

    data class Success(val list: List<SolitaireScore>) : SolitaireLeaderboardListState()

    data object Loading : SolitaireLeaderboardListState()

    data object Error : SolitaireLeaderboardListState()

}

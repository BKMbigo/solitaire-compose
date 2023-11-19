package com.github.bkmbigo.solitaire.presentation.solitaire.screens.state

import com.github.bkmbigo.solitaire.utils.Platform

sealed class SolitaireLeaderboardDialogState(
    open val leaderboard: SolitaireLeaderboardListState,
) {

    data class LeaderboardOnly(
        override val leaderboard: SolitaireLeaderboardListState
    ) : SolitaireLeaderboardDialogState(leaderboard)

    data class LeaderboardAndScore(
        override val leaderboard: SolitaireLeaderboardListState,
        val isEntryAllowed: Boolean = true,
        val isEntryLoading: Boolean = false,
        val score: Int,
        val platform: Platform
    ) : SolitaireLeaderboardDialogState(leaderboard)

    fun copyWithLeaderboard(
        leaderboard: SolitaireLeaderboardListState = this.leaderboard
    ) = when (this) {
        is LeaderboardAndScore -> this.copy(
            leaderboard = leaderboard
        )

        is LeaderboardOnly -> this.copy(
            leaderboard = leaderboard
        )
    }
}

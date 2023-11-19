package com.github.bkmbigo.solitaire.presentation.solitaire.components.dialog.leaderboard

import com.github.bkmbigo.solitaire.utils.Platform

sealed class SolitaireLeaderboardDialogAction {

    /** Dismiss the dialog */
    data object DismissDialog : SolitaireLeaderboardDialogAction()

    /** Filter the leaderbooard according to a custom [leaderboardName] */
    data class FilterByLeaderboard(
        val leaderboardName: String = ""
    ) : SolitaireLeaderboardDialogAction()

    /** Submit your score to the leaderboard */
    data class SubmitLeaderboardScore(
        val playerName: String,
        val customLeaderboardName: String?,
        val platform: Platform
    ) : SolitaireLeaderboardDialogAction()

    /** Go to the next page */ // TODO: Implement paging
    data object GoToNextPage : SolitaireLeaderboardDialogAction()
}

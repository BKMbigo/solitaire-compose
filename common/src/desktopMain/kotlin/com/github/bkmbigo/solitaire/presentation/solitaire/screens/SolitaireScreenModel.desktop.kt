package com.github.bkmbigo.solitaire.presentation.solitaire.screens

import cafe.adriel.voyager.core.model.ScreenModel
import com.github.bkmbigo.solitaire.data.FirebaseScoreRepository
import com.github.bkmbigo.solitaire.game.solitaire.configuration.SolitaireCardsPerDeal
import com.github.bkmbigo.solitaire.game.solitaire.configuration.SolitaireGameConfiguration
import com.github.bkmbigo.solitaire.game.solitaire.moves.SolitaireUserMove
import com.github.bkmbigo.solitaire.game.solitaire.providers.SolitaireGameProvider
import com.github.bkmbigo.solitaire.utils.Platform
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

actual class SolitaireScreenModel(
    private val coroutineScope: CoroutineScope,
    private val firebaseScoreRepository: FirebaseScoreRepository
) : ScreenModel, AbstractSolitaireScreenModel(firebaseScoreRepository) {
    override val scope: CoroutineScope = coroutineScope

    actual fun createGame(provider: SolitaireGameProvider, cardsPerDeal: SolitaireCardsPerDeal) {
        coroutineScope.launch {
            performCreateGame(provider, SolitaireGameConfiguration(cardsPerDeal))
        }
    }

    actual fun deal() = performDeal()

    actual fun play(move: SolitaireUserMove.CardMove) = performPlay(move)

    actual fun undo() = performUndo()

    actual fun redo() = performRedo()

    actual fun offerHint() = performHint()


    actual fun retrieveCustomLeaderboard(leaderboard: String?) {
        coroutineScope.launch {
            getLatestCustomLeaderboard(leaderboard)
        }
    }

    actual fun showLeaderboardOnlyDialog() {
        coroutineScope.launch {
            showLeaderboardDialogBeforeWin()
        }
    }

    actual fun showLeaderboardDialog(platform: Platform) {
        coroutineScope.launch {
            showLeaderboardDialogAfterWin(platform)
        }
    }

    actual fun hideLeaderboardDialog() {
        performHideLeaderboardDialog()
    }


    actual fun submitLeaderboardScore(playerName: String, leaderboard: String?, platform: Platform) {
        coroutineScope.launch {
            submitScore(
                playerName = playerName,
                leaderboard = leaderboard,
                platform = platform
            )
        }
    }


}

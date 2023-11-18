package com.github.bkmbigo.solitaire.data

import com.github.bkmbigo.solitaire.utils.Platform
import kotlinx.serialization.Serializable

/** A score in Klondike Leaderboard */
@Serializable
data class SolitaireScore(
    /** The name the player has chosen to save their score */
    val playerName: String = "Anonymous",
    /** The score achieved by the player */
    val score: Int,
    /** The custom leaderboard the player would like the score to be saved in */
    val leaderboard: String? = null,
    /** The platform used by the player */
    val platform: Platform
)

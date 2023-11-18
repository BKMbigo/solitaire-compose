package com.github.bkmbigo.solitaire.data

/** The repository mainly used for storing scores in a firebase project */
interface FirebaseScoreRepository {

    /** Add a new [SolitaireScore] to firebase
     * @param score The score attained. */
    suspend fun addKlondikeScore(score: SolitaireScore)

    /** Gets the top scores at a custom leaderboard
     * @param leaderboard  The name of the leaderboard you want to query
     * @return List of top [SolitaireScore] ordered by their scores. */
    suspend fun getLeaderboard(leaderboard: String): List<SolitaireScore>

    /** Gets the top-most leaderboard across all platforms and leaderboards
     * @return List of top [SolitaireScore] by their scores. */
    suspend fun getTopLeaderboard(): List<SolitaireScore>
}

package com.github.bkmbigo.solitaire.data

expect class FirebaseScoreRepositoryImpl : FirebaseScoreRepository {

    override suspend fun addKlondikeScore(score: SolitaireScore)

    override suspend fun getLeaderboard(leaderboard: String): List<SolitaireScore>

    override suspend fun getTopLeaderboard(): List<SolitaireScore>
}

internal const val klondikePath = "klondikeScore/"

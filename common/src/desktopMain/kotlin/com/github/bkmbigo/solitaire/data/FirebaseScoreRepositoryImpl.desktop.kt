package com.github.bkmbigo.solitaire.data

import com.github.bkmbigo.solitaire.data.ktor.SolitaireFirestoreApi
import com.github.bkmbigo.solitaire.data.ktor.dto.FirestoreFilterResultDto
import com.github.bkmbigo.solitaire.data.ktor.dto.FirestoreListResponseDto
import com.github.bkmbigo.solitaire.data.ktor.mappers.toSolitaireScore
import com.github.bkmbigo.solitaire.data.ktor.utils.safeApiCall
import com.github.bkmbigo.solitaire.utils.Logger
import io.ktor.client.call.*

actual class FirebaseScoreRepositoryImpl(
    private val firestoreApi: SolitaireFirestoreApi
) : FirebaseScoreRepository {

    actual override suspend fun addKlondikeScore(score: SolitaireScore) {
        firestoreApi.addRecord(klondikePath, score)
    }

    actual override suspend fun getLeaderboard(leaderboard: String): List<SolitaireScore> =
        safeApiCall {
            firestoreApi
                .getRecordByEqualFilter(klondikePath, "leaderboard", leaderboard)
                .body<List<FirestoreFilterResultDto>>()
                .mapNotNull { it.document.fields.toSolitaireScore() }
        }.applyIfError { errorCode, errorMessage ->
            Logger.LogInfo("Error while obtaining Leaderboard", "Error is $errorCode: $errorMessage")
        }.dataOrElse {
            emptyList()
        }


    actual override suspend fun getTopLeaderboard(): List<SolitaireScore> =
        safeApiCall {
            firestoreApi
                .getAllRecords(klondikePath)
                .body<FirestoreListResponseDto>()
                .documents
                .mapNotNull { it.fields.toSolitaireScore() }
        }.applyIfError { errorCode, errorMessage ->
            Logger.LogInfo("Error while obtaining Top Leaderboard", "Error is $errorCode: $errorMessage")
        }.dataOrElse {
            emptyList()
        }
}

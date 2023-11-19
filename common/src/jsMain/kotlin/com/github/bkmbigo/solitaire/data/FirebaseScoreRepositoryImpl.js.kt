package com.github.bkmbigo.solitaire.data

import com.github.bkmbigo.solitaire.utils.Logger
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseOptions
import dev.gitlive.firebase.firestore.*
import dev.gitlive.firebase.initialize

actual class FirebaseScoreRepositoryImpl(
    private val firebaseFirestore: FirebaseFirestore
) : FirebaseScoreRepository {

    actual override suspend fun addKlondikeScore(score: SolitaireScore) {
        try {
            firebaseFirestore.collection(klondikePath).add(score)
        } catch (e: Exception) {
            Logger.LogWarning("FirebaseRepository", "Error uploading scores")
        }
    }

    actual override suspend fun getLeaderboard(leaderboard: String): List<SolitaireScore> =
        try {
            firebaseFirestore
                .collection(klondikePath)
                .where("leaderboard", leaderboard)
                .orderBy("score", Direction.DESCENDING)
                .get()
                .documents
                .map { it.data<SolitaireScore>() }
        } catch (e: Exception) {
            Logger.LogInfo("Firebase", "exception encountered: ${e.message}")
            emptyList()
        }

    actual override suspend fun getTopLeaderboard(): List<SolitaireScore> =
        try {
            firebaseFirestore
                .collection(klondikePath)
                .orderBy("score", Direction.DESCENDING)
                .get()
                .documents
                .map { it.data<SolitaireScore>() }
        } catch (e: Exception) {
            Logger.LogInfo("Firebase", "exception encountered: ${e.message}")
            emptyList()
        }

    companion object {
        /** Creates an instance of a [FirebaseFirestore]. Can return null in case of an error */
        fun initializeFirebase(): FirebaseScoreRepositoryImpl? {
            val options = FirebaseOptions(
                applicationId = FirebaseCredentials.appId,
                apiKey = FirebaseCredentials.apiKey,
                projectId = FirebaseCredentials.projectId
            )
            val app = try {
                Firebase.initialize(null, options)
            } catch (e: Exception) {
                null
            }

            return if (app != null) {
                FirebaseScoreRepositoryImpl(Firebase.firestore(app))
            } else null
        }
    }

}

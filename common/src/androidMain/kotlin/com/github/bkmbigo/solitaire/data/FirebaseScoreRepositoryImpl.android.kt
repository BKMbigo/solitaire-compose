package com.github.bkmbigo.solitaire.data

import android.content.Context
import com.github.bkmbigo.solitaire.utils.Logger
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

actual class FirebaseScoreRepositoryImpl(
    private val firebaseFirestore: FirebaseFirestore
) : FirebaseScoreRepository {

    actual override suspend fun addKlondikeScore(score: SolitaireScore) {
        try {
            firebaseFirestore.collection(klondikePath).add(score).await()
        } catch (e: Exception) {
            Logger.LogInfo("Firebase", "exception encountered: ${e.message}")
        }
    }

    actual override suspend fun getLeaderboard(leaderboard: String): List<SolitaireScore> =
        try {
            firebaseFirestore
                .collection(klondikePath)
                .whereEqualTo("leaderboard", leaderboard)
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject<SolitaireScore>() }
        } catch (e: Exception) {
            Logger.LogInfo("Firebase", "exception encountered: ${e.message}")
            emptyList()
        }


    actual override suspend fun getTopLeaderboard(): List<SolitaireScore> =
        try {
            firebaseFirestore
                .collection(klondikePath)
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject<SolitaireScore>() }
        } catch (e: Exception) {
            Logger.LogInfo("Firebase", "exception encountered: ${e.message}")
            emptyList()
        }

    companion object {
        /** Creates an instance of a [FirebaseFirestore]. Can return null in case of an error */
        fun initializeFirebase(context: Context): FirebaseScoreRepositoryImpl? {
            val firebaseApp = try {
                FirebaseApp.initializeApp(context)
            } catch (e: Exception) {
                null
            }

            return if (firebaseApp != null) {
                FirebaseScoreRepositoryImpl(FirebaseFirestore.getInstance(firebaseApp))
            } else null
        }
    }

}

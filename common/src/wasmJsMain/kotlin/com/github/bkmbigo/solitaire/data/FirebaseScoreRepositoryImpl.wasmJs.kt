package com.github.bkmbigo.solitaire.data

import com.github.bkmbigo.solitaire.utils.Logger
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseApp
import dev.gitlive.firebase.FirebaseOptions
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.firestore.where
import dev.gitlive.firebase.initialize
import externals.catchJsExceptions

actual class FirebaseScoreRepositoryImpl(
    private val firebaseFirestore: FirebaseFirestore
) : FirebaseScoreRepository {

    actual override suspend fun addKlondikeScore(score: SolitaireScore) {
        val scoreJs = createSolitaireScoreJsObject(
            playerName = score.playerName,
            score = score.score,
            leaderboard = score.leaderboard,
            platformName = score.platform.name
        )

        firebaseFirestore.collection(klondikePath).add(scoreJs)
    }

    actual override suspend fun getLeaderboard(leaderboard: String): List<SolitaireScore> =
        try {
            firebaseFirestore
                .collection(klondikePath)
                .where("leaderboard", leaderboard.toJsString())
                .get()
                .documents
                .mapNotNull { it.data()?.let { it1 -> getSolitaireScoreFromJsObject(it1) } }
        } catch (e: Exception) {
            Logger.LogInfo("Firebase", "exception encountered: ${e.message}")
            emptyList()
        }


    actual override suspend fun getTopLeaderboard(): List<SolitaireScore> =
        try {
            firebaseFirestore
                .collection(klondikePath)
                .get()
                .documents
                .mapNotNull { it.data()?.let { it1 -> getSolitaireScoreFromJsObject(it1) } }
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
                val js = catchJsExceptions { Firebase.initialize(options).js }
                FirebaseApp(js)
            } catch (e: Exception) {
                null
            }

            return if (app != null) {
                FirebaseScoreRepositoryImpl(Firebase.firestore(app))
            } else null
        }
    }

}

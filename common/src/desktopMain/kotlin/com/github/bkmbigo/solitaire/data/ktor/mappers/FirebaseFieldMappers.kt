package com.github.bkmbigo.solitaire.data.ktor.mappers

import com.github.bkmbigo.solitaire.data.SolitaireScore
import com.github.bkmbigo.solitaire.data.ktor.dto.FirestoreFieldMap
import com.github.bkmbigo.solitaire.utils.Platform

/* Firebase Constants */
private const val stringValue = "stringValue"
private const val integerValue = "integerValue"
private const val nullValue = "nullValue"
private const val nullConstant = "NULL_VALUE"

/* Field Constants */
private const val playerNameField = "playerName"
private const val scoreField = "score"
private const val leaderboardField = "leaderboard"
private const val platformField = "platform"

/** Converts a [SolitaireScore] to a favorable [FirestoreFieldMap] to be used in the API call */
internal fun SolitaireScore.toFirebaseFieldDto(): FirestoreFieldMap {

    val playerNameDto = mapOf(
        stringValue to playerName
    )

    val scoreDto = mapOf(
        integerValue to score.toString()
    )

    /*
    * Converts a leaderboard? object to either a nullValue or a stringValue
    *  */
    val leaderboardDto = mapOf(
        if (leaderboard != null) {
            stringValue to leaderboard
        } else {
            nullValue to nullConstant
        }
    )

    val platformDto = mapOf(
        stringValue to platform.toString()
    )

    return mapOf(
        playerNameField to playerNameDto,
        scoreField to scoreDto,
        leaderboardField to leaderboardDto,
        platformField to platformDto
    )
}

/** Converts a [FirestoreFieldMap] obtained from an API call to a [SolitaireScore] */
internal fun FirestoreFieldMap.toSolitaireScore(): SolitaireScore? {

    val score = get(scoreField)?.get(integerValue)?.toIntOrNull()

    val playerName = get(playerNameField)?.get(stringValue) ?: "Unknown"

    val platform = get(platformField)?.get(stringValue)?.let {
        try {
            Platform.valueOf(it)
        } catch (e: IllegalArgumentException) {
            null
        }
    }

    val leaderboard = get(leaderboardField)?.get(stringValue)

    return if (score != null && platform != null) {
        SolitaireScore(
            playerName = playerName,
            score = score,
            leaderboard = leaderboard,
            platform = platform
        )
    } else null
}

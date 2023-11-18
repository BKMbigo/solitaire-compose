package com.github.bkmbigo.solitaire.data

import com.github.bkmbigo.solitaire.utils.Platform

@JsFun("(_playerName, _score, _leaderboard, platformName) => ( { playerName: _playerName, score: _score, leaderboard: _leaderboard, platform: platformName })")
/* Workaround for lacking kotlin.js.json */
/** Generates a json object representing [SolitaireScore] */
external fun createSolitaireScoreJsObject(
    playerName: String,
    score: Int,
    leaderboard: String?,
    platformName: String
): JsAny

/* Workaround for lacking kotlin.js.json */
/** Generates a [SolitaireScore] from a json object([data]) */
fun getSolitaireScoreFromJsObject(
    data: JsAny
): SolitaireScore = SolitaireScore(
    playerName = getPlayerNameFromObject(data),
    score = getScoreFromObject(data),
    leaderboard = getLeaderboardFromObject(data),
    platform = Platform.valueOf(getPlatformNameFromObject(data))
)

@JsFun("function getPlayerNameFromObject(data) { return data.playerName }")
private external fun getPlayerNameFromObject(
    data: JsAny
): String

@JsFun("function getScore(data) { return data.score }")
private external fun getScoreFromObject(
    data: JsAny
): Int

@JsFun("function getPlatformNameFromObject(data) { return data.platform }")
private external fun getPlatformNameFromObject(
    data: JsAny
): String

@JsFun("function getLeaderboardFromObject(data) { return data.leaderboard }")
private external fun getLeaderboardFromObject(
    data: JsAny
): String?


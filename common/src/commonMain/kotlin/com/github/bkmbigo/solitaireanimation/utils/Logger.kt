package com.github.bkmbigo.solitaireanimation.utils

expect object Logger {
    fun LogInfo(
        tag: String,
        message: String
    )

    fun LogWarning(
        tag: String,
        message: String
    )
}
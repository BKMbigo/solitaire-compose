package com.github.bkmbigo.solitaire.utils

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
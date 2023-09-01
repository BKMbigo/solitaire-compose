package com.github.bkmbigo.solitaire.utils

actual object Logger {
    actual fun LogInfo(tag: String, message: String) =
        console.log("${tag}: $message")

    actual fun LogWarning(tag: String, message: String) =
        console.warn("${tag}: $message")
}
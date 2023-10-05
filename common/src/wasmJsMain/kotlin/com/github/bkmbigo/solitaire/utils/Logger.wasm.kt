package com.github.bkmbigo.solitaire.utils

actual object Logger {
    actual fun LogInfo(tag: String, message: String) = print("Info: $tag: $message")

    actual fun LogWarning(tag: String, message: String) = print("Warning: $tag: $message")
}
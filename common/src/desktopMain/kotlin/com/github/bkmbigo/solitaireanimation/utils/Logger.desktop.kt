package com.github.bkmbigo.solitaireanimation.utils

import java.util.logging.Level
import java.util.logging.Logger

actual object Logger {
    actual fun LogInfo(tag: String, message: String) {
        Logger.getAnonymousLogger().log(Level.INFO, "${tag}: $message")
    }

    actual fun LogWarning(tag: String, message: String) {
        Logger.getAnonymousLogger().log(Level.WARNING, "${tag}: $message")
    }
}
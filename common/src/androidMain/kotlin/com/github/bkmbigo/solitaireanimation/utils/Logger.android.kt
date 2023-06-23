package com.github.bkmbigo.solitaireanimation.utils

import android.util.Log

actual object Logger {
    actual fun LogInfo(tag: String, message: String) {
        Log.i(tag, message)
    }

    actual fun LogWarning(tag: String, message: String) {
        Log.w(tag, message)
    }
}
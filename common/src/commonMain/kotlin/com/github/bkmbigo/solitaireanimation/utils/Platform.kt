package com.github.bkmbigo.solitaireanimation.utils

enum class Platform {
    ANDROID,
    DESKTOP,
    JS,
    WASM
}

expect val currentPlatform: Platform
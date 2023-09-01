package com.github.bkmbigo.solitaire.utils

enum class Platform {
    ANDROID,
    DESKTOP,
    JS,
    WASM
}

expect val currentPlatform: Platform
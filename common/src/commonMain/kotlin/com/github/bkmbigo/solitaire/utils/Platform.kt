package com.github.bkmbigo.solitaire.utils

enum class Platform {
    ANDROID,
    DESKTOP,
    IDE,
    JS,
    WASM
}

expect val currentPlatform: Platform

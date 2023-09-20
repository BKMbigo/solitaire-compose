package com.github.bkmbigo.solitaire.utils

enum class Platform {
    ANDROID,
    DESKTOP,
    INTELLIJ,
    JS,
    VSCODE,
    WASM
}

expect val currentPlatform: Platform

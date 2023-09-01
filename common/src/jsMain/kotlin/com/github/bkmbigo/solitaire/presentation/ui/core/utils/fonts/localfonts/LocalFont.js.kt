package com.github.bkmbigo.solitaire.presentation.ui.core.utils.fonts.localfonts

import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

actual class LocalFont(
    val identity: String,
    val filename: String,
    actual val weight: FontWeight = FontWeight.Normal,
    actual val style: FontStyle = FontStyle.Normal
)

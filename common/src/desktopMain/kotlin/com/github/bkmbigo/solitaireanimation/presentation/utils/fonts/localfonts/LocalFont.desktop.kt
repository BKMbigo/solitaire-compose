package com.github.bkmbigo.solitaireanimation.presentation.utils.fonts.localfonts

import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

actual class LocalFont(
    val identifier: String,
    val filename: String,
    actual val weight: FontWeight,
    actual val style: FontStyle
)
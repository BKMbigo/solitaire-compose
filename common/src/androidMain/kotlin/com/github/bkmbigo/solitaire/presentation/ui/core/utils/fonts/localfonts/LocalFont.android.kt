package com.github.bkmbigo.solitaire.presentation.ui.core.utils.fonts.localfonts

import androidx.annotation.FontRes
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

actual class LocalFont(
    @FontRes val resource: Int,
    actual val weight: FontWeight = FontWeight.Normal,
    actual val style: FontStyle = FontStyle.Normal
)

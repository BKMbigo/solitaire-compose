package com.github.bkmbigo.solitaireanimation.presentation.utils.fonts.localfonts

import androidx.annotation.FontRes
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

actual class LocalFont(
    @FontRes val resource: Int,
    actual val weight: FontWeight,
    actual val style: FontStyle
)
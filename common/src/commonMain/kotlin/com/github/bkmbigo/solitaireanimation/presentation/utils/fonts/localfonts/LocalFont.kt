package com.github.bkmbigo.solitaireanimation.presentation.utils.fonts.localfonts

import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

expect class LocalFont {
    val weight: FontWeight
    val style: FontStyle
}
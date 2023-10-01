package com.github.bkmbigo.solitaire.presentation.core.utils.fonts.localfonts

import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

expect class LocalFont {
    val weight: FontWeight
    val style: FontStyle
}

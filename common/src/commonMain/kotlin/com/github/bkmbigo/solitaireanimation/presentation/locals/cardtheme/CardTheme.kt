package com.github.bkmbigo.solitaireanimation.presentation.locals.cardtheme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily

interface CardTheme {
    val isDark: Boolean

    val useMiniCards: Boolean

    val cardFrontBackground: Color
    val cardBackBackground: Color
    val gameBackground: Color

    val miniPassportFont: FontFamily

    val appFont: FontFamily

}
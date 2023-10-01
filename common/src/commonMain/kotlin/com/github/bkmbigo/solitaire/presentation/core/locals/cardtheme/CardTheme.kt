package com.github.bkmbigo.solitaire.presentation.core.locals.cardtheme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import com.github.bkmbigo.solitaire.utils.Platform

interface CardTheme {
    val platform: Platform

    val generalResourcePath: String

    val isDark: Boolean
    val useMiniCards: Boolean

    val cardFrontBackground: Color
    val cardBackBackground: Color
    val cardSelectedColor: Color
    val gameBackground: Color

    val miniCardFont: FontFamily
    val appFont: FontFamily

    val cardSize: DpSize
    val verticalCardStackSeparation: Dp
    val horizontalCardStackSeparation: Dp
}

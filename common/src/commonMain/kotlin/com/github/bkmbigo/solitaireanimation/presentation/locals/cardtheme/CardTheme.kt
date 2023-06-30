package com.github.bkmbigo.solitaireanimation.presentation.locals.cardtheme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize

interface CardTheme {
    val isDark: Boolean
    val useMiniCards: Boolean

    val useAnimations: Boolean

    val cardFrontBackground: Color
    val cardBackBackground: Color
    val cardSelectedColor: Color
    val gameBackground: Color

    val miniCardFont: FontFamily
    val appFont: FontFamily

    val cardSize: DpSize
    val verticalCardStackSeparation: Dp
    val horizontalCardStackSeparation: Dp

    val SolitaireGameOptions: SolitaireGameOptions
}
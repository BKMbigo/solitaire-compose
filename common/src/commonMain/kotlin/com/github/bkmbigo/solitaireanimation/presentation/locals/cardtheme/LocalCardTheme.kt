package com.github.bkmbigo.solitaireanimation.presentation.locals.cardtheme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

val LocalCardTheme = staticCompositionLocalOf<CardTheme> { DefaultCardTheme }

val DefaultCardTheme = object: CardTheme {
    override val isDark: Boolean = false
    override val useMiniCards: Boolean = false

    override val cardFrontBackground: Color = Color.White
    override val cardBackBackground: Color = Color(0xFF552200)
    override val gameBackground: Color = Color(0xFF15360F)

    override val miniCardFont: FontFamily = FontFamily.Monospace
    override val appFont: FontFamily = FontFamily.SansSerif

    override val cardSize: DpSize = DpSize((121.5).dp, (77.5).dp)
    override val verticalCardStackSeparation: Dp = 20.dp
    override val horizontalCardStackSeparation: Dp = 20.dp
}
@Composable
fun rememberCardTheme(
    isDark: Boolean,
    andikaFont: FontFamily?,
    lobsterTwoFamily: FontFamily?
) = remember(isDark, andikaFont, lobsterTwoFamily) {
    object: CardTheme {
        override val isDark: Boolean = isDark
        override val useMiniCards: Boolean = false
        override val cardFrontBackground: Color = Color.White
        override val cardBackBackground: Color = Color(0xFF552200)
        override val gameBackground: Color = Color(0xFF15360F)
        override val miniCardFont: FontFamily = lobsterTwoFamily ?: FontFamily.Monospace
        override val appFont: FontFamily = andikaFont ?: FontFamily.SansSerif
        override val cardSize: DpSize = DpSize((121.5).dp, (77.5).dp)
        override val verticalCardStackSeparation: Dp = 20.dp
        override val horizontalCardStackSeparation: Dp = 20.dp
    }
}
package com.github.bkmbigo.solitaireanimation.presentation.locals.cardtheme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily

val LocalCardTheme = staticCompositionLocalOf<CardTheme> { DefaultCardTheme }

private val DefaultCardTheme = object: CardTheme {
    override val isDark: Boolean = false
    override val useMiniCards: Boolean = false
    override val cardFrontBackground: Color = Color.White
    override val cardBackBackground: Color = Color(0xFF552200)
    override val gameBackground: Color = Color(0xFF15360F)
    override val miniPassportFont: FontFamily = FontFamily.Monospace
    override val appFont: FontFamily = FontFamily.SansSerif
}
package com.github.bkmbigo.solitaireanimation.presentation.theme.locals

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily

val LocalCardTheme = staticCompositionLocalOf<CardThemeProperty> { CardThemeProperty.DefaultCardTheme }

interface CardThemeProperty {
    val isDark: Boolean

    val useMiniCards: Boolean

    val cardFrontBackground: Color
    val cardBackBackground: Color
    val gameBackground: Color

    val miniPassportFont: FontFamily

    val appFont: FontFamily

    companion object {
        val DefaultCardTheme = object: CardThemeProperty {
            override val isDark: Boolean = false
            override val useMiniCards: Boolean = false
            override val cardFrontBackground: Color = Color.White
            override val cardBackBackground: Color = Color(0xFF552200)
            override val gameBackground: Color = Color(0xFF15360F)
            override val miniPassportFont: FontFamily = FontFamily.Monospace
            override val appFont: FontFamily = FontFamily.SansSerif
        }
    }
}
package com.github.bkmbigo.solitaireanimation.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontFamily
import com.github.bkmbigo.solitaireanimation.presentation.locals.cardtheme.DefaultCardTheme
import com.github.bkmbigo.solitaireanimation.presentation.locals.cardtheme.LocalCardTheme
import com.github.bkmbigo.solitaireanimation.presentation.locals.cardtheme.rememberCardTheme
import com.github.bkmbigo.solitaireanimation.presentation.locals.resourceprovider.DefaultResourceProvider
import com.github.bkmbigo.solitaireanimation.presentation.locals.resourceprovider.LocalResourceProvider
import com.github.bkmbigo.solitaireanimation.presentation.utils.fonts.localfonts.LocalFontFamily
import com.github.bkmbigo.solitaireanimation.presentation.utils.fonts.provideFontFamily

@Composable
fun SolitaireTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable ()-> Unit
) {
    var andikaFont by remember { mutableStateOf<FontFamily?>(null) }
    var lobsterTwoFont by remember { mutableStateOf<FontFamily?>(null) }

    LaunchedEffect(Unit) {
        andikaFont = LocalFontFamily.ANDIKA.provideFontFamily()
        lobsterTwoFont = LocalFontFamily.LOBSTER_TWO.provideFontFamily()
    }

    CompositionLocalProvider(
        LocalCardTheme provides rememberCardTheme(false, andikaFont, lobsterTwoFont),
        LocalResourceProvider provides DefaultResourceProvider
    ) {
        MaterialTheme(
            content = content
        )
    }
}
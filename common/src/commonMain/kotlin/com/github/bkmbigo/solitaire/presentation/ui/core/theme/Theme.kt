package com.github.bkmbigo.solitaire.presentation.ui.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import com.github.bkmbigo.solitaire.presentation.ui.core.locals.cardtheme.CardVectors
import com.github.bkmbigo.solitaire.presentation.ui.core.locals.cardtheme.EmptyCardVectors
import com.github.bkmbigo.solitaire.presentation.ui.core.locals.cardtheme.LocalCardTheme
import com.github.bkmbigo.solitaire.presentation.ui.core.locals.cardtheme.rememberCardTheme
import com.github.bkmbigo.solitaire.presentation.ui.core.locals.resourceprovider.DefaultResourceProvider
import com.github.bkmbigo.solitaire.presentation.ui.core.locals.resourceprovider.LocalResourceProvider
import com.github.bkmbigo.solitaire.presentation.ui.core.utils.fonts.localfonts.LocalFontFamily
import com.github.bkmbigo.solitaire.presentation.ui.core.utils.fonts.provideFontFamily
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SolitaireTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    var density = LocalDensity.current

    var andikaFont by remember { mutableStateOf<FontFamily?>(null) }
    var lobsterTwoFont by remember { mutableStateOf<FontFamily?>(null) }
    var cardVectors by remember { mutableStateOf<CardVectors?>(null) }

    LaunchedEffect(Unit) {
        launch {
            andikaFont = LocalFontFamily.ANDIKA.provideFontFamily()
            lobsterTwoFont = LocalFontFamily.LOBSTER_TWO.provideFontFamily()
        }
        launch {
//            cardVectors = getCardVectors(density)
        }
    }

    CompositionLocalProvider(
        LocalCardTheme provides rememberCardTheme(
            false,
            andikaFont,
            lobsterTwoFont,
            cardVectors ?: EmptyCardVectors
        ),
        LocalResourceProvider provides DefaultResourceProvider
    ) {
        MaterialTheme {
            ProvideTextStyle(
                value = MaterialTheme.typography
                    .bodyLarge
                    .copy(fontFamily = if (andikaFont != null) andikaFont else FontFamily.Cursive),
                content = content
            )
        }
    }
}

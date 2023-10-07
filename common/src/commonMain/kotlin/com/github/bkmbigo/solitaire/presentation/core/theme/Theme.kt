package com.github.bkmbigo.solitaire.presentation.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontFamily
import com.github.bkmbigo.solitaire.presentation.core.locals.cardtheme.LocalCardTheme
import com.github.bkmbigo.solitaire.presentation.core.locals.cardtheme.rememberCardTheme
import com.github.bkmbigo.solitaire.presentation.core.locals.resourceprovider.DefaultResourceProvider
import com.github.bkmbigo.solitaire.presentation.core.locals.resourceprovider.LocalResourceProvider
import com.github.bkmbigo.solitaire.presentation.core.utils.fonts.localfonts.LocalFontFamily
import com.github.bkmbigo.solitaire.presentation.core.utils.fonts.provideFontFamily
import com.github.bkmbigo.solitaire.utils.Platform
import kotlinx.coroutines.launch

@Composable
fun SolitaireTheme(
    platform: Platform,
    generalResourcePath: String = "",
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    var andikaFont by remember { mutableStateOf<FontFamily?>(null) }
    var lobsterTwoFont by remember { mutableStateOf<FontFamily?>(null) }

    LaunchedEffect(Unit) {
        launch {
            andikaFont = LocalFontFamily.ANDIKA.provideFontFamily(
                generalResourcePath = generalResourcePath,
                platform = platform
            )
            lobsterTwoFont = LocalFontFamily.LOBSTER_TWO.provideFontFamily(
                generalResourcePath = generalResourcePath,
                platform = platform
            )
        }
    }

    CompositionLocalProvider(
        LocalCardTheme provides rememberCardTheme(
            isDark = false,
            platform = platform,
            generalResourcePath = generalResourcePath,
            andikaFont = andikaFont,
            lobsterTwoFamily = lobsterTwoFont
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

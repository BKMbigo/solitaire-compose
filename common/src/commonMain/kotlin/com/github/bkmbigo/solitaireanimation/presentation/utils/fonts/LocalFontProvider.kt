package com.github.bkmbigo.solitaireanimation.presentation.utils.fonts

import androidx.compose.ui.text.font.FontFamily
import com.github.bkmbigo.solitaireanimation.presentation.utils.ResourcePath
import com.github.bkmbigo.solitaireanimation.presentation.utils.fonts.localfonts.LocalFontFamily

suspend fun LocalFontFamily.provideFontFamily(resourcePath: ResourcePath = ResourcePath.FONT_DIRECTORY) =
    FontFamily(
        fonts.map { localFont ->
            localFont.provideFont(resourcePath)
        }
    )
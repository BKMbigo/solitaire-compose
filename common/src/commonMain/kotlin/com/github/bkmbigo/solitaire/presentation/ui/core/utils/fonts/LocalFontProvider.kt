package com.github.bkmbigo.solitaire.presentation.ui.core.utils.fonts

import androidx.compose.ui.text.font.FontFamily
import com.github.bkmbigo.solitaire.presentation.ui.core.utils.ResourcePath
import com.github.bkmbigo.solitaire.presentation.ui.core.utils.fonts.localfonts.LocalFontFamily

suspend fun LocalFontFamily.provideFontFamily(resourcePath: ResourcePath = ResourcePath.FONT_DIRECTORY) =
    FontFamily(
        fonts.map { localFont ->
            localFont.provideFont(resourcePath)
        }
    )

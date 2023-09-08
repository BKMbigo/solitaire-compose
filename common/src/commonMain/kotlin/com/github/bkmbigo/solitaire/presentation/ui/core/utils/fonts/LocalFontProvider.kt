package com.github.bkmbigo.solitaire.presentation.ui.core.utils.fonts

import androidx.compose.ui.text.font.FontFamily
import com.github.bkmbigo.solitaire.presentation.ui.core.utils.ResourcePath
import com.github.bkmbigo.solitaire.presentation.ui.core.utils.fonts.localfonts.LocalFontFamily
import com.github.bkmbigo.solitaire.utils.Platform

suspend fun LocalFontFamily.provideFontFamily(resourcePath: ResourcePath = ResourcePath.FONT_DIRECTORY, platform: Platform) =
    FontFamily(
        fonts.map { localFont ->
            localFont.provideFont(resourcePath, platform)
        }
    )

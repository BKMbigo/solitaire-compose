package com.github.bkmbigo.solitaire.presentation.core.locals.resourceprovider

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontFamily
import com.github.bkmbigo.solitaire.presentation.core.utils.fonts.localfonts.LocalFontFamily

interface ResourceProvider {
    @Composable
    fun getImage(resourcePath: String): Painter
    suspend fun getFont(fontFamily: LocalFontFamily): FontFamily
}

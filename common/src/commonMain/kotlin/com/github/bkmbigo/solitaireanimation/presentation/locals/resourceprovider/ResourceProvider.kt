package com.github.bkmbigo.solitaireanimation.presentation.locals.resourceprovider

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontFamily
import com.github.bkmbigo.solitaireanimation.presentation.utils.fonts.localfonts.LocalFontFamily

interface ResourceProvider {
    @Composable
    fun getImage(resourcePath: String): Painter
    suspend fun getFont(fontFamily: LocalFontFamily): FontFamily
}
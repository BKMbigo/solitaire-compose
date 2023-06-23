package com.github.bkmbigo.solitaireanimation.presentation.locals.resourceprovider

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontFamily
import com.github.bkmbigo.solitaireanimation.presentation.utils.fonts.localfonts.LocalFontFamily
import com.github.bkmbigo.solitaireanimation.presentation.utils.fonts.provideFontFamily
import com.github.bkmbigo.solitaireanimation.presentation.utils.images.vectorResourceCached

val LocalResourceProvider = compositionLocalOf<ResourceProvider> { DefaultResourceProvider }

object DefaultResourceProvider: ResourceProvider {
    @Composable
    override fun getImage(resourcePath: String): Painter = vectorResourceCached(resourcePath)

    override suspend fun getFont(fontFamily: LocalFontFamily): FontFamily = fontFamily.provideFontFamily()

}
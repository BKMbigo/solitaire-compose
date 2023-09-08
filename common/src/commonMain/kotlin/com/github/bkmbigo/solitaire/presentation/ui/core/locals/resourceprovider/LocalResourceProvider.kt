package com.github.bkmbigo.solitaire.presentation.ui.core.locals.resourceprovider

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontFamily
import com.github.bkmbigo.solitaire.presentation.ui.core.utils.fonts.localfonts.LocalFontFamily
import com.github.bkmbigo.solitaire.presentation.ui.core.utils.fonts.provideFontFamily
import com.github.bkmbigo.solitaire.presentation.ui.core.utils.images.vectorResourceCached
import com.github.bkmbigo.solitaire.utils.Platform

val LocalResourceProvider = compositionLocalOf<ResourceProvider> { DefaultResourceProvider }

val DefaultResourceProvider = object : ResourceProvider {
    @Composable
    override fun getImage(resourcePath: String): Painter = vectorResourceCached(resourcePath)

    override suspend fun getFont(fontFamily: LocalFontFamily): FontFamily = fontFamily.provideFontFamily(platform = Platform.DESKTOP)

}

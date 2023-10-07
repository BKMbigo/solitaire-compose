package com.github.bkmbigo.solitaire.presentation.core.utils.fonts

import androidx.compose.ui.text.font.Font
import com.github.bkmbigo.solitaire.presentation.core.utils.ResourcePath
import com.github.bkmbigo.solitaire.presentation.core.utils.fonts.localfonts.LocalFont
import com.github.bkmbigo.solitaire.utils.Platform
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.resource

@OptIn(ExperimentalResourceApi::class)
internal actual suspend fun LocalFont.provideFont(
    generalResourcePath: String,
    resourcePath: ResourcePath,
    platform: Platform
): Font {

    print("Font: The full resource path is: ${generalResourcePath}${resourcePath.directoryPath}/${filename}")

    return androidx.compose.ui.text.platform.Font(
        identity = identity,
        data = resource("${generalResourcePath}${resourcePath.directoryPath}/${filename}").readBytes(),
        weight = weight,
        style = style
    )
}

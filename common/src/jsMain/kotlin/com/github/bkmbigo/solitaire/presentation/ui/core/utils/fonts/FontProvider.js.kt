package com.github.bkmbigo.solitaire.presentation.ui.core.utils.fonts

import androidx.compose.ui.text.font.Font
import com.github.bkmbigo.solitaire.presentation.ui.core.utils.ResourcePath
import com.github.bkmbigo.solitaire.presentation.ui.core.utils.images.resource
import com.github.bkmbigo.solitaire.presentation.ui.core.utils.fonts.localfonts.LocalFont
import com.github.bkmbigo.solitaire.utils.Platform
import org.jetbrains.compose.resources.ExperimentalResourceApi
import androidx.compose.ui.text.platform.Font as PlatformFont

@OptIn(ExperimentalResourceApi::class)
internal actual suspend fun LocalFont.provideFont(
    resourcePath: ResourcePath,
    platform: Platform
) : Font  = PlatformFont(
    identity = identity,
    data = resource("${resourcePath.directoryPath}/${filename}", platform).readBytes(),
    weight = weight,
    style = style
)

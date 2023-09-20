package com.github.bkmbigo.solitaire.presentation.ui.core.utils.fonts

import androidx.compose.ui.text.font.Font
import com.github.bkmbigo.solitaire.presentation.ui.core.utils.ResourcePath
import com.github.bkmbigo.solitaire.presentation.ui.core.utils.fonts.localfonts.LocalFont
import com.github.bkmbigo.solitaire.utils.Platform
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.resource

@OptIn(ExperimentalResourceApi::class)
internal actual suspend fun LocalFont.provideFont(
    resourcePath: ResourcePath,
    platform: Platform
): Font = androidx.compose.ui.text.platform.Font(
    identity = identity,
    data = resource("${resourcePath.directoryPath}/${filename}").readBytes(),
    weight = weight,
    style = style
)

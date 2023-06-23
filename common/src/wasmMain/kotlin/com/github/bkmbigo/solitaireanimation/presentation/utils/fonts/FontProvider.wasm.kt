package com.github.bkmbigo.solitaireanimation.presentation.utils.fonts

import androidx.compose.ui.text.font.Font
import com.github.bkmbigo.solitaireanimation.presentation.utils.ResourcePath
import com.github.bkmbigo.solitaireanimation.presentation.utils.fonts.localfonts.LocalFont
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.resource

@OptIn(ExperimentalResourceApi::class)
internal actual suspend fun LocalFont.provideFont(resourcePath: ResourcePath) : Font  = androidx.compose.ui.text.platform.Font(
    identity = identity,
    data = resource("${resourcePath.directoryPath}/${filename}").readBytes(),
    weight = weight,
    style = style
)
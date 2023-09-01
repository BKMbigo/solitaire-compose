package com.github.bkmbigo.solitaire.presentation.ui.core.utils.fonts

import androidx.compose.ui.text.font.Font
import com.github.bkmbigo.solitaire.presentation.ui.core.utils.ResourcePath
import com.github.bkmbigo.solitaire.presentation.ui.core.utils.fonts.localfonts.LocalFont

internal actual suspend fun LocalFont.provideFont(resourcePath: ResourcePath) : Font = Font(
    this.resource,
    weight = weight,
    style = style
)

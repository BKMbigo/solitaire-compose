package com.github.bkmbigo.solitaire.presentation.ui.core.utils.fonts

import androidx.compose.ui.text.font.Font
import com.github.bkmbigo.solitaire.presentation.ui.core.utils.ResourcePath
import com.github.bkmbigo.solitaire.presentation.ui.core.utils.fonts.localfonts.LocalFont

internal expect suspend fun LocalFont.provideFont(resourcePath: ResourcePath = ResourcePath.FONT_DIRECTORY): Font

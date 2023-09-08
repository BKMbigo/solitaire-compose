package com.github.bkmbigo.solitaire.presentation.ui.core.utils.fonts

import androidx.compose.ui.text.font.Font
import com.github.bkmbigo.solitaire.presentation.ui.core.utils.ResourcePath
import com.github.bkmbigo.solitaire.presentation.ui.core.utils.fonts.localfonts.LocalFont
import com.github.bkmbigo.solitaire.utils.Platform

internal expect suspend fun LocalFont.provideFont(resourcePath: ResourcePath = ResourcePath.FONT_DIRECTORY, platform: Platform): Font

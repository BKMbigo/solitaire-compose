package com.github.bkmbigo.solitaireanimation.presentation.utils.fonts

import androidx.compose.ui.text.font.Font
import com.github.bkmbigo.solitaireanimation.presentation.utils.ResourcePath
import com.github.bkmbigo.solitaireanimation.presentation.utils.fonts.localfonts.LocalFont

internal expect suspend fun LocalFont.provideFont(resourcePath: ResourcePath = ResourcePath.FONT_DIRECTORY): Font
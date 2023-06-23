package com.github.bkmbigo.solitaireanimation.presentation.utils.fonts

import androidx.compose.ui.text.font.Font
import com.github.bkmbigo.solitaireanimation.presentation.utils.fonts.localfonts.LocalFont

val fontDirectory = "assets/fonts/"

internal expect suspend fun LocalFont.provideFont(): Font
package com.github.bkmbigo.solitaireanimation.presentation.utils.fonts

import android.graphics.Typeface
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.github.bkmbigo.solitaireanimation.presentation.utils.fonts.localfonts.LocalFont

actual suspend fun LocalFont.provideFont() : Font = Font(
    this.resource,
    weight = weight,
    style = style
)
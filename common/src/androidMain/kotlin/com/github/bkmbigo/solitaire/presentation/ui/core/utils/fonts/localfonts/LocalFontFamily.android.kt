package com.github.bkmbigo.solitaire.presentation.ui.core.utils.fonts.localfonts

import androidx.compose.ui.text.font.FontWeight
import com.github.bkmbigo.solitaire.R
import com.github.bkmbigo.solitaire.presentation.ui.core.utils.fonts.localfonts.LocalFont

actual enum class LocalFontFamily(
    actual val fonts: List<LocalFont>
) {
    ANDIKA(
        fonts = listOf(
            LocalFont(
                resource = R.font.andika_bold,
                weight = FontWeight.Bold
            ),
            LocalFont(
                resource = R.font.andika_regular,
                weight = FontWeight.Normal
            )
        )
    ),
    LOBSTER_TWO(
        fonts = listOf(
            LocalFont(
                resource = R.font.lobster_two_bold,
                weight = FontWeight.Bold
            )
        )
    );
}

package com.github.bkmbigo.solitaire.presentation.ui.core.utils.fonts.localfonts

import androidx.compose.ui.text.font.FontWeight
import com.github.bkmbigo.solitaire.presentation.ui.core.utils.fonts.localfonts.LocalFont

actual enum class LocalFontFamily(
    actual val fonts: List<LocalFont>
) {
    ANDIKA(
        fonts = listOf(
            LocalFont(
                identity = "Andika Bold",
                filename = "Andika-Bold.ttf",
                weight = FontWeight.Bold
            ),
            LocalFont(
                identity = "Andika Regular",
                filename = "Andika-Regular.ttf",
                weight = FontWeight.Normal
            )
        )
    ),
    LOBSTER_TWO(
        fonts = listOf(
            LocalFont(
                identity = "Lobster Two",
                filename = "LobsterTwo-Bold.ttf",
                weight = FontWeight.Bold
            )
        )
    );
}

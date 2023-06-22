package com.github.bkmbigo.solitaireanimation.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun SolitaireAnimationTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable ()-> Unit
) {


    MaterialTheme(
        content = content
    )
}
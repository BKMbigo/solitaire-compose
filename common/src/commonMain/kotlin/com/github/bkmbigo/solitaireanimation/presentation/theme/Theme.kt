package com.github.bkmbigo.solitaireanimation.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.github.bkmbigo.solitaireanimation.presentation.locals.cardtheme.DefaultCardTheme
import com.github.bkmbigo.solitaireanimation.presentation.locals.cardtheme.LocalCardTheme
import com.github.bkmbigo.solitaireanimation.presentation.locals.resourceprovider.DefaultResourceProvider
import com.github.bkmbigo.solitaireanimation.presentation.locals.resourceprovider.LocalResourceProvider

@Composable
fun SolitaireAnimationTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable ()-> Unit
) {

    CompositionLocalProvider(
        LocalCardTheme provides DefaultCardTheme,
        LocalResourceProvider provides DefaultResourceProvider
    ) {
        MaterialTheme(
            content = content
        )
    }
}
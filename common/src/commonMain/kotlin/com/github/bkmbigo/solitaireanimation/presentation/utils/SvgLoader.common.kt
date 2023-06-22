package com.github.bkmbigo.solitaireanimation.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale

@Composable
expect fun SvgLoader(
    image: SvgImage,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop
)
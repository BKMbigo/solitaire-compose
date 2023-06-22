package com.github.bkmbigo.solitaireanimation.presentation.utils

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

@Composable
actual fun SvgLoader(
    image: SvgImage,
    modifier: Modifier,
    contentScale: ContentScale
) {
    Image(
//        painter = painterResource(id = image.drawable),
        painter = painterResource(id = 1),
        contentDescription = null,
        modifier = modifier,
        contentScale = contentScale
    )
}
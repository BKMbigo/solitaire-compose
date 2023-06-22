package com.github.bkmbigo.solitaireanimation.presentation.utils

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.resource
import org.jetbrains.skia.Image as SkiaImage

@OptIn(ExperimentalResourceApi::class, ExperimentalAnimationApi::class)
@Composable
actual fun SvgLoader(
    image: SvgImage,
    modifier: Modifier,
    contentScale: ContentScale
) {
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(Unit) {
        val byteArray = resource(image.filename).readBytes()
        imageBitmap = SkiaImage.makeFromEncoded(byteArray).toComposeImageBitmap()
    }

    AnimatedContent(
        targetState = imageBitmap != null,
        modifier = modifier,
        contentAlignment = Alignment.Center,
        label = "",
    ) { isImageLoaded ->
        when(isImageLoaded) {
            true -> {
                Image(
                    bitmap = imageBitmap!!,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
            false -> {
                CircularProgressIndicator()
            }
        }
    }

}
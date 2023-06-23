package com.github.bkmbigo.solitaireanimation.presentation.utils.images

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.LoadState
import org.jetbrains.compose.resources.orEmpty
import org.jetbrains.compose.resources.rememberImageBitmap
import org.jetbrains.compose.resources.resource

private val cache = mutableStateMapOf<String, Painter>()

@OptIn(ExperimentalResourceApi::class)
@Composable
internal fun painterResourceCached(res: String): Painter =
    if(cache.containsKey(res)) {
        cache[res]!!
    } else {
        val imageBitmap = resource(res).rememberImageBitmap()
        if(imageBitmap !is LoadState.Success<ImageBitmap>) {
            BitmapPainter(imageBitmap.orEmpty())
        } else {
            cache[res] = BitmapPainter(imageBitmap.orEmpty())
            cache[res]!!
        }
    }
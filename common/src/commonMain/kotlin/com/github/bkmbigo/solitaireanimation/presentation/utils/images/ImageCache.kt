package com.github.bkmbigo.solitaireanimation.presentation.utils.images

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalDensity
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.LoadState
import org.jetbrains.compose.resources.orEmpty
import org.jetbrains.compose.resources.rememberImageBitmap
import org.jetbrains.compose.resources.rememberImageVector
import org.jetbrains.compose.resources.resource

private val cache = mutableStateMapOf<String, Painter>()

@OptIn(ExperimentalResourceApi::class)
@Composable
internal fun vectorResourceCached(res: String): Painter =
    if(cache.containsKey(res)) {
        cache[res]!!
    } else {
        val imageBitmap = resource(res).rememberImageVector(LocalDensity.current)
        if(imageBitmap !is LoadState.Success<ImageVector>) {
            rememberVectorPainter(imageBitmap.orEmpty())
        } else {
            cache[res] = rememberVectorPainter(imageBitmap.orEmpty())
            cache[res]!!
        }
    }
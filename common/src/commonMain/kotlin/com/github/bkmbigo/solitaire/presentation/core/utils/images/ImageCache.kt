package com.github.bkmbigo.solitaire.presentation.core.utils.images

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import com.github.bkmbigo.solitaire.presentation.core.utils.ResourcePath
import org.jetbrains.compose.resources.*

private val bitmapCache = mutableStateMapOf<String, Painter>()
internal val vectorCache: MutableMap<String, ImageVector> = mutableMapOf()

@Composable
expect fun vectorResourceCached(
    res: String,
    resourcePath: ResourcePath = ResourcePath.IMAGE_DIRECTORY
): Painter

@OptIn(ExperimentalResourceApi::class)
@Composable
fun painterResourceCached(
    res: String,
    resourcePath: ResourcePath = ResourcePath.IMAGE_DIRECTORY
): Painter {
    val fullResourcePath = "${resourcePath}/$res"

    return if (bitmapCache.containsKey(fullResourcePath)) {
        bitmapCache[fullResourcePath]!!
    } else {
        val rib = resource(fullResourcePath).rememberImageBitmap()
        if (rib !is LoadState.Success<ImageBitmap>) {
            BitmapPainter(rib.orEmpty())
        } else {
            bitmapCache[fullResourcePath] = BitmapPainter(rib.orEmpty())
            bitmapCache[fullResourcePath]!!
        }
    }
}

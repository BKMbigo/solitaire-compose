package com.github.bkmbigo.solitaireanimation.presentation.utils.images

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.VectorPainter
import com.github.bkmbigo.solitaireanimation.presentation.utils.ResourcePath
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.LoadState
import org.jetbrains.compose.resources.orEmpty
import org.jetbrains.compose.resources.rememberImageBitmap
import org.jetbrains.compose.resources.resource

private val bitmapCache = mutableStateMapOf<String, Painter>()
internal val vectorCache: MutableMap<String, VectorPainter> = mutableMapOf()

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
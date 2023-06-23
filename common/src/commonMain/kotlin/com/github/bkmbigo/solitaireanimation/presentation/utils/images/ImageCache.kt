package com.github.bkmbigo.solitaireanimation.presentation.utils.images

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalDensity
import com.github.bkmbigo.solitaireanimation.presentation.utils.ResourcePath
import com.github.bkmbigo.solitaireanimation.utils.Logger
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.LoadState
import org.jetbrains.compose.resources.orEmpty
import org.jetbrains.compose.resources.rememberImageBitmap
import org.jetbrains.compose.resources.rememberImageVector
import org.jetbrains.compose.resources.resource

private val cache = mutableStateMapOf<String, Painter>()

@Composable
expect fun vectorResourceCached(res: String, resourcePath: ResourcePath = ResourcePath.IMAGE_DIRECTORY): Painter

@OptIn(ExperimentalResourceApi::class)
@Composable
fun painterResourceCached(res: String, resourcePath: ResourcePath = ResourcePath.IMAGE_DIRECTORY): Painter {
    val fullResourcePath = "${resourcePath}/$res"

    return if (cache.containsKey(fullResourcePath)) {
        cache[fullResourcePath]!!
    } else {
        val rib = resource(fullResourcePath).rememberImageBitmap()
        if (rib !is LoadState.Success<ImageBitmap>) {
            BitmapPainter(rib.orEmpty())
        } else {
            cache[fullResourcePath] = BitmapPainter(rib.orEmpty())
            cache[fullResourcePath]!!
        }
    }
}
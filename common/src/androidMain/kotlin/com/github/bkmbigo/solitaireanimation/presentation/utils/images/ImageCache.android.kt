package com.github.bkmbigo.solitaireanimation.presentation.utils.images

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalDensity
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.LoadState
import org.jetbrains.compose.resources.orEmpty
import org.jetbrains.compose.resources.rememberImageVector
import org.jetbrains.compose.resources.resource

@OptIn(ExperimentalResourceApi::class)
@Composable
actual fun vectorResourceCached(res: String): Painter {
    val imageBitmap = resource(res).rememberImageVector(LocalDensity.current)
    return if (imageBitmap !is LoadState.Success<ImageVector>) {
        rememberVectorPainter(imageBitmap.orEmpty())
    } else {
        rememberVectorPainter(imageBitmap.orEmpty())
    }
}
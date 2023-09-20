package com.github.bkmbigo.solitaire.presentation.ui.core.utils.images

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalDensity
import com.github.bkmbigo.solitaire.presentation.ui.core.locals.cardtheme.LocalCardTheme
import com.github.bkmbigo.solitaire.presentation.ui.core.utils.ResourcePath
import com.github.bkmbigo.solitaire.utils.Platform
import org.jetbrains.compose.resources.*
import java.io.IOException

@OptIn(ExperimentalResourceApi::class)
@Composable
actual fun vectorResourceCached(res: String, resourcePath: ResourcePath): Painter {
    val cardTheme = LocalCardTheme.current
    val fullResourcePath = "${resourcePath.directoryPath}/$res"

    return if (vectorCache.containsKey(fullResourcePath)) {
        rememberVectorPainter(vectorCache[fullResourcePath]!!)
    } else {
        val imageBitmap =
            resource(fullResourcePath, platform = cardTheme.platform).rememberImageVector(LocalDensity.current)
        return if (imageBitmap !is LoadState.Success<ImageVector>) {
            val resource = resource(fullResourcePath)
            val bitmap = imageBitmap.orEmpty()
            rememberVectorPainter(bitmap)
        } else {
            val newVector = imageBitmap.orEmpty()
            vectorCache[fullResourcePath] = newVector
            rememberVectorPainter(newVector)
        }
    }
}

@ExperimentalResourceApi
fun resource(path: String, platform: Platform): Resource = DesktopResourceImpl(path, platform)

@ExperimentalResourceApi
private class DesktopResourceImpl(path: String, val platform: Platform) : AbstractResourceImpl(path) {
    override suspend fun readBytes(): ByteArray {
        val classLoader = when (platform) {
            Platform.INTELLIJ -> DesktopResourceImpl::class.java.classLoader
            else -> Thread.currentThread().contextClassLoader ?: (::DesktopResourceImpl.javaClass.classLoader)
        }
        val resource = classLoader?.getResourceAsStream(path)
        if (resource != null) {
            return resource.readBytes()
        } else {
            throw MissingResourceException(path)
        }
    }
}


@OptIn(ExperimentalResourceApi::class)
internal abstract class AbstractResourceImpl(val path: String) : Resource {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        return if (other is AbstractResourceImpl) {
            path == other.path
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        return path.hashCode()
    }
}

internal class MissingResourceException(path: String) :
    IOException("Missing resource with path: $path")


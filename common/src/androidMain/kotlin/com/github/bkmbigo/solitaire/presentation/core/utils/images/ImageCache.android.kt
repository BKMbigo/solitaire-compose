@file:Suppress(
    "INVISIBLE_MEMBER",
    "CANNOT_OVERRIDE_INVISIBLE_MEMBER",
    "INVISIBLE_REFERENCE",
    "EXPOSED_PARAMETER_TYPE",
)

package com.github.bkmbigo.solitaire.presentation.core.utils.images

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalDensity
import com.github.bkmbigo.solitaire.presentation.core.utils.ResourcePath
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@OptIn(ExperimentalResourceApi::class)
@Composable
actual fun vectorResourceCached(res: String, resourcePath: ResourcePath): Painter {
    val fullResourcePath = "${resourcePath.directoryPath}/$res"

    return if (vectorCache.containsKey(fullResourcePath)) {
        rememberVectorPainter(vectorCache[fullResourcePath]!!)
    } else {
        val imageBitmap = resource(fullResourcePath).rememberImageVector(LocalDensity.current)
        return if (imageBitmap !is LoadState.Success<ImageVector>) {
            rememberVectorPainter(imageBitmap.orEmpty())
        } else {
            val newVector = imageBitmap.orEmpty()
            vectorCache[fullResourcePath] = newVector
            rememberVectorPainter(newVector)
        }
    }
}

@ExperimentalResourceApi
fun resource(path: String): Resource = AndroidResourceImpl(path)

@ExperimentalResourceApi
private class AndroidResourceImpl(path: String) : AbstractResourceImpl(path) {
    override suspend fun readBytes(): ByteArray = withContext(Dispatchers.IO) {
        suspendCoroutine { continuation ->
            val classLoader = Thread.currentThread().contextClassLoader ?: (::AndroidResourceImpl.javaClass.classLoader)
            val resource = classLoader.getResourceAsStream(path)
            if (resource != null) {
                val bytes = resource.readBytes()
                continuation.resume(bytes)
            } else {
                continuation.resumeWithException(MissingResourceException(path))
            }
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

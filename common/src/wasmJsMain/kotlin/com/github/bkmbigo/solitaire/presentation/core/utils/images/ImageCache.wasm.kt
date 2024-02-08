@file:Suppress(
    "INVISIBLE_MEMBER",
    "CANNOT_OVERRIDE_INVISIBLE_MEMBER",
    "INVISIBLE_REFERENCE",
    "EXPOSED_PARAMETER_TYPE",
)

package com.github.bkmbigo.solitaire.presentation.core.utils.images

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import com.github.bkmbigo.solitaire.presentation.core.locals.cardtheme.LocalCardTheme
import com.github.bkmbigo.solitaire.presentation.core.utils.ResourcePath
import org.jetbrains.compose.resources.*
import org.jetbrains.compose.resources.vector.parseVectorRoot
import org.w3c.dom.parsing.DOMParser
import org.w3c.dom.Element as DomElement
import org.w3c.dom.Node as DomNode

@OptIn(ExperimentalResourceApi::class)
@Composable
actual fun vectorResourceCached(res: String, resourcePath: ResourcePath): Painter {
    val cardTheme = LocalCardTheme.current

    val generalResourcePath = cardTheme.generalResourcePath.ifBlank { "" }

//    "https://bkmbigo.github.io/solitaire-compose/wasm/solitaire-compose/assets/images/suite_spade.xml"
//    "https://bkmbigo.github.io/solitaire-compose/assets/images/suite_spade.xml"

    val fullResourcePath = "${generalResourcePath}${resourcePath.directoryPath}/$res"

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

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun Resource.rememberImageVector(density: Density): LoadState<ImageVector> {
    val state: MutableState<LoadState<ImageVector>> =
        remember(this, density) { mutableStateOf(LoadState.Loading()) }
    LaunchedEffect(this, density) {
        state.value = try {
            LoadState.Success(readBytes().toImageVector(density))
        } catch (e: Exception) {
            LoadState.Error(e)
        }
    }
    return state.value
}

private fun ByteArray.toImageVector(density: Density): ImageVector =
    parseXML(this).parseVectorRoot(density)

private fun parseXML(byteArray: ByteArray): org.jetbrains.compose.resources.vector.xmldom.Element {
    val xmlString = byteArray.decodeToString()
    val xmlDom = DOMParser().parseFromString(xmlString, "application/xml".toJsString())
    val domElement =
        xmlDom.documentElement ?: throw MalformedXMLException("missing documentElement")
    return ElementImpl(domElement)
}

class MalformedXMLException(message: String?) : Exception(message)

private open class NodeImpl(val n: DomNode) : org.jetbrains.compose.resources.vector.xmldom.Node {
    override val nodeName: String
        get() = n.nodeName

    override val localName =
        "" /* localName is not a Node property, only applies to Elements and Attrs */

    override val namespaceURI =
        "" /* namespaceURI is not a Node property, only applies to Elements and Attrs */

    override val childNodes: org.jetbrains.compose.resources.vector.xmldom.NodeList by lazy {
        object : org.jetbrains.compose.resources.vector.xmldom.NodeList {
            override fun item(i: Int): org.jetbrains.compose.resources.vector.xmldom.Node {
                val child = n.childNodes.item(i)
                    ?: throw IndexOutOfBoundsException("no child node accessible at index=$i")
                return if (child is DomElement) ElementImpl(child) else NodeImpl(child)
            }

            override val length: Int = n.childNodes.length
        }
    }

    override fun lookupPrefix(namespaceURI: String): String = n.lookupPrefix(namespaceURI) ?: ""
}

private class ElementImpl(val element: DomElement) : NodeImpl(element),
    org.jetbrains.compose.resources.vector.xmldom.Element {

    override val localName: String
        get() = element.localName

    override val namespaceURI: String
        get() = element.namespaceURI ?: ""

    override fun getAttributeNS(nameSpaceURI: String, localName: String): String =
        element.getAttributeNS(nameSpaceURI, localName) ?: ""

    override fun getAttribute(name: String): String = element.getAttribute(name) ?: ""
}

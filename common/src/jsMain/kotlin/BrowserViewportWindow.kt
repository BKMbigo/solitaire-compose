@file:Suppress(
    "INVISIBLE_MEMBER",
    "INVISIBLE_REFERENCE",
    "EXPOSED_PARAMETER_TYPE",
)

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.window.ComposeWindow
import kotlinx.browser.document
import kotlinx.browser.window
import org.jetbrains.skiko.CanvasRenderer
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.HTMLStyleElement
import org.w3c.dom.HTMLTitleElement

private const val CANVAS_ELEMENT_ID = "ComposeTarget" // Hardwired into ComposeWindow

/**
 * A Skiko/Canvas-based top-level window using the browser's entire viewport. Supports resizing.
 */
@Suppress("FunctionName")
fun BrowserViewportWindow(
    title: String = "Untitled",
    content: @Composable ComposeWindow.(Int) -> Unit
) {
    val htmlHeadElement = document.head!!
    htmlHeadElement.appendChild(
        (document.createElement("style") as HTMLStyleElement).apply {
            type = "text/css"
            appendChild(
                document.createTextNode(
                    """
                    .loading {
                        visibility: hidden
                    }
                    html, body {
                        overflow: hidden;
                        margin: 0 !important;
                        padding: 0 !important;
                    }
                    #$CANVAS_ELEMENT_ID {
                        outline: none;
                    }
                    """.trimIndent()
                )
            )
        }
    )

    fun HTMLCanvasElement.fillViewportSize() {
        console.log("Window Inner Width is: ${window.innerWidth}")
        console.log("Window Inner Height is: ${window.innerHeight}")

        setAttribute("width", "${window.innerWidth}")
        setAttribute("height", "${window.innerHeight}")
    }

    fun getHtmlCanvas() = (document.getElementById(CANVAS_ELEMENT_ID) as HTMLCanvasElement).apply {
        fillViewportSize()
    }

    getHtmlCanvas()

    ComposeWindow().apply {
        window.addEventListener("resize", {
            val scale = layer.layer.contentScale
            val density = window.devicePixelRatio.toFloat()
            val canvas = getHtmlCanvas()

            console.log("Scale is $scale")
            console.log("Density is $density")

            canvas.fillViewportSize()

            //layer.layer.attachTo(canvas)
            canvas.style.width = "${canvas.width}px"
            canvas.style.height = "${canvas.height}px"
            layer.layer.state = object : CanvasRenderer(canvas) {
                override fun drawFrame(currentTimestamp: Double) {
                    // currentTimestamp is in milliseconds.
                    val currentNanos = currentTimestamp * 1_000_000
                    layer.layer.skikoView?.onRender(
                        layer.layer.state?.canvas!!,
                        canvas.width,
                        canvas.height,
                        currentNanos.toLong()
                    )
                }
            }.apply { initCanvas(canvas.width, canvas.height, scale, layer.layer.pixelGeometry) }

            layer.layer.needRedraw()
            layer.setSize(
                (canvas.width / scale * density).toInt(),
                (canvas.height / scale * density).toInt()
            )
        })

        // WORKAROUND: ComposeWindow does not implement `setTitle(title)`
        val htmlTitleElement = (
                htmlHeadElement.getElementsByTagName("title").item(0)
                    ?: document.createElement("title").also { htmlHeadElement.appendChild(it) }
                ) as HTMLTitleElement
        htmlTitleElement.textContent = title

        setContent {
            var screenWidth =
                (window.innerWidth / layer.layer.contentScale * window.devicePixelRatio.toFloat()).toInt()

            LaunchedEffect(Unit) {
                window.addEventListener("resize", {
//                    setNewCanvasSize()
                    screenWidth =
                        (window.innerWidth / layer.layer.contentScale * window.devicePixelRatio.toFloat()).toInt()
                })
            }

            content(screenWidth)
        }
    }
}

import com.github.bkmbigo.solitaire.presentation.ui.core.screens.StartScreen
import com.github.bkmbigo.solitaire.presentation.ui.core.theme.SolitaireTheme
import com.github.bkmbigo.solitaire.utils.Platform
import kotlinx.browser.document
import org.jetbrains.skiko.wasm.onWasmReady

fun main() {
    /* Resources are handled differently on VSCode extensions: Read more: https://code.visualstudio.com/api/extension-guides/webview#loading-local-content
    *   To overcome difference in paths: The application gets the location of the loaded css stylesheet and determines the location of the rest of the resources from the path obtained */
    val path = document.getElementById("loadingStyleSheet")?.getAttribute("href")?.substringBefore("loading.css")
    console.log("Path obtained is: $path")

    onWasmReady {
        BrowserViewportWindow("Solitaire") {
            SolitaireTheme(
                platform = Platform.VSCODE,
                generalResourcePath = path ?: ""
            ) {
                StartScreen()
            }
        }
    }
}

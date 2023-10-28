import com.github.bkmbigo.solitaire.presentation.core.screens.StartScreen
import com.github.bkmbigo.solitaire.presentation.core.theme.SolitaireTheme
import com.github.bkmbigo.solitaire.utils.Platform
import org.jetbrains.skiko.wasm.onWasmReady

fun main() {

    val generalResourcePath = ""

    onWasmReady {
        BrowserViewportWindow("Solitaire") {
            SolitaireTheme(
                generalResourcePath = generalResourcePath,
                platform = Platform.JS
            ) {
                StartScreen()
            }
        }
    }
}

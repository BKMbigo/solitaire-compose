import com.github.bkmbigo.solitaire.presentation.ui.core.screens.StartScreen
import com.github.bkmbigo.solitaire.presentation.ui.core.theme.SolitaireTheme
import com.github.bkmbigo.solitaire.utils.Platform
import org.jetbrains.skiko.wasm.onWasmReady

fun main() {
    onWasmReady {
        BrowserViewportWindow("Solitaire") {
            SolitaireTheme(
                platform = Platform.JS
            ) {
                StartScreen()
            }
        }
    }
}

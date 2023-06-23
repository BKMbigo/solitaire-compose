import com.github.bkmbigo.solitaireanimation.presentation.screens.GameScreen
import com.github.bkmbigo.solitaireanimation.presentation.theme.SolitaireTheme
import org.jetbrains.skiko.wasm.onWasmReady

fun main() {
    onWasmReady {
        BrowserViewportWindow("Solitaire") { screenWidth ->
            SolitaireTheme {
                GameScreen()
            }
        }
    }
}
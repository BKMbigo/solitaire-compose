import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.github.bkmbigo.solitaire.presentation.ui.core.screens.StartScreen
import com.github.bkmbigo.solitaire.presentation.ui.core.theme.SolitaireTheme
import com.github.bkmbigo.solitaire.utils.Platform

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    CanvasBasedWindow("Solitaire") {
        SolitaireTheme(
            platform = Platform.WASM
        ) {
            StartScreen()
        }
    }
}

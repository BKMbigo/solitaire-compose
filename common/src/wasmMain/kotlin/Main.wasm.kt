import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.github.bkmbigo.solitaire.presentation.ui.core.screens.StartScreen
import com.github.bkmbigo.solitaire.presentation.ui.core.theme.SolitaireTheme

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    CanvasBasedWindow("Solitaire") {
        SolitaireTheme {
            StartScreen()
        }
    }
}

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.github.bkmbigo.solitaireanimation.presentation.screens.GameScreen
import com.github.bkmbigo.solitaireanimation.presentation.theme.SolitaireTheme

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    CanvasBasedWindow("Solitaire") {
        SolitaireTheme {
            GameScreen()
        }
    }
}
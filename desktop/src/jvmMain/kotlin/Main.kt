import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.github.bkmbigo.solitaireanimation.presentation.screens.GameScreen
import com.github.bkmbigo.solitaireanimation.presentation.theme.SolitaireTheme

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Solitaire Animation"
    ) {
        SolitaireTheme {
            GameScreen()
        }
    }
}
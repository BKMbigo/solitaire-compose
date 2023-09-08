import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.github.bkmbigo.solitaire.presentation.ui.core.screens.StartScreen
import com.github.bkmbigo.solitaire.presentation.ui.core.theme.SolitaireTheme
import com.github.bkmbigo.solitaire.utils.Platform

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Solitaire Animation"
    ) {
        SolitaireTheme(
            platform = Platform.DESKTOP
        ) {
            StartScreen()
        }
    }
}

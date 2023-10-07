import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.github.bkmbigo.solitaire.presentation.core.screens.StartScreen
import com.github.bkmbigo.solitaire.presentation.core.theme.SolitaireTheme
import com.github.bkmbigo.solitaire.utils.Platform

@OptIn(ExperimentalComposeUiApi::class)
fun main() {

    val generalResourcePath = "solitaire-compose/" // Use in production
//    val generalResourcePath = "" // Use in local development

    CanvasBasedWindow("Solitaire") {
        SolitaireTheme(
            generalResourcePath = generalResourcePath,
            platform = Platform.WASM
        ) {
            StartScreen()
        }
    }
}

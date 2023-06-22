import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.github.bkmbigo.solitaireanimation.presentation.screens.GameScreen
import kotlinx.browser.document
import org.jetbrains.skiko.wasm.onWasmReady

fun main() {
    onWasmReady {
        BrowserViewportWindow("Solitaire") { screenWidth ->
            GameScreen()
        }
    }
}
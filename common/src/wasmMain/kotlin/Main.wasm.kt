import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.CanvasBasedWindow
import com.github.bkmbigo.solitaireanimation.presentation.screens.GameScreen

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
     CanvasBasedWindow("Solitaire") {
         GameScreen()
     }
 }
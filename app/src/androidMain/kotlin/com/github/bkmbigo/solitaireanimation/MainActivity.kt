package com.github.bkmbigo.solitaireanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.bkmbigo.solitaireanimation.ui.theme.SolitaireAnimationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SolitaireAnimationTheme {
                Text(
                    text = "Sere"
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SolitaireAnimationTheme {
        Box(modifier = Modifier.fillMaxSize()) {
//            Image(
//                painter = painterResource(id = Card.THREE_OF_CLOVER.image.drawable),
//                contentDescription = null,
//                modifier = Modifier
//                    .align(Alignment.Center)
//                    .background(Color(0xFFC6F8CA))
//                    .clip(RoundedCornerShape(4.dp))
//                    .border(1.dp, color = Color.Black, shape = RoundedCornerShape(4.dp))
//            )
        }
    }
}
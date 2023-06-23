package com.github.bkmbigo.solitaireanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.bkmbigo.solitaireanimation.models.Card
import com.github.bkmbigo.solitaireanimation.presentation.locals.resourceprovider.LocalResourceProvider
import com.github.bkmbigo.solitaireanimation.presentation.screens.GameScreen
import com.github.bkmbigo.solitaireanimation.presentation.theme.SolitaireTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SolitaireTheme {
                GameScreen()
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
    SolitaireTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = LocalResourceProvider.current.getImage(resourcePath = "assets/images/${Card.THREE_OF_CLOVER.imageFilename}"),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.Center)
                    .background(Color(0xFFC6F8CA))
                    .clip(RoundedCornerShape(4.dp))
                    .border(1.dp, color = Color.Black, shape = RoundedCornerShape(4.dp))
            )
        }
    }
}
package com.github.bkmbigo.solitaire

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.bkmbigo.solitaire.presentation.ui.core.screens.StartScreen
import com.github.bkmbigo.solitaire.presentation.ui.core.theme.SolitaireTheme
import com.github.bkmbigo.solitaire.utils.Platform

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SolitaireTheme(
                platform = Platform.ANDROID
            ) {
                StartScreen()
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
    SolitaireTheme(
        platform = Platform.ANDROID
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

        }
    }
}

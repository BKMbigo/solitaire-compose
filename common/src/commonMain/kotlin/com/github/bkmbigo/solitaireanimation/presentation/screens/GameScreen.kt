package com.github.bkmbigo.solitaireanimation.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.github.bkmbigo.solitaireanimation.models.CardSuite
import com.github.bkmbigo.solitaireanimation.presentation.utils.SvgLoader

@Composable
fun GameScreen() {

    val card by remember { mutableStateOf(CardSuite.DIAMOND) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
//        SvgLoader(
//            image = card.image,
//            modifier = Modifier.align(Alignment.Center)
//        )
        Text(
            text = "Hello Game"
        )
    }
}
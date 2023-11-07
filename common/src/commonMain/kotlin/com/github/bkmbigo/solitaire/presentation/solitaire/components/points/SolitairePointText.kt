package com.github.bkmbigo.solitaire.presentation.solitaire.components.points

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@Composable
fun SolitairePointText(
    points: Int,
    modifier: Modifier = Modifier
) {
    val materialColorScheme = MaterialTheme.colorScheme

    var previousPoints by remember { mutableStateOf(0) }

    var textColor by remember { mutableStateOf(materialColorScheme.onBackground) }

    LaunchedEffect(points) {
        if (points > previousPoints) {
            textColor = Color.Green
        } else if (points < previousPoints) {
            textColor = Color.Red
        }

        delay(300)
        if (isActive) {
            previousPoints = points

            textColor = materialColorScheme.onBackground
        }
    }

    Row(
        modifier = modifier
    ) {
        Text(
            text = "Points: $points",
            color = textColor,
            modifier = Modifier.padding(horizontal = 2.dp)
        )
    }
}

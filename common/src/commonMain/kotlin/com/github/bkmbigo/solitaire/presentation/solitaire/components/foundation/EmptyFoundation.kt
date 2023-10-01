package com.github.bkmbigo.solitaire.presentation.solitaire.components.foundation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.bkmbigo.solitaire.models.core.CardSuite
import com.github.bkmbigo.solitaire.presentation.core.utils.images.vectorResourceCached

@Composable
fun EmptyFoundation(
    suite: CardSuite,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        border = BorderStroke(1.dp, Color.Black),
        shape = RoundedCornerShape(5.dp),
        color = Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = vectorResourceCached(suite.imageFilename),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(0.2f)
                    .align(Alignment.Center)
            )
        }
    }
}

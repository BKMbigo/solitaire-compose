package com.github.bkmbigo.solitaireanimation.presentation.components.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.bkmbigo.solitaireanimation.models.Card
import com.github.bkmbigo.solitaireanimation.presentation.theme.locals.LocalCardTheme

/* [MiniCard] Displays a mini card whenever full cards cannot fit in the screen. Please ensure the card maintain an aspect ratio ~ 155:243
* */

@Composable
fun MiniCard(
    card: Card,
    isFlipped: Boolean,
    modifier: Modifier = Modifier
) {

    val cardTheme = LocalCardTheme.current

   Column(
       modifier = modifier
   ) {
       Row(
           modifier = Modifier
               .fillMaxWidth()
               .padding(
                   horizontal = 4.dp,
                   vertical = 4.dp
               ),
           horizontalArrangement = Arrangement.SpaceBetween,
           verticalAlignment = Alignment.CenterVertically
       ) {
           Text(
               text = card.rank.symbol,
           )
       }
   }
}
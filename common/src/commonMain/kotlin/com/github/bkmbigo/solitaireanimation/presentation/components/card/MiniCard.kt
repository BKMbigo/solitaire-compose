package com.github.bkmbigo.solitaireanimation.presentation.components.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.bkmbigo.solitaireanimation.models.Card
import com.github.bkmbigo.solitaireanimation.models.CardColor
import com.github.bkmbigo.solitaireanimation.presentation.locals.cardtheme.LocalCardTheme
import com.github.bkmbigo.solitaireanimation.presentation.utils.images.vectorResourceCached

/* [MiniCard] Displays a mini card whenever full cards cannot fit in the screen. Please ensure the card maintain an aspect ratio ~ 155:243
* */

@Composable
internal fun MiniCard(
    card: Card,
    isFlipped: Boolean,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false
) {
    val cardTheme = LocalCardTheme.current

    Surface(
        modifier = modifier.size(cardTheme.cardSize),
        border = if (isSelected) BorderStroke(2.dp, cardTheme.cardSelectedColor)
        else BorderStroke(1.dp, Color.Black),
        color = cardTheme.cardFrontBackground,
        shape = RoundedCornerShape(5.dp),
        shadowElevation = 8.dp
    ) {
        if (isFlipped) {
            Image(
                painter = vectorResourceCached(Card.cardBackFilename),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 4.dp
                        )
                        .border(1.dp, Color.Black),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = card.rank.symbol,
                        fontWeight = FontWeight.Bold,
                        fontFamily = cardTheme.miniCardFont,
                        color = when (card.suite.color) {
                            CardColor.RED -> Card.redColor
                            CardColor.BLACK -> when (cardTheme.isDark) {
                                true -> Color.Black
                                false -> Color.White
                            }
                        },
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.CenterVertically),
                        fontSize = 14.sp
                    )

                    Image(
                        painter = vectorResourceCached(card.suite.imageFilename),
                        contentDescription = null,
                        modifier = Modifier
                            .size(12.dp)
                            .align(Alignment.CenterVertically)
                    )
                }

                Box(
                    modifier = Modifier.weight(1f, true).fillMaxWidth()
                ) {
                    Image(
                        painter = vectorResourceCached(card.suite.imageFilename),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize(0.4f)
                            .align(Alignment.Center)
                    )
                }
            }
        }
    }
}
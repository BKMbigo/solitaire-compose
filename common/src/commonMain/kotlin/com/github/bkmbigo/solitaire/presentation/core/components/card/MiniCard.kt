package com.github.bkmbigo.solitaire.presentation.core.components.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.bkmbigo.solitaire.models.core.Card
import com.github.bkmbigo.solitaire.models.core.CardColor
import com.github.bkmbigo.solitaire.presentation.core.locals.cardtheme.LocalCardTheme
import com.github.bkmbigo.solitaire.presentation.core.utils.images.vectorResourceCached

/* [MiniCard] Displays a mini card whenever full cards cannot fit in the screen. Please ensure the card maintain an aspect ratio ~ 155:243
* */

@Composable
internal fun MiniCard(
    card: Card,
    isHidden: Boolean,
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
        if (isHidden) {
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
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = card.rank.symbol,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        color = when (card.suite.color) {
                            CardColor.RED -> Card.redColor
                            CardColor.BLACK -> when (cardTheme.isDark) {
                                true -> Color.White
                                false -> Color.Black
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

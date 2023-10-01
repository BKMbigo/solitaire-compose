package com.github.bkmbigo.solitaire.presentation.solitaire.components.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.github.bkmbigo.solitaire.game.solitaire.configuration.SolitaireCardsPerDeal
import com.github.bkmbigo.solitaire.game.solitaire.providers.RandomSolitaireGameProvider
import com.github.bkmbigo.solitaire.game.solitaire.providers.SolitaireGameProvider
import com.github.bkmbigo.solitaire.game.solitaire.providers.VeryEasySolitaireGameProvider
import com.github.bkmbigo.solitaire.presentation.core.layouts.DialogLayout
import com.github.bkmbigo.solitaire.presentation.core.locals.cardtheme.LocalCardTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SolitaireGameCreationDialog(
    onConfigurationSet: (SolitaireGameProvider, SolitaireCardsPerDeal) -> Unit,
    onDismissRequest: () -> Unit
) {
    val cardTheme = LocalCardTheme.current

    var provider by remember { mutableStateOf<SolitaireGameProvider?>(null) }
    var cardsPerDeal by remember { mutableStateOf(SolitaireCardsPerDeal.ONE) }

    DialogLayout(
        onDismissRequest = onDismissRequest,
    ) {
        Text(
            text = "Select a difficulty:"
        )

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedCard(
                modifier = Modifier
                    .weight(1f, true)
                    .padding(horizontal = 4.dp),
                onClick = {
                    provider = VeryEasySolitaireGameProvider
                },
                colors = CardDefaults.outlinedCardColors(
                    containerColor = if (provider == VeryEasySolitaireGameProvider) MaterialTheme.colorScheme.primaryContainer else cardTheme.gameBackground,
                    contentColor = if (provider == VeryEasySolitaireGameProvider) MaterialTheme.colorScheme.onPrimaryContainer else Color.Black
                ),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = "Easy",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            OutlinedCard(
                modifier = Modifier
                    .weight(1f, true)
                    .padding(horizontal = 4.dp),
                onClick = {
                    provider = RandomSolitaireGameProvider
                },
                colors = CardDefaults.outlinedCardColors(
                    containerColor = if (provider == RandomSolitaireGameProvider) MaterialTheme.colorScheme.primaryContainer else cardTheme.gameBackground,
                    contentColor = if (provider == RandomSolitaireGameProvider) MaterialTheme.colorScheme.onPrimaryContainer else Color.Black
                ),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = "Random",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }

        Text(
            text = "Choose Configuration"
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            OutlinedCard(
                modifier = Modifier
                    .weight(1f, true)
                    .padding(horizontal = 4.dp),
                onClick = {
                    cardsPerDeal = SolitaireCardsPerDeal.ONE
                },
                colors = CardDefaults.outlinedCardColors(
                    containerColor = if (cardsPerDeal == SolitaireCardsPerDeal.ONE) MaterialTheme.colorScheme.primaryContainer else cardTheme.gameBackground,
                    contentColor = if (cardsPerDeal == SolitaireCardsPerDeal.ONE) MaterialTheme.colorScheme.onPrimaryContainer else Color.Black
                ),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = "One Card Per Deal",
                    modifier = Modifier.padding(4.dp),
                    textAlign = TextAlign.Center
                )
            }

            OutlinedCard(
                modifier = Modifier
                    .weight(1f, true)
                    .padding(horizontal = 4.dp),
                onClick = {
                    cardsPerDeal = SolitaireCardsPerDeal.THREE
                },
                colors = CardDefaults.outlinedCardColors(
                    containerColor = if (cardsPerDeal == SolitaireCardsPerDeal.THREE) MaterialTheme.colorScheme.primaryContainer else cardTheme.gameBackground,
                    contentColor = if (cardsPerDeal == SolitaireCardsPerDeal.THREE) MaterialTheme.colorScheme.onPrimaryContainer else Color.Black
                ),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = "Three Card Per Deal",
                    modifier = Modifier.padding(4.dp),
                    textAlign = TextAlign.Center
                )
            }


        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = {
                    provider?.let { provider ->
                        onConfigurationSet(provider, cardsPerDeal)
                    }
                },
                enabled = provider != null,
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = "Continue"
                )
            }
        }
    }
}

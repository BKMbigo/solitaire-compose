package com.github.bkmbigo.solitaire.presentation.ui.core.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.bkmbigo.solitaire.presentation.ui.core.locals.cardtheme.LocalCardTheme

@Composable
expect fun StartScreen()

expect object HomeScreen


@Composable
fun HomeScreenContent(
    navigateToSolitaire: () -> Unit,
) {
    val cardTheme = LocalCardTheme.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = cardTheme.gameBackground
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = { /* TODO: not yet implemented */ }
                ) {
                    Icon(
                        imageVector = Icons.Filled.HelpOutline,
                        contentDescription = null
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Welcome to Solitaire",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = cardTheme.appFont
                )

                Spacer(Modifier.height(8.dp))

                Text("Which game would you like to play?")

                Spacer(Modifier.height(8.dp))

                LazyRow(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    item {
                        GameItem(
                            gameName = "Solitaire",
                            onGameOpened = navigateToSolitaire
                        )
                    }
                }
            }

            Row {

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GameItem(
    gameName: String,
    onGameOpened: () -> Unit
) {
    val cardTheme = LocalCardTheme.current

    OutlinedCard(
        modifier = Modifier
            .size(250.dp)
            .padding(all = 4.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.outlinedCardElevation(
            defaultElevation = 12.dp,
            hoveredElevation = 4.dp
        ),
        colors = CardDefaults.outlinedCardColors(containerColor = cardTheme.gameBackground),
        onClick = {
            onGameOpened()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .weight(1f, true)
                    .fillMaxWidth()
            )

            Text(
                text = gameName,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

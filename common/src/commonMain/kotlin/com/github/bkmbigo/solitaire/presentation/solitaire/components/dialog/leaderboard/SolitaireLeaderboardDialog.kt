package com.github.bkmbigo.solitaire.presentation.solitaire.components.dialog.leaderboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.bkmbigo.solitaire.data.SolitaireScore
import com.github.bkmbigo.solitaire.presentation.core.locals.cardtheme.LocalCardTheme
import com.github.bkmbigo.solitaire.presentation.solitaire.screens.state.SolitaireLeaderboardDialogState
import com.github.bkmbigo.solitaire.presentation.solitaire.screens.state.SolitaireLeaderboardListState

@Composable
fun SolitaireLeaderboardDialog(
    state: SolitaireLeaderboardDialogState,
    onAction: (SolitaireLeaderboardDialogAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val cardTheme = LocalCardTheme.current

    var filterLeaderboardState by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }

    Surface(
        modifier = modifier
            .widthIn(min = 300.dp),
        shape = RoundedCornerShape(16.dp),
        color = cardTheme.gameBackground,
        shadowElevation = 16.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Top Leaderboard",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f, true),
                    fontFamily = cardTheme.appFont,
                    fontSize = 18.sp,
                    color = Color.White
                )

                IconButton(
                    onClick = {
                        onAction(SolitaireLeaderboardDialogAction.DismissDialog)
                    },
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = Color.White
                    ),
                    modifier = Modifier.align(Alignment.Top)
                ) {
                    Icon(
                        imageVector = Icons.Default.Cancel,
                        contentDescription = null
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = filterLeaderboardState,
                    onValueChange = {
                        filterLeaderboardState = it
                    },
                    label = {
                        Text(
                            text = "Filter by Custom Leaderboard",
                            color = Color.White
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    trailingIcon = {
                        if (filterLeaderboardState.text.isNotBlank()) {
                            IconButton(
                                onClick = {
                                    filterLeaderboardState = TextFieldValue("")
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                        }
                    }
                )

                Spacer(modifier = Modifier.width(4.dp))

                Button(
                    onClick = {
                        onAction(SolitaireLeaderboardDialogAction.FilterByLeaderboard(filterLeaderboardState.text))
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.FilterList,
                        contentDescription = null,
                        tint = Color.White
                    )

                    Spacer(modifier = Modifier.width(2.dp))

                    Text(
                        text = "Apply Filter"
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            when (state.leaderboard) {
                SolitaireLeaderboardListState.Error -> {
                    Column(
                        modifier = Modifier.weight(1f, true),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Error,
                            contentDescription = null,
                            tint = Color.Red
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Error while obtaining list",
                            color = Color.White
                        )
                    }
                }

                SolitaireLeaderboardListState.Loading -> {
                    Box(
                        modifier = Modifier
                            .weight(1f, true)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }

                is SolitaireLeaderboardListState.Success -> {
                    LeaderboardList(
                        list = (state.leaderboard as SolitaireLeaderboardListState.Success).list,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f, true)
                            .padding(horizontal = 4.dp)
                    )
                }
            }

            if (state is SolitaireLeaderboardDialogState.LeaderboardAndScore) {

                Spacer(modifier = Modifier.height(4.dp))

                LeaderboardEntry(
                    isEntryAllowed = state.isEntryAllowed,
                    isEntryLoading = false,
                    onSubmitScore = { playerName, leaderboardName ->
                        onAction(
                            SolitaireLeaderboardDialogAction.SubmitLeaderboardScore(
                                playerName = playerName,
                                customLeaderboardName = leaderboardName,
                                platform = state.platform
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min)
                        .padding(horizontal = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
        }

    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun LeaderboardEntry(
    isEntryAllowed: Boolean = true,
    isEntryLoading: Boolean,
    onSubmitScore: (playerName: String, leaderboardName: String?) -> Unit,
    modifier: Modifier = Modifier
) {

    var playerNameState by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }

    var leaderboardTextState by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }

    FlowRow(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = playerNameState,
            onValueChange = {
                playerNameState = it
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            label = {
                Text(
                    text = "Name",
                    color = Color.White
                )
            },
            enabled = isEntryAllowed
        )

        Spacer(modifier = Modifier.width(4.dp))

        OutlinedTextField(
            value = leaderboardTextState,
            onValueChange = {
                leaderboardTextState = it
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            label = {
                Text(
                    text = "Leaderboard",
                    color = Color.White
                )
            },
            enabled = isEntryAllowed
        )

        Spacer(modifier = Modifier.width(2.dp))

        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                modifier = Modifier,
                onClick = {
                    onSubmitScore(
                        playerNameState.text,
                        leaderboardTextState.text.ifBlank { null }
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(5.dp),
                enabled = playerNameState.text.isNotBlank() && isEntryAllowed && !isEntryLoading
            ) {
                if (isEntryLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(12.dp)
                    )

                    Spacer(modifier = Modifier.width(2.dp))
                }

                Text(
                    text = "Submit Score"
                )
            }
        }
    }
}

@Composable
private fun LeaderboardList(
    list: List<SolitaireScore>,
    modifier: Modifier = Modifier
) {
    val cardTheme = LocalCardTheme.current

    Column(
        modifier = modifier
    ) {
        ProvideTextStyle(value = TextStyle(Color.White)) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Name",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f, true)
                )

                Text(
                    text = "Leaderboard",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth(0.25f)
                )

                Text(
                    text = "Score",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth(0.25f)
                )
            }

            Divider(modifier = Modifier.fillMaxWidth())

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(list) { item ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = item.playerName,
                                fontFamily = cardTheme.appFont,
                                modifier = Modifier
                                    .weight(1f, true),
                                fontSize = 17.sp
                            )

                            Spacer(modifier = Modifier.width(2.dp))

                            if (item.leaderboard != null) {
                                Text(
                                    text = item.leaderboard,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth(0.25f),
                                    fontWeight = FontWeight.Light
                                )
                            } else {
                                Spacer(
                                    modifier = Modifier
                                        .fillMaxWidth(0.25f),
                                )
                            }

                            Spacer(modifier = Modifier.width(2.dp))

                            Text(
                                text = item.score.toString(),
                                modifier = Modifier
                                    .fillMaxWidth(0.25f),
                                fontFamily = cardTheme.appFont,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(2.dp))

                        Divider(
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

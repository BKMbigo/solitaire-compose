package com.github.bkmbigo.solitaire.presentation.ui.game.card.solitaire.components.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.github.bkmbigo.solitaire.presentation.ui.core.layouts.DialogLayout

@Composable
fun SolitaireGameDrawnDialog(
    onContinue: () -> Unit,
    onCreateNewGame: () -> Unit,
    onUndoLastMove: () -> Unit,
    onDismissRequest: () -> Unit
) {

    DialogLayout(
        onDismissRequest = onDismissRequest
    ) {
        Text(
            text = "The game has detected a possible draw. What action would you like to take?",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(2.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Button(
                onClick = onContinue,
                shape = RoundedCornerShape(5.dp)
            ) {
                Text(
                    text = "Continue"
                )
            }

            Button(
                onClick = onCreateNewGame,
                shape = RoundedCornerShape(5.dp)
            ) {
                Text(
                    text = "Start New Game"
                )
            }

            Button(
                onClick = onUndoLastMove,
                shape = RoundedCornerShape(5.dp)
            ) {
                Text(
                    text = "Undo Last Move"
                )
            }
        }
    }
}

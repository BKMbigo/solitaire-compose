package com.github.bkmbigo.solitaireanimation.presentation.components.dialog

import androidx.compose.runtime.Composable
import com.github.bkmbigo.solitaireanimation.presentation.layouts.DialogLayout

@Composable
fun GameDialog(
    isDismissible: Boolean,
    onDismissRequest: (() -> Unit)?,
    onStartNewGame: () -> Unit,
    onGoToInstructions: () -> Unit,
    onOpenSettings: () -> Unit
) {

    DialogLayout(
        onDismissRequest = onDismissRequest
    ) {

    }
}
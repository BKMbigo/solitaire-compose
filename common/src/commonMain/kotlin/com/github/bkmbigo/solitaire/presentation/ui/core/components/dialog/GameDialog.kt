package com.github.bkmbigo.solitaire.presentation.ui.core.components.dialog

import androidx.compose.runtime.Composable
import com.github.bkmbigo.solitaire.presentation.ui.core.layouts.DialogLayout

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

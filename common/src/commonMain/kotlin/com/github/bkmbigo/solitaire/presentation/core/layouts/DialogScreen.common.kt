package com.github.bkmbigo.solitaire.presentation.core.layouts

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable

/*  This layout provides a dialog for web-based screens as it is not yet available as at Compose Version 1.4.0
*   On other targets, it defaults to a native dialog implementation */
@Composable
expect fun DialogScreen(
    isDialogOpen: Boolean,
    onDismissRequest: () -> Unit,
    dialog: @Composable () -> Unit,
    content: @Composable BoxScope.() -> Unit
)

package com.github.bkmbigo.solitaireanimation.presentation.layouts

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeDialog
import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
actual fun DialogScreen(
    isDialogOpen: Boolean,
    onDismissRequest: () -> Unit,
    dialog: @Composable () -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val animatedBlur by animateDpAsState(
            targetValue = if (isDialogOpen) 8.dp else 0.dp,
            animationSpec = tween(
                durationMillis = 3000,
                easing = LinearEasing
            ),
            label = "BlurAnimation"
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .blur(
                    animatedBlur
                ),
            content = content
        )

        if (isDialogOpen) {
            Dialog(
                visible = false,
                onPreviewKeyEvent = { false },
                onKeyEvent = { false },
                create = {
                     ComposeDialog()
                },
                dispose = {
                    onDismissRequest()
                },
                update = { /* no-op */ },
                content = {
                    dialog()
                }
            )
        }
    }
}
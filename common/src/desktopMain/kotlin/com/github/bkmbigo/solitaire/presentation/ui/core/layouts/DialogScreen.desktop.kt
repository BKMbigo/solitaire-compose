package com.github.bkmbigo.solitaire.presentation.ui.core.layouts

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeDialog
import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogWindow

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
                .then(
                    if(isDialogOpen) {
                        Modifier.blur(animatedBlur)
                    } else {
                        Modifier
                    }
                ),
            content = content
        )

        if (isDialogOpen) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        onDismissRequest()
                    }
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 24.dp, vertical = 32.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {}
                ) {
                    dialog()
                }
            }
        }
    }
}

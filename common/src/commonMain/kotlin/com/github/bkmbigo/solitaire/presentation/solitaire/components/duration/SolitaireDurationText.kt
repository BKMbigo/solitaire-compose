package com.github.bkmbigo.solitaire.presentation.solitaire.components.duration

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.time.Duration

@Composable
fun SolitaireDurationText(
    duration: Duration,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
    ) {
        Text(
            text = duration.toComponents { hours, minutes, seconds, _ ->
                val hasHours = hours > 0
                val hasMinutes = minutes > 0
                val hasSeconds = seconds > 0

                buildString {
                    if (hasHours) {
                        append("$hours Hours ")
                    }

                    if (hasMinutes || hasHours) {
                        append("$minutes Minutes ")
                    }

                    if (hasSeconds || hasHours || hasMinutes) {
                        append("$seconds Seconds")
                    }

                }
            },
            modifier = Modifier.padding(horizontal = 4.dp)
        )
    }
}

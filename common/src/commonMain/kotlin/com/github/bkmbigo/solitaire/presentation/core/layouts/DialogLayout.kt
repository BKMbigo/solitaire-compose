package com.github.bkmbigo.solitaire.presentation.core.layouts

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.bkmbigo.solitaire.presentation.core.locals.cardtheme.LocalCardTheme

/* The default Layout for all dialogs in the project*/
@Composable
fun DialogLayout(
    onDismissRequest: (() -> Unit)?,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    val cardTheme = LocalCardTheme.current

    Surface(
        modifier = modifier
            .widthIn(min = 300.dp)
            .width(IntrinsicSize.Min)
            .height(IntrinsicSize.Max),
        shape = RoundedCornerShape(16.dp),
        color = cardTheme.gameBackground,
        shadowElevation = 16.dp
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Solitaire",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f, true),
                    fontFamily = cardTheme.appFont,
                    fontSize = 18.sp
                )

                onDismissRequest?.let {
                    IconButton(
                        onClick = onDismissRequest,
                        modifier = Modifier.align(Alignment.Top)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Cancel,
                            contentDescription = null
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                content()

                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

package com.devautro.financetracker.feature_moneySource.presentation.money_sorces.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.devautro.financetracker.ui.theme.FinanceTrackerTheme

@Composable
fun DeleteDialog(
    onConfirmClick: () -> Unit,
    onDismissClick: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismissClick,
        confirmButton = {
            TextButton(onClick = onConfirmClick) {
                Text(
                    text = "Delete",
                    color = MaterialTheme.colorScheme.errorContainer
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissClick) {
                Text(text = "Cancel", color = MaterialTheme.colorScheme.primary)
            }
        },
        title = {
            Text(text = "Delete")
        },
        text = {
            Text(
                buildAnnotatedString {
                    append("Are you sure you want to ")

                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.errorContainer)) {
                        append("delete ")
                    }
                    append("this data? You will ")
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("not ")
                    }
                    append("restore it anymore!")
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        textContentColor = Color.Black,
        titleContentColor = Color.Black
    )
}

@Preview
@Composable
fun DeleteDialogPreview() {
    FinanceTrackerTheme {
        DeleteDialog(
            onConfirmClick = {},
            onDismissClick = {}
        )
    }
}
package com.devautro.financetracker.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devautro.financetracker.ui.theme.AccentBlue
import com.devautro.financetracker.ui.theme.CancelButton
import com.devautro.financetracker.ui.theme.FinanceTrackerTheme

@Composable
fun DualOptionButtonsRow(
    dismissText: String,
    approveText: String,
    onDismiss: () -> Unit,
    onApprove: () -> Unit,
    isApproveEnabled: Boolean = true
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(bottom = 20.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            modifier = Modifier
                .weight(0.5f)
                .padding(horizontal = 20.dp),
            onClick = {
                onDismiss()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = CancelButton,
                contentColor = MaterialTheme.colorScheme.onBackground
            ),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground)
        ) {
            Text(text = dismissText)
        }
        Button(
            modifier = Modifier
                .weight(0.5f)
                .padding(horizontal = 15.dp),
            enabled = isApproveEnabled,
            onClick = {
                onApprove()
                onDismiss()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground
            ),
            border = BorderStroke(1.dp, AccentBlue)
        ) {
            Text(text = approveText)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonsRowPreview() {
    FinanceTrackerTheme {
        Row(
            modifier = Modifier.background(MaterialTheme.colorScheme.primary)
        ) {
            DualOptionButtonsRow(
                dismissText = "Cancel",
                approveText = "Add",
                onDismiss = {},
                onApprove = {}
            )
        }
    }
}
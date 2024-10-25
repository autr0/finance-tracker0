package com.devautro.financetracker.feature_payment.presentation.add_payment.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.devautro.financetracker.R
import com.devautro.financetracker.ui.theme.AccentBlue
import com.devautro.financetracker.ui.theme.OnBackgroundColor
import com.devautro.financetracker.ui.theme.secondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerItem(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text(text = stringResource(id = R.string.ok), color = AccentBlue)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(id = R.string.cancel), color = AccentBlue)
            }
        },

        ) {
        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.colors(
                containerColor = secondary,
                titleContentColor = OnBackgroundColor,
                headlineContentColor = OnBackgroundColor,
                todayContentColor = AccentBlue,
                todayDateBorderColor = AccentBlue,
                selectedDayContentColor = secondary,
                selectedDayContainerColor = AccentBlue,
                dividerColor = AccentBlue,

            )
        )
    }
}
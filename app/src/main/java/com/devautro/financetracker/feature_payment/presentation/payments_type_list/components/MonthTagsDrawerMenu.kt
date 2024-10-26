package com.devautro.financetracker.feature_payment.presentation.payments_type_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.devautro.financetracker.core.util.Const
import com.devautro.financetracker.ui.theme.AccentBlue
import com.devautro.financetracker.ui.theme.OnBackgroundColor
import com.devautro.financetracker.ui.theme.secondary

@Composable
fun MonthTagsDrawerMenu(
    modifier: Modifier = Modifier, // pass the back color via modifier
    isExpanded: Boolean,
    onDismissMenu: () -> Unit,
    selectedItem: String,
    onSelectedItem: (String) -> Unit
) {
    DropdownMenu(
        expanded = isExpanded,
        onDismissRequest = {
            onDismissMenu()
        },
        scrollState = rememberScrollState(),
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.3f)
            .background(secondary)
    ) {
        Const.months.forEach { month ->

            DropdownMenuItem(
                onClick = {
                    onDismissMenu()
                    onSelectedItem(month.value) // pass the en month string
                },
                text = {
                    Text(
                        text = stringResource(id = month.key), // display month in corresponding language
                        color = if (selectedItem == month.value) AccentBlue else OnBackgroundColor
                    )
                }
            )
        }

    }
}
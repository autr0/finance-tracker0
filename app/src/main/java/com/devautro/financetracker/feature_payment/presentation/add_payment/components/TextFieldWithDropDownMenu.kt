package com.devautro.financetracker.feature_payment.presentation.add_payment.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.devautro.financetracker.feature_moneySource.domain.model.MoneySource
import com.devautro.financetracker.ui.theme.AccentBlue
import com.devautro.financetracker.ui.theme.BackgroundColor
import com.devautro.financetracker.ui.theme.OnBackgroundColor
import com.devautro.financetracker.ui.theme.secondary

@Composable
fun <T> TextFieldWithDropDownMenu(
    modifier: Modifier = Modifier,
    itemsList: List<T>,
    isExpanded: Boolean,
    onDismissMenu: () -> Unit, // change to () -> Unit, 'cause it will be event
    selectedItem: T?,
    onSelectedItemChange: (T) -> Unit, // leave as it, need to give that new value to vm.onEvent(AddPaymentEvent.monthTag)
    labelText: String,
    trailingIcon: @Composable (() -> Unit),
    supportingText: @Composable() (() -> Unit)? = null
) {
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    Column {
        TextFieldComponent(
            modifier = modifier
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                }, // - maybe wrong order of the modifier's functions -> width before positioned?!
            value = when (selectedItem) {
                is String -> selectedItem
                is MoneySource -> selectedItem.name
                else -> ""
            },
            onValueChange = { /* read-only! */ },
            labelText = labelText,
            trailingIcon = trailingIcon,
            readOnly = true,
            supportingText = supportingText
        )

        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { onDismissMenu() },
            scrollState = rememberScrollState(),
            modifier = Modifier
                .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
                .fillMaxHeight(0.3f).background(BackgroundColor)
        ) {
            itemsList.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        onDismissMenu()
                        onSelectedItemChange(item) // we have to pass a MoneySource, not id :(
                    },
                    text = {
                        Text(
                            text = when (item) {
                                is String -> item
                                is MoneySource -> item.name
                                else -> ""
                            },
                            fontSize = 18.sp,
                            color = if (item == selectedItem) AccentBlue else OnBackgroundColor,
                        )
                    }
                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun TextFieldWithMenuPreview() {
//    FinanceTrackerTheme {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(MaterialTheme.colorScheme.background),
//            horizontalAlignment = Alignment.CenterHorizontally,
//        ) {
//            TextFieldWithDropDownMenu(
//                itemsList = listOf("January", "February", "March", "April", "May"),
//                isExpanded = true,
//                onIsExpandedChange = { newValue ->
//
//                }
//            )
//        }
//    }
//}
package com.devautro.financetracker.feature_payment.presentation.add_payment

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devautro.financetracker.core.presentation.components.DualOptionButtonsRow
import com.devautro.financetracker.core.util.Const
import com.devautro.financetracker.feature_payment.presentation.add_payment.components.DatePickerItem
import com.devautro.financetracker.feature_payment.presentation.add_payment.components.TextFieldComponent
import com.devautro.financetracker.feature_payment.presentation.add_payment.components.TextFieldWithDropDownMenu
import com.devautro.financetracker.feature_payment.presentation.add_payment.components.TextFieldWithDropDownMenuMoneySource
import com.devautro.financetracker.feature_payment.util.convertMillisToDate
import com.devautro.financetracker.feature_payment.util.isConvertibleToDouble
import com.devautro.financetracker.ui.theme.AccentBlue
import com.devautro.financetracker.ui.theme.BackgroundColor
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPaymentBottomSheet(
    viewModel: AddPaymentViewModel = hiltViewModel(),
    sheetState: SheetState,
    navigateBack: () -> Unit
) {
    // for auto navigate to next textField & hide keyboard
    val amountFieldFocusRequester = remember { FocusRequester() }
    val monthTagFieldFocusRequester = remember { FocusRequester() }
//    val keyboardController = LocalSoftwareKeyboardController.current
//    val focusManager = LocalFocusManager.current

    val data by viewModel.paymentData.collectAsStateWithLifecycle()
    val state by viewModel.paymentState.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.sideEffects.collectLatest { effect ->
            when (effect) {
                is AddPaymentSideEffects.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(message = effect.message)
                }
                is AddPaymentSideEffects.AddButton -> navigateBack() // navigateUp()
                is AddPaymentSideEffects.CancelButton -> navigateBack() // navigateUp()
            }
        }
    }

    ModalBottomSheet(
        onDismissRequest = navigateBack,
        sheetState = sheetState,
        modifier = Modifier.fillMaxSize(),
        containerColor = BackgroundColor
    ) {

        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ) { contentPadding ->
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .fillMaxSize()
                    .padding(contentPadding)
                    .verticalScroll(state = rememberScrollState()), // for horizontal orientation?
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextFieldComponent(
                    value = if (data.date == null) "" else convertMillisToDate(data.date!!),
                    onValueChange = { },
                    labelText = "Date",
                    trailingIcon = {
                        IconButton(onClick = {
                            viewModel.onEvent(AddPaymentEvent.DateIconClick)
                        }) {
                            Icon(
                                imageVector = Icons.Default.CalendarMonth,
                                contentDescription = "date",
                                tint = AccentBlue
                            )
                        }
                    },
                    readOnly = true,
                    supportingText = {
                        Text(
                            text = "Date should be picked!",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                )
                TextFieldComponent(
                    value = data.description,
                    onValueChange = { viewModel.onEvent(AddPaymentEvent.EnteredDescription(it)) },
                    labelText = "Description",
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
//                            keyboardController?.hide()
                            amountFieldFocusRequester.requestFocus()
                        }
                    ),
                    supportingText = {
                        Text(
                            text = "Enter description!",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                )
                TextFieldComponent(
                    modifier = Modifier.focusRequester(amountFieldFocusRequester),
                    value = state.amountInString,
                    onValueChange = { viewModel.onEvent(AddPaymentEvent.EnteredAmount(it)) },
                    labelText = "Amount",
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
//                            focusManager.clearFocus(force = true)
//                            keyboardController?.hide()
                            monthTagFieldFocusRequester.requestFocus()
                        }
                    ),
                    supportingText = {
                        Text(
                            text = "Enter amount!",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                )

                TextFieldWithDropDownMenu(
                    modifier = Modifier.focusRequester(monthTagFieldFocusRequester),
                    itemsList = Const.months,
                    isExpanded = state.isMonthTagMenuVisible,
                    onDismissMenu = { viewModel.onEvent(AddPaymentEvent.DismissMonthTagMenu) },
                    selectedItem = data.monthTag,
                    onSelectedItemChange = { selectedMonth ->
                        viewModel.onEvent(AddPaymentEvent.MonthTagSelected(selectedMonth))
                    },
                    labelText = "Month Tag",
                    trailingIcon = {
                        IconButton(onClick = {
                            viewModel.onEvent(event = AddPaymentEvent.MonthTagIconClick)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Tag,
                                contentDescription = "tag",
                                tint = AccentBlue
                            )
                        }
                    },
                    supportingText = {
                        Text(
                            text = "Choose the month tag!",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                )
                TextFieldWithDropDownMenuMoneySource(
                    itemsList = state.moneySourceList,
                    isExpanded = state.isMoneySourceMenuVisible,
                    onDismissMenu = { viewModel.onEvent(AddPaymentEvent.DismissMoneySourceMenu) },
                    selectedItem = state.selectedMoneySource,
                    onSelectedItemChange = { newMoneySource ->
                        viewModel.onEvent(AddPaymentEvent.MoneySourceSelected(newMoneySource))
                    },
                    labelText = "Money source",
                    trailingIcon = {
                        IconButton(onClick = {
                            viewModel.onEvent(AddPaymentEvent.MoneySourceIconClick)
                        }) {
                            Icon(
                                imageVector = Icons.Default.AccountBalanceWallet,
                                contentDescription = "moneySource",
                                tint = AccentBlue
                            )
                        }
                    },
                    leadingIcon = {
                        IconButton(onClick = {
                            viewModel.onEvent(AddPaymentEvent.ClearChosenMoneySource)
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = "moneySource clear",
                                tint = AccentBlue
                            )
                        }
                    },
                    supportingText = {
                        Text(
                            text = "You can add your Money Source here",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(top = 20.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.onBackground
                    ),
                    border = BorderStroke(1.dp, AccentBlue)
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.weight(0.8f),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = !data.isExpense,
                                onCheckedChange = {
                                    viewModel.onEvent(AddPaymentEvent.CheckBoxSelected(isExpense = false))
                                },
                                colors = CheckboxDefaults.colors(
                                    checkmarkColor = BackgroundColor,
                                    checkedColor = AccentBlue
                                )
                            )
                            Text(text = "Income")
                        }
                        Row(
                            modifier = Modifier.weight(1f),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = data.isExpense,
                                onCheckedChange = {
                                    viewModel.onEvent(AddPaymentEvent.CheckBoxSelected(isExpense = true))
                                },
                                colors = CheckboxDefaults.colors(
                                    checkmarkColor = BackgroundColor,
                                    checkedColor = AccentBlue
                                )
                            )
                            Text(text = "Expense")
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
                Spacer(modifier = Modifier.weight(1f))
                DualOptionButtonsRow(
                    dismissText = "Cancel",
                    approveText = "Add",
                    onDismiss = { viewModel.onEvent(AddPaymentEvent.CancelButtonClick) },
                    onApprove = { viewModel.onEvent(AddPaymentEvent.AddButtonClick) },
                    isApproveEnabled = data.date != null && data.monthTag.isNotBlank() && data.amountNew != null &&
                            data.description.isNotBlank() && state.amountInString.isConvertibleToDouble()
                )

                if (state.isDatePickerVisible) {
                    DatePickerItem(
                        onDateSelected = { dateInMillis ->
                            dateInMillis?.let { millis ->
                                viewModel.onEvent(AddPaymentEvent.DateSelected(millis))
                            }
                        },
                        onDismiss = { viewModel.onEvent(AddPaymentEvent.DismissDatePicker) }
                    )
                }
            }
        }

    }
}


//@OptIn(ExperimentalMaterial3Api::class)
//@Preview(showBackground = true)
//@Composable
//fun BottomSheetContentPreview() {
//    FinanceTrackerTheme {
//        AddPaymentBottomSheet(
//            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
//            navigateBack = {}
//        )
//    }
//}
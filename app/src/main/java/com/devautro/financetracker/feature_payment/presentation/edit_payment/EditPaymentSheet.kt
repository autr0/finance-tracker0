package com.devautro.financetracker.feature_payment.presentation.edit_payment

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devautro.financetracker.R
import com.devautro.financetracker.core.presentation.components.DualOptionButtonsRow
import com.devautro.financetracker.feature_payment.domain.model.Payment
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
fun EditPaymentSheet(
    viewModel: EditPaymentViewModel = hiltViewModel(),
    sheetState: SheetState,
    navigateBack: () -> Unit,
    initialPayment: Payment
) {
    // for auto navigate to next textField & hide keyboard
    val amountFieldFocusRequester = remember { FocusRequester() }
    val monthTagFieldFocusRequester = remember { FocusRequester() }

    val data by viewModel.paymentData.collectAsStateWithLifecycle()
    val state by viewModel.paymentState.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.onEvent(EditPaymentEvent.UpdateInitialPayment(initialPayment = initialPayment))

        viewModel.sideEffects.collectLatest { effect ->
            when(effect) {
                is EditPaymentSideEffects.CancelButton -> navigateBack()
                is EditPaymentSideEffects.SaveButton -> navigateBack()
                is EditPaymentSideEffects.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = effect.message.asString(context)
                    )
                }
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
                    labelText = stringResource(id = R.string.date_label),
                    trailingIcon = {
                        IconButton(onClick = {
                            viewModel.onEvent(EditPaymentEvent.DateIconClick)
                        }) {
                            Icon(
                                imageVector = Icons.Default.CalendarMonth,
                                contentDescription = stringResource(id = R.string.date_icon_description),
                                tint = AccentBlue
                            )
                        }
                    },
                    readOnly = true,
                    supportingText = {
                        Text(
                            text = stringResource(id = R.string.date_supporting_text),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                )
                TextFieldComponent(
                    value = data.description,
                    onValueChange = { viewModel.onEvent(EditPaymentEvent.EnteredDescription(it)) },
                    labelText = stringResource(id = R.string.description_label),
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
                            text = stringResource(id = R.string.description_supporting_text),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                )
                TextFieldComponent(
                    modifier = Modifier.focusRequester(amountFieldFocusRequester),
                    value = state.amountInString,
                    onValueChange = { viewModel.onEvent(EditPaymentEvent.EnteredAmount(it)) },
                    labelText = stringResource(id = R.string.amount_label),
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
                            text = stringResource(id = R.string.amount_supporting_text_0),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                )

                TextFieldWithDropDownMenu(
                    modifier = Modifier.focusRequester(monthTagFieldFocusRequester),
                    isExpanded = state.isMonthTagMenuVisible,
                    onDismissMenu = { viewModel.onEvent(EditPaymentEvent.DismissMonthTagMenu) },
                    selectedItem = data.monthTag,
                    onSelectedItemChange = { selectedMonth ->
                        viewModel.onEvent(EditPaymentEvent.MonthTagSelected(selectedMonth))
                    },
                    labelText = stringResource(id = R.string.month_tag_label),
                    trailingIcon = {
                        IconButton(onClick = {
                            viewModel.onEvent(event = EditPaymentEvent.MonthTagIconClick)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Tag,
                                contentDescription = stringResource(id = R.string.month_tag_icon_description),
                                tint = AccentBlue
                            )
                        }
                    },
                    supportingText = {
                        Text(
                            text = stringResource(id = R.string.month_tag_supporting_text),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                )
                TextFieldWithDropDownMenuMoneySource(
                    itemsList = state.moneySourceList,
                    isExpanded = state.isMoneySourceMenuVisible,
                    onDismissMenu = { viewModel.onEvent(EditPaymentEvent.DismissMoneySourceMenu) },
                    selectedItem = state.selectedMoneySource,
                    onSelectedItemChange = { newMoneySource ->
                        viewModel.onEvent(EditPaymentEvent.MoneySourceSelected(newMoneySource))
                    },
                    labelText = stringResource(id = R.string.ms_name_label),
                    trailingIcon = {
                        IconButton(onClick = {
                            viewModel.onEvent(EditPaymentEvent.MoneySourceIconClick)
                        }) {
                            Icon(
                                imageVector = Icons.Default.AccountBalanceWallet,
                                contentDescription = stringResource(id = R.string.ms_icon_def_description),
                                tint = AccentBlue
                            )
                        }
                    },
                    leadingIcon = {
                        IconButton(onClick = {
                            viewModel.onEvent(EditPaymentEvent.ClearChosenMoneySource)
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = stringResource(id = R.string.ms_icon_clean_description),
                                tint = AccentBlue
                            )
                        }
                    },
                    supportingText = {
                        Text(
                            text = stringResource(id = R.string.ms_supporting_text),
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
                                    viewModel.onEvent(EditPaymentEvent.CheckBoxSelected(isExpense = false))
                                },
                                colors = CheckboxDefaults.colors(
                                    checkmarkColor = BackgroundColor,
                                    checkedColor = AccentBlue
                                )
                            )
                            Text(text = stringResource(id = R.string.income_checkbox))
                        }
                        Row(
                            modifier = Modifier.weight(1f),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = data.isExpense,
                                onCheckedChange = {
                                    viewModel.onEvent(EditPaymentEvent.CheckBoxSelected(isExpense = true))
                                },
                                colors = CheckboxDefaults.colors(
                                    checkmarkColor = BackgroundColor,
                                    checkedColor = AccentBlue
                                )
                            )
                            Text(text = stringResource(id = R.string.expense_checkbox))
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
                Spacer(modifier = Modifier.weight(1f))
                DualOptionButtonsRow(
                    dismissText = stringResource(id = R.string.cancel),
                    approveText = stringResource(id = R.string.save),
                    onDismiss = { viewModel.onEvent(EditPaymentEvent.CancelButtonClick) },
                    onApprove = { viewModel.onEvent(EditPaymentEvent.SaveButtonClick) },
                    isApproveEnabled = data.date != null && data.monthTag.isNotBlank() && data.amountNew != null &&
                            data.description.isNotBlank() && state.amountInString.isConvertibleToDouble()
                )

                if (state.isDatePickerVisible) {
                    DatePickerItem(
                        onDateSelected = { dateInMillis ->
                            dateInMillis?.let { millis ->
                                viewModel.onEvent(EditPaymentEvent.DateSelected(millis))
                            }
                        },
                        onDismiss = { viewModel.onEvent(EditPaymentEvent.DismissDatePicker) }
                    )
                }
            }
        }

    }
}
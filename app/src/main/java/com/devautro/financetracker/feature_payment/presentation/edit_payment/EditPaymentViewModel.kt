package com.devautro.financetracker.feature_payment.presentation.edit_payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devautro.financetracker.feature_moneySource.domain.model.MoneySource
import com.devautro.financetracker.feature_moneySource.domain.use_case.MoneySourceUseCases
import com.devautro.financetracker.feature_payment.domain.model.InvalidPaymentException
import com.devautro.financetracker.feature_payment.domain.model.Payment
import com.devautro.financetracker.feature_payment.domain.use_case.PaymentUseCases
import com.devautro.financetracker.feature_payment.util.formatDoubleToString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditPaymentViewModel @Inject constructor(
    private val moneySourceUseCases: MoneySourceUseCases,
    private val paymentUseCases: PaymentUseCases
) : ViewModel() {

    // UI state divided in 2 states 'cause we need to pass Payment via UseCase -->
    private val _paymentData = MutableStateFlow(Payment())
    val paymentData: StateFlow<Payment> = _paymentData.asStateFlow()

    private val _paymentState = MutableStateFlow(EditPaymentState())
    val paymentState: StateFlow<EditPaymentState> = _paymentState.asStateFlow()

    private val _sideEffects = MutableSharedFlow<EditPaymentSideEffects>()
    val sideEffects = _sideEffects.asSharedFlow()

    init {
        viewModelScope.launch {
            moneySourceUseCases.getAllMoneySourcesUseCase().collect { moneySourceList ->

                // it's unnecessary here to add 1st item if list.isEmpty()
                // because we've already checked it in the AddPaymentViewModel

                if (moneySourceList.isNotEmpty()) {
                    _paymentState.update { state ->
                        state.copy(moneySourceList = moneySourceList)
                    }
                }
            }
        }
    }

    fun onEvent(event: EditPaymentEvent) {
        when (event) {
            is EditPaymentEvent.UpdateInitialPayment -> {
                _paymentData.update { payment ->
                    payment.copy(
                        id = event.initialPayment.id,
                        date = event.initialPayment.date,
                        monthTag = event.initialPayment.monthTag,
                        description = event.initialPayment.description,
                        amountBefore = event.initialPayment.amountNew, // because of updating?
                        amountNew = event.initialPayment.amountNew,
                        isExpense = event.initialPayment.isExpense,
                        sourceId = event.initialPayment.sourceId
                    )
                }

                _paymentState.update { state ->
                    state.copy(
                        amountInString = formatDoubleToString(event.initialPayment.amountNew!!),
                        selectedMoneySource = _paymentState.value.moneySourceList.find {
                            it.id == event.initialPayment.sourceId
                        }
                    )
                }
            }

            is EditPaymentEvent.DateIconClick -> {
                _paymentState.update { state ->
                    state.copy(
                        isDatePickerVisible = true
                    )
                }
            }

            is EditPaymentEvent.DateSelected -> {
                _paymentData.update { payment ->
                    payment.copy(
                        date = event.date
                    )
                }
            }

            is EditPaymentEvent.DismissDatePicker -> {
                _paymentState.update { state ->
                    state.copy(
                        isDatePickerVisible = false
                    )
                }
            }

            is EditPaymentEvent.EnteredDescription -> {
                _paymentData.update { payment ->
                    payment.copy(
                        description = event.text
                    )
                }
            }

            is EditPaymentEvent.EnteredAmount -> {
                try {
                    _paymentData.update { payment ->
                        payment.copy(
                            amountNew = event.amount.toDouble()
                        )
                    }
                } catch (e: NumberFormatException) {
                    viewModelScope.launch {
                        _sideEffects.emit(
                            EditPaymentSideEffects.ShowSnackbar(
                                message = "Invalid amount input: ${e.message}"
                            )
                        )
                    }
                }
                _paymentState.update { state ->
                    state.copy(
                        amountInString = event.amount
                    )
                }
            }

            is EditPaymentEvent.MonthTagIconClick -> {
                _paymentState.update { state ->
                    state.copy(
                        isMonthTagMenuVisible = true
                    )
                }
            }

            is EditPaymentEvent.MonthTagSelected -> {
                _paymentData.update { payment ->
                    payment.copy(
                        monthTag = event.monthTag
                    )
                }
            }

            is EditPaymentEvent.DismissMonthTagMenu -> {
                _paymentState.update { state ->
                    state.copy(
                        isMonthTagMenuVisible = false
                    )
                }
            }

            is EditPaymentEvent.MoneySourceIconClick -> {
                _paymentState.update { state ->
                    state.copy(
                        isMoneySourceMenuVisible = true
                    )
                }
            }

            is EditPaymentEvent.ClearChosenMoneySource -> {
                _paymentData.update { payment ->
                    payment.copy(
                        sourceId = null
                    )
                }
                _paymentState.update { state ->
                    state.copy(
                        selectedMoneySource = null
                    )
                }
            }

            is EditPaymentEvent.MoneySourceSelected -> {
//                do not update sourceId because we need to
//                keep it original value until updateMoneySourceAmount

//                _paymentData.update { payment ->
//                    payment.copy(
//                        sourceId = event.moneySource.id
//                    )
//                }

                _paymentState.update { state ->
                    state.copy(
                        selectedMoneySource = state.moneySourceList.find { moneySource ->
                            moneySource == event.moneySource
                        }
                    )
                }
            }

            is EditPaymentEvent.DismissMoneySourceMenu -> {
                _paymentState.update { state ->
                    state.copy(
                        isMoneySourceMenuVisible = false
                    )
                }
            }

            is EditPaymentEvent.CheckBoxSelected -> {
                _paymentData.update { payment ->
                    payment.copy(
                        isExpense = event.isExpense
                    )
                }
            }

            is EditPaymentEvent.SaveButtonClick -> {
                viewModelScope.launch {
                    val payment = _paymentData.value
                    val selectedMs = _paymentState.value.selectedMoneySource
                    try {
                        paymentUseCases.editPaymentUseCase(
                            payment = payment
                        )
                    } catch (e: InvalidPaymentException) {
                        _sideEffects.emit(
                            EditPaymentSideEffects.ShowSnackbar(
                                message = e.message ?: "Couldn't update payment"
                            )
                        )
                        return@launch
                    }
                    try {
                        updateMoneySourceAmount(
                            payment = payment,
                            selectedMoneySource = selectedMs
                        )
                    } catch (e: Exception) {
                        _sideEffects.emit(
                            EditPaymentSideEffects.ShowSnackbar(
                                message = "Couldn't update money source amount value :("
                            )
                        )
                        return@launch
                    }
                    _sideEffects.emit(EditPaymentSideEffects.SaveButton)
                    clearSelectedData()
                }
            }

            is EditPaymentEvent.CancelButtonClick -> {
                viewModelScope.launch {
                    _sideEffects.emit(EditPaymentSideEffects.CancelButton)
                }
                clearSelectedData()
            }
        }
    }

    private fun clearSelectedData() {
        _paymentData.update { payment ->
            payment.copy(
                date = null,
                description = "",
                amountNew = null,
                monthTag = "",
                isExpense = true,
                sourceId = null
            )
        }

        _paymentState.update { state ->
            state.copy(
                amountInString = "",
                selectedMoneySource = null
            )
        }
    }

    private suspend fun updateMoneySourceAmount(
        payment: Payment,
        selectedMoneySource: MoneySource?
    ) {
//        if (payment.sourceId == selectedMoneySource?.id && payment.amountNew == payment.amountBefore) return

        val initialMoneySourceId = payment.sourceId
        val newMoneySourceId = selectedMoneySource?.id
        var moneySource = selectedMoneySource
        val correctingValue = payment.amountNew
        val initialValue = payment.amountBefore

        when {
            correctingValue == initialValue && initialMoneySourceId == newMoneySourceId -> return

            // newPaymentAmount != paymentAmountBefore && initialMsId == newMsId
            correctingValue != initialValue && initialMoneySourceId == newMoneySourceId -> {
                if (moneySource == null || correctingValue == null || initialValue == null) return
                moneySource = if (payment.isExpense) {
                    // Expense
                    moneySource.copy(
                        amount = (moneySource.amount + initialValue) - correctingValue
                    )
                } else {
                    // Income
                    moneySource.copy(
                        amount = (moneySource.amount - initialValue) + correctingValue
                    )
                }

                moneySourceUseCases.editMoneySourceUseCase(moneySource = moneySource)
            }
            // newPaymentAmount == paymentAmountBefore && initialMsId != newMsId
            correctingValue == initialValue && initialMoneySourceId != newMoneySourceId -> {
                if (correctingValue == null) return

                // newPaymentAmount != paymentAmountBefore && initialMsId(!null) != newMsId(null)
                if (newMoneySourceId == null  && selectedMoneySource == null) {
                    moneySource = moneySourceUseCases.getMoneySourceUseCase(id = initialMoneySourceId!!)
                    if (moneySource != null) {
                        moneySource = if (payment.isExpense) {
                            // Expense
                            moneySource?.copy(
                                amount = moneySource.amount + correctingValue
                            )
                        } else {
                            // Income
                            moneySource?.copy(
                                amount = moneySource.amount - correctingValue
                            )
                        }
                        moneySourceUseCases.editMoneySourceUseCase(moneySource = moneySource)
                    }
                }

                // newPaymentAmount != paymentAmountBefore && initialMsId(null) != newMsId(!null)
                if (initialMoneySourceId == null) {
                    moneySource = if (payment.isExpense) {
                        // Expense
                        moneySource?.copy(
                            amount = moneySource.amount - correctingValue
                        )
                    } else {
                        // Income
                        moneySource?.copy(
                            amount = moneySource.amount + correctingValue
                        )
                    }
                }


            }
            correctingValue != initialValue && initialMoneySourceId != newMoneySourceId -> {

            }
//            else -> return
        }


//        if (moneySource != null && correctingValue != null && initialValue != null) {
//
//            moneySource = when (payment.isExpense) {
//                true -> {
//                    val newAmountExpenseUpdated = if (correctingValue != initialValue) {
//                        (moneySource.amount + initialValue) - correctingValue
//                    } else {
//                        moneySource.amount - correctingValue
//                    }
//
//                    moneySource.copy(
//                        amount = newAmountExpenseUpdated
//                    )
//                }
//
//                false -> {
//                    val newAmountIncomeUpdated = if (correctingValue != initialValue) {
//                        (moneySource.amount - initialValue) + correctingValue
//                    } else {
//                        moneySource.amount + correctingValue
//                    }
//
//                    moneySource.copy(
//                        amount = newAmountIncomeUpdated
//                    )
//                }
//            }
//
//            moneySourceUseCases.editMoneySourceUseCase(moneySource = moneySource)
//        }
    }





}
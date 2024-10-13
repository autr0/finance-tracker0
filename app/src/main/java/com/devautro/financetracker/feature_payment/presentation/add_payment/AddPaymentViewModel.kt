package com.devautro.financetracker.feature_payment.presentation.add_payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devautro.financetracker.feature_moneySource.domain.use_case.MoneySourceUseCases
import com.devautro.financetracker.feature_payment.domain.model.InvalidPaymentException
import com.devautro.financetracker.feature_payment.domain.model.Payment
import com.devautro.financetracker.feature_payment.domain.use_case.AddPaymentUseCase
import com.devautro.financetracker.feature_payment.domain.use_case.PaymentUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPaymentViewModel @Inject constructor(
    private val paymentUseCases: PaymentUseCases,
    private val moneySourceUseCases: MoneySourceUseCases
) : ViewModel() {

    // UI state divided in 2 states 'cause we need to pass Payment via UseCase -->
    private val _paymentData = MutableStateFlow(Payment())
    val paymentData: StateFlow<Payment> = _paymentData.asStateFlow()

    private val _paymentState = MutableStateFlow(AddPaymentState())
    val paymentState: StateFlow<AddPaymentState> = _paymentState.asStateFlow()

    private val _sideEffects = MutableSharedFlow<AddPaymentSideEffects>()
    val sideEffects = _sideEffects.asSharedFlow()

    init {
        viewModelScope.launch {
            val moneySourceList = moneySourceUseCases.getAllMoneySourcesUseCase().first()
            if (moneySourceList.isNotEmpty()) {
                _paymentState.update { state ->
                    state.copy(
                        moneySourceList = moneySourceList,
//                        selectedMoneySource = moneySourceList[0] -> empty value is better ?
                    )
                }
            }
        }
    }

    fun onEvent(event: AddPaymentEvent) {
        when (event) {
            is AddPaymentEvent.DateIconClick -> {
                _paymentState.update { state ->
                    state.copy(
                        isDatePickerVisible = true
                    )
                }
            }
            is AddPaymentEvent.DateSelected -> {
                _paymentData.update { payment ->
                    payment.copy(
                        date = event.date
                    )
                }
            }
            is AddPaymentEvent.DismissDatePicker -> {
                _paymentState.update { state ->
                    state.copy(
                        isDatePickerVisible = false
                    )
                }
            }
            is AddPaymentEvent.EnteredDescription -> {
                _paymentData.update { payment ->
                    payment.copy(
                        description = event.text
                    )
                }
            }
            is AddPaymentEvent.EnteredAmount -> {
                if (event.amount.isNotBlank()) {
                    try {
                        _paymentData.update { payment ->
                            payment.copy(
                                amountNew = event.amount.toDouble()
                            )
                        }
                    } catch (e: NumberFormatException) {
                        viewModelScope.launch {
                            _sideEffects.emit(
                                AddPaymentSideEffects.ShowSnackbar(
                                    message = "Invalid amount input: ${e.message}"
                                )
                            )
                        }
                    }
                }
                _paymentState.update { state ->
                    state.copy(
                        amountInString = event.amount
                    )
                }
            }
            is AddPaymentEvent.MonthTagIconClick -> {
                _paymentState.update { state ->
                    state.copy(
                        isMonthTagMenuVisible = true
                    )
                }
            }
            is AddPaymentEvent.MonthTagSelected -> {
                _paymentData.update { payment ->
                    payment.copy(
                        monthTag = event.monthTag
                    )
                }
            }
            is AddPaymentEvent.DismissMonthTagMenu -> {
                _paymentState.update { state ->
                    state.copy(
                        isMonthTagMenuVisible = false
                    )
                }
            }
            is AddPaymentEvent.MoneySourceIconClick -> {
                _paymentState.update { state ->
                    state.copy(
                        isMoneySourceMenuVisible = true
                    )
                }
            }
            is AddPaymentEvent.MoneySourceSelected -> {
                _paymentData.update { payment ->
                    payment.copy(
                        sourceId = event.moneySource.id
                    )
                }
                _paymentState.update { state ->
                    state.copy(
                        selectedMoneySource = state.moneySourceList.find { moneySource ->
                            moneySource == event.moneySource
                        }
                    )
                }
            }
            is AddPaymentEvent.DismissMoneySourceMenu -> {
                _paymentState.update { state ->
                    state.copy(
                        isMoneySourceMenuVisible = false
                    )
                }
            }
            is AddPaymentEvent.CheckBoxSelected -> {
                _paymentData.update { payment ->
                    payment.copy(
                        isExpense = event.isExpense
                    )
                }
            }
            is AddPaymentEvent.AddButtonClick -> {
                viewModelScope.launch {
                    try {
                        paymentUseCases.addPaymentUseCase(
                            payment = _paymentData.value
                        )
                        _sideEffects.emit(AddPaymentSideEffects.AddButton)
                    } catch (e: InvalidPaymentException) {
                        _sideEffects.emit(
                            AddPaymentSideEffects.ShowSnackbar(
                                message = e.message ?: "Couldn't add payment"
                            )
                        )
                    }
                }
            }
            is AddPaymentEvent.CancelButtonClick -> {
                viewModelScope.launch {
                    _sideEffects.emit(AddPaymentSideEffects.CancelButton)
                }
            }
        }
    }

}
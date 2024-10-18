package com.devautro.financetracker.feature_payment.presentation.payments_type_list.incomes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devautro.financetracker.feature_payment.domain.model.Payment
import com.devautro.financetracker.feature_payment.domain.use_case.PaymentUseCases
import com.devautro.financetracker.feature_payment.presentation.mappers.toPayment
import com.devautro.financetracker.feature_payment.presentation.mappers.toPaymentItem
import com.devautro.financetracker.feature_payment.presentation.payments_type_list.PaymentItem
import com.devautro.financetracker.feature_payment.presentation.payments_type_list.PaymentsListEvent
import com.devautro.financetracker.feature_payment.presentation.payments_type_list.PaymentsListSideEffects
import com.devautro.financetracker.feature_payment.presentation.payments_type_list.PaymentsListState
import com.devautro.financetracker.feature_payment.util.formatDoubleToString
import com.devautro.financetracker.feature_payment.util.getYearOfTheDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IncomesViewModel @Inject constructor(
    private val paymentUseCases: PaymentUseCases,
) : ViewModel() {

    private val _paymentsState = MutableStateFlow(PaymentsListState())
    val paymentsState: StateFlow<PaymentsListState> = _paymentsState.asStateFlow()

    private val _sideEffects = MutableSharedFlow<PaymentsListSideEffects>()
    val sideEffects = _sideEffects.asSharedFlow()

    private var recentlyDeletedPayment: Payment? = null

    init {
        getInitialData()
    }

    fun onEvent(event: PaymentsListEvent) {
        when (event) {
            is PaymentsListEvent.ShowEditBottomSheet -> {
                _paymentsState.update { state ->
                    state.copy(
                        isEditBottomSheetVisible = false
                    )
                }
            }

            is PaymentsListEvent.FilterIconClick -> {
                _paymentsState.update { state ->
                    state.copy(
                        isMonthTagMenuVisible = true
                    )
                }
            }

            is PaymentsListEvent.MonthTagSelected -> {
                _paymentsState.update { state ->
                    state.copy(
                        selectedMonthTag = event.monthTag
                    )
                }
                getFilteredDataByMonthTag()
            }

            is PaymentsListEvent.CurrentYearSelected -> {
                getFilteredDataByMonthTag(
                    yearIndex = event.yearIndex
                )
            }

            is PaymentsListEvent.DismissMonthTagMenu -> {
                _paymentsState.update { state ->
                    state.copy(
                        isMonthTagMenuVisible = false
                    )
                }
            }

            is PaymentsListEvent.ClearIconCLick -> {
                _paymentsState.update { state ->
                    state.copy(
                        selectedMonthTag = ""
                    )
                }
                getInitialData()
            }

            is PaymentsListEvent.EditIconClick -> {
                _paymentsState.update { state ->
                    state.copy(
                        isEditBottomSheetVisible = true,
                        paymentForSheet = event.paymentItem.toPayment()
                    )
                }
            }

            is PaymentsListEvent.DeleteIconClick -> {
                viewModelScope.launch {
                    paymentUseCases.deletePaymentUseCase(
                        payment = event.paymentItem.toPayment()
                    )
                    recentlyDeletedPayment = event.paymentItem.toPayment()

                    _sideEffects.emit(
                        PaymentsListSideEffects.ShowSnackBar(
                            message = "Item deleted"
                        )
                    )

                }
            }

            is PaymentsListEvent.ItemRevealed -> {
                _paymentsState.update { state ->
                    state.copy(
                        paymentItemsList = updateIsRevealedField(
                            targetList = state.paymentItemsList,
                            newValue = event.isRevealed,
                            itemId = event.id
                        )
                    )
                }
            }

            is PaymentsListEvent.NavigateBack -> {
                viewModelScope.launch {
                    _sideEffects.emit(PaymentsListSideEffects.NavigateBack)
                }
            }

            PaymentsListEvent.RestorePayment -> {
                viewModelScope.launch {
                    paymentUseCases.addPaymentUseCase(
                        payment = recentlyDeletedPayment ?: return@launch
                    )
                    recentlyDeletedPayment = null
                }
            }
        }
    }


    private fun getFilteredDataByMonthTag(
        yearIndex: Int = -1
    ) {
        viewModelScope.launch {
            paymentUseCases.getIncomesUseCase().collectLatest { incomesList ->
                val uniqueYears = incomesList.map {
                    getYearOfTheDate(it.date!!)
                }.toSet().sorted().toList()

                val lastYearIndex = if (yearIndex >= 0) yearIndex else uniqueYears.lastIndex

                val filteredIncomesList = incomesList.filter {
                    it.monthTag == _paymentsState.value.selectedMonthTag &&
                            getYearOfTheDate(it.date!!) == uniqueYears[lastYearIndex]
                }
                val filteredTotalAmount = filteredIncomesList.mapNotNull { it.amountNew }.sum()

                _paymentsState.update { state ->
                    state.copy(
                        paymentItemsList = filteredIncomesList
                            .map { payment ->
                                payment.toPaymentItem()
                            },
                        totalAmount = formatDoubleToString(filteredTotalAmount),
                        paymentYearsList = uniqueYears,
                        selectedYearIndex = lastYearIndex
                    )
                }
            }
        }
    }

    private fun getInitialData() {
        viewModelScope.launch {
            paymentUseCases.getIncomesUseCase().collectLatest { incomesList ->
                val totalAmount = incomesList
                    .mapNotNull { it.amountNew }
                    .sum()

                _paymentsState.update { state ->
                    state.copy(
                        paymentItemsList = incomesList
                            .map { payment ->
                                payment.toPaymentItem()
                            },
                        totalAmount = formatDoubleToString(totalAmount)
                    )
                }
            }
        }
    }

    private fun updateIsRevealedField(
        targetList: List<PaymentItem>,
        newValue: Boolean,
        itemId: Long
    ): List<PaymentItem> {
        val mList = mutableListOf<PaymentItem>()
        mList.addAll(targetList)

        return mList.map { item ->
            if (item.id == itemId) {
                item.copy(isRevealed = newValue)
            } else {
                item
            }
        }.toList()
    }

}
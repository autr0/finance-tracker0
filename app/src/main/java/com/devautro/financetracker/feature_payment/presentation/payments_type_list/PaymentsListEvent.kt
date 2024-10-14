package com.devautro.financetracker.feature_payment.presentation.payments_type_list

sealed class PaymentsListEvent {
    data object FilterIconClick : PaymentsListEvent()
    data class MonthTagSelected(val monthTag: String, val isExpense: Boolean) : PaymentsListEvent()
    data object DismissMonthTagMenu : PaymentsListEvent()
    data class ClearIconCLick(val isExpense: Boolean) : PaymentsListEvent()
    data object NavigateBack : PaymentsListEvent()
    data class ItemRevealed(val id: Long, val isRevealed: Boolean) : PaymentsListEvent()
    data class EditIconClick(val paymentItem: PaymentItem) : PaymentsListEvent()
    data class DeleteIconClick(val paymentItem: PaymentItem) : PaymentsListEvent()
    data class GetInitialPaymentType(val isExpense: Boolean) : PaymentsListEvent()
    data object RestorePayment : PaymentsListEvent()
    data object ShowEditBottomSheet : PaymentsListEvent()
}
package com.devautro.financetracker.feature_payment.presentation.payments_type_list

sealed class PaymentsListSideEffects {
    data object NavigateBack : PaymentsListSideEffects()
    data class ShowSnackBar(val message: String) : PaymentsListSideEffects()
}
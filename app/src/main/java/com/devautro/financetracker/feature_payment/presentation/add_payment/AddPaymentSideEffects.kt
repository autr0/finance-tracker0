package com.devautro.financetracker.feature_payment.presentation.add_payment

sealed class AddPaymentSideEffects {
    data object CancelButton : AddPaymentSideEffects()
    data object AddButton : AddPaymentSideEffects()
    data class ShowSnackbar(val message: String) : AddPaymentSideEffects()
}
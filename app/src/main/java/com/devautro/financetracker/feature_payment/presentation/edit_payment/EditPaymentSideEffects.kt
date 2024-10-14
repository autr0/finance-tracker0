package com.devautro.financetracker.feature_payment.presentation.edit_payment

sealed class EditPaymentSideEffects {
    data object CancelButton : EditPaymentSideEffects()
    data object SaveButton : EditPaymentSideEffects()
    data class ShowSnackbar(val message: String) : EditPaymentSideEffects()
}
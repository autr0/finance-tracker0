package com.devautro.financetracker.feature_payment.presentation.home_screen

sealed class HomeScreenSideEffects {
    data object IncomesClick : HomeScreenSideEffects()
    data object ExpensesClick : HomeScreenSideEffects()
    data class ShowSnackbar(val message: String) : HomeScreenSideEffects()
}
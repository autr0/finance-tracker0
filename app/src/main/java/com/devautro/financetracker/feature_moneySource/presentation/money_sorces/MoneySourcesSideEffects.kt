package com.devautro.financetracker.feature_moneySource.presentation.money_sorces

sealed class MoneySourcesSideEffects {
    data class ShowSnackbar(val message: String) : MoneySourcesSideEffects()
    data object NavigateAddScreen : MoneySourcesSideEffects()
    data class NavigateEditScreen(val id: Long, val paleColor: Int) : MoneySourcesSideEffects()
}
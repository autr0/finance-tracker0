package com.devautro.financetracker.feature_moneySource.presentation.add_edit_money_source

sealed class AddEditSourceSideEffects {
    data object CancelButtonClicked : AddEditSourceSideEffects()
    data object ApproveButtonClicked : AddEditSourceSideEffects()
    data class Showsnackbar(val message: String) : AddEditSourceSideEffects()
}
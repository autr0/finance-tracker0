package com.devautro.financetracker.feature_moneySource.presentation.add_edit_money_source.mappers

import com.devautro.financetracker.feature_moneySource.domain.model.MoneySource
import com.devautro.financetracker.feature_moneySource.presentation.add_edit_money_source.AddEditMoneySourceState
import com.devautro.financetracker.feature_payment.util.formatDoubleToString

fun AddEditMoneySourceState.toMoneySource(): MoneySource {
    return MoneySource(
        id = id,
        name = name,
        amount = amount.toDouble(),
        includeInTotal = includedInTotal,
        paleColor = paleColor,
        accentColor = accentColor
    )
}

fun MoneySource.toAddEditMoneySourceState(): AddEditMoneySourceState {
    return AddEditMoneySourceState(
        id = id,
        name = name,
        amount = formatDoubleToString(amount),
        includedInTotal = includeInTotal,
        paleColor = paleColor,
        accentColor = accentColor
    )
}
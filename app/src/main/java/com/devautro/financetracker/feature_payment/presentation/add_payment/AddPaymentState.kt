package com.devautro.financetracker.feature_payment.presentation.add_payment

import com.devautro.financetracker.feature_moneySource.domain.model.MoneySource

data class AddPaymentState(
    val isDatePickerVisible: Boolean = false,
    val isMonthTagMenuVisible: Boolean = false,
    val moneySourceList: List<MoneySource> = emptyList(),
    val selectedMoneySource: MoneySource? = null,
    val isMoneySourceMenuVisible: Boolean = false,
    val amountInString: String = ""
//    val isExpenseCheckBoxPicked: Boolean = true, //--> we don't need it 'cause we have 'isExpanse' flag in Payment model
//    val isAddButtonEnabled: Boolean = false //--> it's better to transfer this to UI directly!
)

package com.devautro.financetracker.feature_payment.presentation.payments_type_list

import com.devautro.financetracker.core.util.MonthTags
import com.devautro.financetracker.feature_payment.util.convertDateToMillis

data class PaymentsListItem(
//    val paymentList: List<Payment>,
    val isRevealed: Boolean,
    val id: Long,
    val description: String,
    val amount: Double,
    val monthTag: String = MonthTags.months.random(),
    val date: Long = convertDateToMillis("12.10.24")
)

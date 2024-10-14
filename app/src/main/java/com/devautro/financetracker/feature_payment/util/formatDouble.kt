package com.devautro.financetracker.feature_payment.util

import java.text.NumberFormat
import java.util.Locale

fun formatDoubleToString(value: Double): String {
    val numberFormat = NumberFormat.getInstance(Locale.getDefault())
    numberFormat.minimumFractionDigits = 2
    numberFormat.maximumFractionDigits = 2
    return numberFormat.format(value)
}

fun toDoubleOrNull(value: String): Double? {
    return try {
        val numberFormat = NumberFormat.getInstance(Locale.getDefault())
        numberFormat.parse(value)?.toDouble()
    } catch (e: Exception) {
        null
    }
}
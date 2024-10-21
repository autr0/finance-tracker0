package com.devautro.financetracker.feature_payment.util

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.text.ParseException
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

fun String.isConvertibleToDouble(): Boolean {
    try {
        this.toDouble()
        return true
    } catch (e: NumberFormatException) {
        return false
    }
}

//fun formatDoubleToString(value: Double): String {
//    val numberFormat = DecimalFormat("#,##0.00", DecimalFormatSymbols.getInstance(Locale.getDefault()))
//    return numberFormat.format(value)
//}
//
//fun parseStringToDouble(value: String): Double? {
//    return try {
//        val numberFormat = DecimalFormat("#,##0.00", DecimalFormatSymbols.getInstance(Locale.getDefault()))
//        numberFormat.parse(value)?.toDouble()
//    } catch (e: ParseException) {
//        null
//    }
//}
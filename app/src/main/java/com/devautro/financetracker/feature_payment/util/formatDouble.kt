package com.devautro.financetracker.feature_payment.util

import java.util.Locale

fun formatDoubleToString(value: Double): String {
    val stringValue = String.format(Locale.US, "%.2f", value)

    val (integerPart, decimalPart) = stringValue.split(".")

    val modifiedIntegerPart = integerPart.reversed().chunked(3).joinToString(" ").reversed()

    return if (decimalPart.isNotEmpty()) {
        "$modifiedIntegerPart.$decimalPart"
    } else {
        modifiedIntegerPart
    }

}

fun formatStringToDouble(value: String): Double {
    val regex = Regex("^\\d+(\\.\\d{1,2})?$")
    if (!regex.matches(value)) {
        throw NumberFormatException("Invalid Input: only digits, point and up to 2 digits after allowed")
    }

    return value.toDouble()

}

fun String.isConvertibleToDouble(): Boolean {
    val regex = Regex("^\\d+(\\.\\d{1,2})?$")
    return regex.matches(this)
}
package com.devautro.financetracker.feature_payment.util

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun convertMillisToDate(millis: Long): String {
    val localDate = Instant.ofEpochMilli(millis)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yy")
    return localDate.format(formatter)
}

fun convertDateToMillis(dateString: String): Long {
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yy")
    val localDate = LocalDate.parse(dateString, formatter)
    // convert it with the start of the day value (00:00) ->
    return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
}
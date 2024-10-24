package com.devautro.financetracker.core.util

import com.devautro.financetracker.R
import com.devautro.financetracker.feature_settings.presentation.FlagItem
import com.devautro.financetracker.ui.theme.GreenAccentCard
import com.devautro.financetracker.ui.theme.GreenPaleCard
import com.devautro.financetracker.ui.theme.OrangeAccentCard
import com.devautro.financetracker.ui.theme.OrangePaleCard
import com.devautro.financetracker.ui.theme.PinkAccentCard
import com.devautro.financetracker.ui.theme.PinkPaleCard
import com.devautro.financetracker.ui.theme.PurpleAccentCard
import com.devautro.financetracker.ui.theme.PurplePaleCard

object Const {

    val months = listOf(
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December"
    )

    val filterTags = listOf("WEEK", "MONTH", "ALL")

    val sourcePaleColors = listOf(OrangePaleCard, PinkPaleCard, GreenPaleCard, PurplePaleCard)
    val sourceAccentColors = listOf(OrangeAccentCard, PinkAccentCard, GreenAccentCard, PurpleAccentCard)

    val flags = listOf(
        FlagItem(language = "English", imageResource = R.drawable.gb),
        FlagItem(language = "Spanish", imageResource = R.drawable.es),
        FlagItem(language = "Russian", imageResource = R.drawable.ru)
    )

}
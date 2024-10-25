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
        R.string.january,
        R.string.february,
        R.string.march,
        R.string.april,
        R.string.may,
        R.string.june,
        R.string.july,
        R.string.august,
        R.string.september,
        R.string.october,
        R.string.november,
        R.string.december
    )

    val filterTags = listOf(R.string.week, R.string.month, R.string.all)

    val sourcePaleColors = listOf(OrangePaleCard, PinkPaleCard, GreenPaleCard, PurplePaleCard)
    val sourceAccentColors = listOf(OrangeAccentCard, PinkAccentCard, GreenAccentCard, PurpleAccentCard)

    val flags = listOf(
        FlagItem(language = R.string.english, imageResource = R.drawable.gb),
        FlagItem(language = R.string.spanish, imageResource = R.drawable.es),
        FlagItem(language = R.string.russian, imageResource = R.drawable.ru)
    )

}
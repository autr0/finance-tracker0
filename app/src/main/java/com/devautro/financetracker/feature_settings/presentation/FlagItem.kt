package com.devautro.financetracker.feature_settings.presentation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class FlagItem(
    @StringRes val language: Int,
    @DrawableRes val imageResource: Int
)

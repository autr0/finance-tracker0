package com.devautro.financetracker.feature_settings.presentation

import androidx.annotation.DrawableRes

sealed class SettingsEvent {
    data class NewLanguageSelected(
        @DrawableRes val flagImage: Int,
        val locale: String
    ) : SettingsEvent()
    data object ShowLanguageMenu : SettingsEvent()
    data object DismissLanguageMenu : SettingsEvent()
}
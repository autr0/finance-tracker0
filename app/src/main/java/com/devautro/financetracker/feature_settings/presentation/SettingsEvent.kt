package com.devautro.financetracker.feature_settings.presentation

import androidx.annotation.DrawableRes

sealed class SettingsEvent {
    data class NewLanguageSelected(
        @DrawableRes val flagImage: Int,
        val locale: String
    ) : SettingsEvent()
    data object ShowLanguageMenu : SettingsEvent()
    data object DismissLanguageMenu : SettingsEvent()
    data object ShowDeleteDialog : SettingsEvent()
    data object DismissDeleteDialog : SettingsEvent()
    data object ApproveDeleteDialog : SettingsEvent()
    data object ClickOnPaymentsCheckBox : SettingsEvent()
    data object ClickOnMoneySourcesCheckBox : SettingsEvent()
}
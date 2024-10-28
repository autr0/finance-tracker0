package com.devautro.financetracker.feature_settings.data.repository

import com.devautro.financetracker.feature_settings.data.data_source.SettingsPreferences
import com.devautro.financetracker.feature_settings.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val settingsPreferences: SettingsPreferences
) : SettingsRepository {

    override fun getCurrentTheme(): Flow<Boolean> {
        return settingsPreferences.getCurrentTheme()
    }

    override suspend fun changeCurrentTheme(isDarkTheme: Boolean) {
        settingsPreferences.changeCurrentTheme(isDarkTheme = isDarkTheme)
    }
}
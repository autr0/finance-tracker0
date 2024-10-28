package com.devautro.financetracker.feature_settings.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getCurrentTheme(): Flow<Boolean>

    suspend fun changeCurrentTheme(isDarkTheme: Boolean)

}
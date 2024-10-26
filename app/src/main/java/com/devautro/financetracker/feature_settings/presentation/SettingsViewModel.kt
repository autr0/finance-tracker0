package com.devautro.financetracker.feature_settings.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(

) : ViewModel() {

    private val _settingsState = MutableStateFlow(SettingsState())
    val settingsState: StateFlow<SettingsState> = _settingsState.asStateFlow()


    fun onEvent(event: SettingsEvent) {
        when(event) {
            is SettingsEvent.NewLanguageSelected -> {
                _settingsState.update { state ->
                    state.copy(
                        selectedLanguageImageId = event.flagImage,
                        locale = event.locale
                    )
                }
            }
            is SettingsEvent.ShowLanguageMenu -> {
                _settingsState.update { state ->
                    state.copy(
                        isLanguageMenuShown = true
                    )
                }
            }
            is SettingsEvent.DismissLanguageMenu -> {
                _settingsState.update { state ->
                    state.copy(
                        isLanguageMenuShown = false
                    )
                }
            }
        }
    }

}
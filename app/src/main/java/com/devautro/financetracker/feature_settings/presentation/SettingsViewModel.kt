package com.devautro.financetracker.feature_settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devautro.financetracker.R
import com.devautro.financetracker.core.util.UiText
import com.devautro.financetracker.feature_moneySource.domain.use_case.MoneySourceUseCases
import com.devautro.financetracker.feature_payment.domain.use_case.PaymentUseCases
import com.devautro.financetracker.feature_settings.domain.use_case.SettingsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val paymentUseCases: PaymentUseCases,
    private val moneySourceUseCases: MoneySourceUseCases,
    private val settingsUseCases: SettingsUseCases
) : ViewModel() {

    private val _settingsState = MutableStateFlow(SettingsState())
    val settingsState: StateFlow<SettingsState> = _settingsState.asStateFlow()

    private val _sideEffects = MutableSharedFlow<SettingsSideEffects>()
    val sideEffects = _sideEffects.asSharedFlow()

    init {
        getInitialTheme()
    }

    fun onEvent(event: SettingsEvent) {
        when (event) {
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

            is SettingsEvent.SwitchTheme -> {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        settingsUseCases.changeCurrentThemeUseCase(isDarkTheme = event.isDarkTheme)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        return@launch
                    }
                }
                _settingsState.update { state ->
                    state.copy(
                        isDarkTheme = event.isDarkTheme
                    )
                }
            }

            is SettingsEvent.ShowDeleteDialog -> {
                _settingsState.update { state ->
                    state.copy(showDeleteDialog = true)
                }
            }

            is SettingsEvent.DismissDeleteDialog -> {
                _settingsState.update { state ->
                    state.copy(showDeleteDialog = false)
                }
            }

            is SettingsEvent.ApproveDeleteDialog -> {

                viewModelScope.launch(Dispatchers.IO) {
                    if (_settingsState.value.isDeletePaymentsPicked) {
                        try {
                            paymentUseCases.clearAllPaymentsUseCase()
                        } catch (e: Exception) {
                            _sideEffects.emit(
                                SettingsSideEffects.ShowSnackbar(
                                    message = UiText.StringResource((R.string.error_delete_all))
                                )
                            )
                        }
                    }
                }

                viewModelScope.launch(Dispatchers.IO) {
                    if (_settingsState.value.isDeleteMoneySourcesPicked) {
                        try {
                            moneySourceUseCases.clearAllMoneySourcesUseCase()
                        } catch (e: Exception) {
                            _sideEffects.emit(
                                SettingsSideEffects.ShowSnackbar(
                                    message = UiText.StringResource((R.string.error_delete_all))
                                )
                            )
                        }
                    }
                }

                _settingsState.update { state ->
                    state.copy(showDeleteDialog = false)
                }
            }

            is SettingsEvent.ClickOnMoneySourcesCheckBox -> {
                _settingsState.update { state ->
                    state.copy(
                        isDeleteMoneySourcesPicked = !state.isDeleteMoneySourcesPicked
                    )
                }
            }

            is SettingsEvent.ClickOnPaymentsCheckBox -> {
                _settingsState.update { state ->
                    state.copy(
                        isDeletePaymentsPicked = !state.isDeletePaymentsPicked
                    )
                }
            }
        }
    }

    /**
     * Have to use 'runBlocking' to prevent display of theme with default value
     * and after that blinking of theme switching with data from DataStore
     */
    private fun getInitialTheme() = runBlocking {
        val isDark = settingsUseCases.getCurrentThemeUseCase().first()
        _settingsState.update { state ->
            state.copy(
                isDarkTheme = isDark
            )
        }
    }

}
package com.devautro.financetracker.feature_moneySource.presentation.add_edit_money_source.add_money_source

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devautro.financetracker.feature_moneySource.domain.model.MoneySource
import com.devautro.financetracker.feature_moneySource.domain.use_case.MoneySourceUseCases
import com.devautro.financetracker.feature_moneySource.presentation.add_edit_money_source.AddEditSourceSideEffects
import com.devautro.financetracker.feature_moneySource.presentation.add_edit_money_source.AddEditMoneySourceEvent
import com.devautro.financetracker.feature_moneySource.presentation.add_edit_money_source.AddEditMoneySourceState
import com.devautro.financetracker.feature_moneySource.presentation.add_edit_money_source.mappers.toMoneySource
import com.devautro.financetracker.feature_payment.util.formatStringToDouble
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddMoneySourceViewModel @Inject constructor(
    private val moneySourceUseCases: MoneySourceUseCases
) : ViewModel() {

    private val _addMoneySourceState = MutableStateFlow(AddEditMoneySourceState())
    val addMoneySourceState: StateFlow<AddEditMoneySourceState> = _addMoneySourceState.asStateFlow()

    private val _sideEffects = MutableSharedFlow<AddEditSourceSideEffects>()
    val sideEffects = _sideEffects.asSharedFlow()

    fun onEvent(event: AddEditMoneySourceEvent) {
        when (event) {
            is AddEditMoneySourceEvent.IncludeInTotalToggled -> {
                _addMoneySourceState.update { state ->
                    state.copy(
                        includedInTotal = !state.includedInTotal
                    )
                }
            }

            is AddEditMoneySourceEvent.NewColorPicked -> {
                _addMoneySourceState.update { state ->
                    state.copy(
                        paleColor = event.paleColor,
                        accentColor = event.accentColor
                    )
                }
            }

            is AddEditMoneySourceEvent.SourceAmountChanged -> {
                try {
                    formatStringToDouble(event.amount)
                } catch (e: NumberFormatException) {
                    viewModelScope.launch {
                        _sideEffects.emit(
                            AddEditSourceSideEffects.Showsnackbar(
                                message = e.message ?: "Invalid amount input! :("
                            )
                        )
                    }
                }

                _addMoneySourceState.update { state ->
                    state.copy(
                        amount = event.amount
                    )
                }
            }

            is AddEditMoneySourceEvent.SourceNameChanged -> {
                _addMoneySourceState.update { state ->
                    state.copy(
                        name = event.name
                    )
                }
            }

            is AddEditMoneySourceEvent.ApproveButtonClicked -> {
                viewModelScope.launch {
                    try {
                        moneySourceUseCases.addMoneySourceUseCase(
                            moneySource = _addMoneySourceState.value.toMoneySource()
                        )
                    } catch (e: Exception) {
                        _sideEffects.emit(
                            AddEditSourceSideEffects.Showsnackbar(
                                message = e.message ?: "Couldn't add money source :("
                            )
                        )
                        return@launch
                    }
                    _sideEffects.emit(AddEditSourceSideEffects.ApproveButtonClicked)
                }
            }

            is AddEditMoneySourceEvent.CancelButtonClicked -> {
                viewModelScope.launch {
                    _sideEffects.emit(AddEditSourceSideEffects.CancelButtonClicked)
                }
            }
        }
    }

}
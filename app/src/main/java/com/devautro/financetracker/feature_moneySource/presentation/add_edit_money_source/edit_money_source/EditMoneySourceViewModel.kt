package com.devautro.financetracker.feature_moneySource.presentation.add_edit_money_source.edit_money_source

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.devautro.financetracker.core.presentation.navigation.EditAccount
import com.devautro.financetracker.feature_moneySource.domain.model.MoneySource
import com.devautro.financetracker.feature_moneySource.domain.use_case.MoneySourceUseCases
import com.devautro.financetracker.feature_moneySource.presentation.add_edit_money_source.AddEditMoneySourceEvent
import com.devautro.financetracker.feature_moneySource.presentation.add_edit_money_source.AddEditMoneySourceState
import com.devautro.financetracker.feature_moneySource.presentation.add_edit_money_source.AddEditSourceSideEffects
import com.devautro.financetracker.feature_payment.util.formatDoubleToString
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
class EditMoneySourceViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val moneySourceUseCases: MoneySourceUseCases
) : ViewModel() {

    private val _editMoneySourceState = MutableStateFlow(AddEditMoneySourceState())
    val editMoneySourceState: StateFlow<AddEditMoneySourceState> = _editMoneySourceState.asStateFlow()

    private val _sideEffects = MutableSharedFlow<AddEditSourceSideEffects>()
    val sideEffects = _sideEffects.asSharedFlow()

    init {
        val route = savedStateHandle.toRoute<EditAccount>()
        _editMoneySourceState.update { state ->
            state.copy(
                id = route.id
            )
        }
        getInitialDataWithId(id = route.id)
    }

    fun onEvent(event: AddEditMoneySourceEvent) {
        when(event) {
            is AddEditMoneySourceEvent.IncludeInTotalToggled -> {
                _editMoneySourceState.update { state ->
                    state.copy(
                        includedInTotal = !state.includedInTotal
                    )
                }
            }
            is AddEditMoneySourceEvent.NewColorPicked -> {
                _editMoneySourceState.update { state ->
                    state.copy(
                        paleColor = event.paleColor,
                        accentColor = event.accentColor
                    )
                }
            }
            is AddEditMoneySourceEvent.SourceAmountChanged -> {

                if (event.amount.isNotBlank()) {
                    try {
                        event.amount.toDouble()         // test this field !!!
                    } catch (e: NumberFormatException) {
                        viewModelScope.launch {
                            _sideEffects.emit(
                                AddEditSourceSideEffects.Showsnackbar(
                                    message = "Invalid amount input: ${e.message}"
                                )
                            )
                        }
                    }
                }
                _editMoneySourceState.update { state ->
                    state.copy(
                        amount = event.amount
                    )
                }
            }
            is AddEditMoneySourceEvent.SourceNameChanged -> {
                _editMoneySourceState.update { state ->
                    state.copy(
                        name = event.name
                    )
                }
            }
            is AddEditMoneySourceEvent.ApproveButtonClicked -> {
                viewModelScope.launch {
                    try {
                        moneySourceUseCases.editMoneySourceUseCase(
                            moneySource = MoneySource(
                                id = _editMoneySourceState.value.id,
                                name = _editMoneySourceState.value.name,
                                amount = _editMoneySourceState.value.amount.toDouble(),
                                paleColor = _editMoneySourceState.value.paleColor,
                                accentColor = _editMoneySourceState.value.accentColor,
                                includeInTotal = _editMoneySourceState.value.includedInTotal
                            )
                        )
                    } catch (e: Exception) {
                        _sideEffects.emit(
                            AddEditSourceSideEffects.Showsnackbar(
                                message = e.message ?: "Couldn't update money source :("
                            ))
                    } finally {
                        _sideEffects.emit(AddEditSourceSideEffects.ApproveButtonClicked)
                    }
                }
            }
            is AddEditMoneySourceEvent.CancelButtonClicked -> {
                viewModelScope.launch {
                    _sideEffects.emit(AddEditSourceSideEffects.CancelButtonClicked)
                }
            }
        }
    }

    private fun getInitialDataWithId(id: Long) {
        viewModelScope.launch {
            val moneyS = moneySourceUseCases.getMoneySourceUseCase(id = id)

            if (moneyS != null) {
                _editMoneySourceState.update { state ->
                    state.copy(
                        name = moneyS.name,
                        amount = formatDoubleToString(moneyS.amount),
                        paleColor = moneyS.paleColor,
                        accentColor = moneyS.accentColor,
                        includedInTotal = moneyS.includeInTotal
                    )
                }
            } else {
                _sideEffects.emit(AddEditSourceSideEffects.Showsnackbar(
                    message = "Error: couldn't find this item :("
                ))
            }
        }
    }
}
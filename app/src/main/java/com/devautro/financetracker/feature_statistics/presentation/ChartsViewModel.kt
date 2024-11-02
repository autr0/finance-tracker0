package com.devautro.financetracker.feature_statistics.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ChartsViewModel @Inject constructor(

) : ViewModel() {

    private val _chartsState = MutableStateFlow(ChartsState())
    val chartsState: StateFlow<ChartsState> = _chartsState.asStateFlow()

    private val _sideEffects = MutableSharedFlow<ChartsSideEffects>()
    val sideEffects = _sideEffects.asSharedFlow()

    fun onEvent(event: ChartsEvent) {
        when(event) {
            is ChartsEvent.PeriodTabClick -> {
                _chartsState.update { state ->
                    state.copy(
                        selectedPeriodIndex = event.index
                    )
                }
            }
        }
    }

    private fun getWeekIncome() {

    }

}
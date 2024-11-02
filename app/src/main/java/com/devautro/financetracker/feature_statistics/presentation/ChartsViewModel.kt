package com.devautro.financetracker.feature_statistics.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.model.Point
import co.yml.charts.ui.barchart.models.BarData
import co.yml.charts.ui.barchart.models.GroupBar
import com.devautro.financetracker.R
import com.devautro.financetracker.core.util.UiText
import com.devautro.financetracker.core.util.formatDoubleToString
import com.devautro.financetracker.feature_payment.domain.use_case.PaymentUseCases
import com.devautro.financetracker.feature_statistics.util.convertMillisToMonthYear
import com.devautro.financetracker.feature_statistics.util.convertMonthYearToMillis
import com.devautro.financetracker.ui.theme.ExpenseRed
import com.devautro.financetracker.ui.theme.IncomeGreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChartsViewModel @Inject constructor(
    private val paymentUseCases: PaymentUseCases
) : ViewModel() {

    private val _chartsState = MutableStateFlow(ChartsState())
    val chartsState: StateFlow<ChartsState> = _chartsState.asStateFlow()

    private val _sideEffects = MutableSharedFlow<ChartsSideEffects>()
    val sideEffects = _sideEffects.asSharedFlow()

    init {
        getAllData()
    }

    fun onEvent(event: ChartsEvent) {
        when(event) {
            is ChartsEvent.PeriodTabClick -> {
                _chartsState.update { state ->
                    state.copy(
                        selectedPeriodIndex = event.index
                    )
                }
                when(event.index) {
                    0 -> {}
                    1 -> {}
                    2 -> {
                        getAllData()
                    }
                }
            }
        }
    }

    private fun getAllData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                combine(
                    paymentUseCases.getIncomesUseCase(),
                    paymentUseCases.getExpensesUseCase()
                ) { incomes, expenses ->

                    val incomesSumOfMonthAndYear = incomes.groupBy { payment ->
//                        "${payment.monthTag} ${getYearOfTheDate(payment.date!!)}"
                        convertMillisToMonthYear(payment.date!!)
                    }.map {
                        it.key to it.value.sumOf { payment -> payment.amountNew!! }
                    }.toMap()

                    val expensesSumOfMonthAndYear = expenses.groupBy { payment ->
//                        "${payment.monthTag} ${getYearOfTheDate(payment.date!!)}"
                        convertMillisToMonthYear(payment.date!!)
                    }.map {
                        it.key to it.value.sumOf { payment -> payment.amountNew!! }
                    }.toMap()

                    incomesSumOfMonthAndYear to expensesSumOfMonthAndYear
                }.collect { (incomesMap, expensesMap) ->
                    val incomesSum = incomesMap.values.sum()
                    val expensesSum = expensesMap.values.sum()
                    val max = (incomesMap.values + expensesMap.values).max()

                    val allKeys = (incomesMap.keys + expensesMap.keys).distinct()

                    val combinedMap = allKeys.associateWith { key ->
                        val income = incomesMap[key]
                        val expense = expensesMap[key]
                        Pair(income, expense)
                    }


                    val groupedBarList = combinedMap.toList()
                        .sortedBy { pair ->
                            convertMonthYearToMillis(pair.first)
                        }
                        .mapIndexed { index, value ->
                        val amount = value.second
                        val x = index.toFloat()


                        GroupBar(
                            label = value.first,
                            barList = listOf(
                                BarData(
                                    point = Point(x = x, y = amount.first?.toFloat() ?: 0f),
                                    color = IncomeGreen
                                ),
                                BarData(
                                    point = Point(x = x, y = amount.second?.toFloat() ?: 0f),
                                    color = ExpenseRed
                                )
                            )
                        )
                    }

                    _chartsState.update { state ->
                        state.copy(
                            incomesSum = formatDoubleToString(incomesSum),
                            expensesSum = formatDoubleToString(expensesSum),
                            groupBarList = groupedBarList,
                            maxAmount = max
                        )
                    }

                }
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    private suspend fun handleError(e: Exception) {
        e.printStackTrace()

        _sideEffects.emit(
            ChartsSideEffects.ShowSnackbar(
                message = UiText.StringResource(R.string.error_get_payments_data)
            )
        )
    }

}
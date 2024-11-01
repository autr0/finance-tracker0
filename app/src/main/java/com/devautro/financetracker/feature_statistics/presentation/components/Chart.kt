package com.devautro.financetracker.feature_statistics.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.barchart.BarChart
import co.yml.charts.ui.barchart.models.BarChartData
import co.yml.charts.ui.barchart.models.BarData
import co.yml.charts.ui.barchart.models.BarStyle
import co.yml.charts.ui.barchart.models.SelectionHighlightData
import com.devautro.financetracker.ui.theme.DarkGreenCircle
import com.devautro.financetracker.ui.theme.IncomeGreen

@Composable
fun Chart(
    modifier: Modifier = Modifier
) {
    val barDataList = listOf(
        BarData(
            point = Point(0f, 40f),
            color = IncomeGreen,
            gradientColorList = listOf(Color.Transparent, IncomeGreen, DarkGreenCircle),
            label = "First"
        ),
        BarData(
            point = Point(1f, 90f),
            color = IncomeGreen,
            gradientColorList = listOf(Color.Transparent, IncomeGreen, DarkGreenCircle),
            label = "Second"
        ),
        BarData(
            point = Point(2f, 100f),
            color = IncomeGreen,
            gradientColorList = listOf(Color.Transparent, IncomeGreen, DarkGreenCircle),
            label = "Third"
        ),
        BarData(
            point = Point(3f, 60f),
            color = IncomeGreen,
            gradientColorList = listOf(Color.Transparent, IncomeGreen, DarkGreenCircle),
            label = "Fourth"
        ),
        BarData(
            point = Point(4f, 10f),
            color = IncomeGreen,
            gradientColorList = listOf(Color.Transparent, IncomeGreen, DarkGreenCircle),
            label = "Fifth"
        )
    )

    val xAxisData = AxisData.Builder()
        .startDrawPadding(42.dp) // check it out
        .axisStepSize(50.dp)
        .backgroundColor(MaterialTheme.colorScheme.primaryContainer)
        .steps(barDataList.size - 1)
        .labelData { i -> barDataList[i].label }
        .labelAndAxisLinePadding(10.dp)
        .axisLabelColor(IncomeGreen)
        .axisLineColor(IncomeGreen)
        .shouldDrawAxisLineTillEnd(true) // ???
        .build()

    val yAxisData = AxisData.Builder()
        .topPadding(42.dp) // check it out
        .steps(5)
        .backgroundColor(MaterialTheme.colorScheme.primaryContainer)
        .labelData { i ->
            val yScale = 100 / barDataList.size
            (i * yScale).toString()
        }
        .labelAndAxisLinePadding(20.dp)
        .axisLabelColor(IncomeGreen)
        .axisLineColor(IncomeGreen)
        .build()

    val barChartData = BarChartData(
        chartData = barDataList,
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
        barStyle = BarStyle(
            isGradientEnabled = true,
            selectionHighlightData = SelectionHighlightData(
                popUpLabel = { _, y ->
                    val amount = y.toDouble()
                    "amount: $amount"
                }
            )
        )
    )

    BarChart(modifier = modifier, barChartData = barChartData)

}
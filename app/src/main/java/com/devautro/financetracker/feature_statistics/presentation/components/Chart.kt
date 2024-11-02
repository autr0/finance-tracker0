package com.devautro.financetracker.feature_statistics.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.barchart.GroupBarChart
import co.yml.charts.ui.barchart.models.BarData
import co.yml.charts.ui.barchart.models.BarPlotData
import co.yml.charts.ui.barchart.models.BarStyle
import co.yml.charts.ui.barchart.models.GroupBar
import co.yml.charts.ui.barchart.models.GroupBarChartData
import co.yml.charts.ui.barchart.models.GroupSeparatorConfig
import co.yml.charts.ui.barchart.models.SelectionHighlightData
import com.devautro.financetracker.R
import com.devautro.financetracker.ui.theme.DarkestColor
import com.devautro.financetracker.ui.theme.ExpenseRed
import com.devautro.financetracker.ui.theme.IncomeGreen

@Composable
fun Chart(
    modifier: Modifier = Modifier
) {
    val groupedBars = listOf(
        GroupBar(
            barList = listOf(
                BarData(
                    point = Point(0f, 40f),
                    color = IncomeGreen,
//                    gradientColorList = listOf(Color.Transparent, IncomeGreen, DarkGreenCircle),
//                    label = "First"
                ),
                BarData(
                    point = Point(0f, 66f),
                    color = ExpenseRed,
//                    gradientColorList = listOf(Color.Transparent, ExpenseRed, DarkRedCircle),
//                    label = "First"
                )
            ),
            label = "First"
        ),
        GroupBar(
            barList = listOf(
                BarData(
                    point = Point(1f, 20f),
                    color = IncomeGreen,
//                    gradientColorList = listOf(Color.Transparent, IncomeGreen, DarkGreenCircle),
//                    label = "Second"
                ),
                BarData(
                    point = Point(1f, 100f),
                    color = ExpenseRed,
//                    gradientColorList = listOf(Color.Transparent, ExpenseRed, DarkRedCircle),
//                    label = "Second"
                )
            ),
            label = "Second"
        ),
        GroupBar(
            barList = listOf(
                BarData(
                    point = Point(2f, 90f),
                    color = IncomeGreen,
//                    gradientColorList = listOf(Color.Transparent, IncomeGreen, DarkGreenCircle),
//                    label = "Third"
                ),
                BarData(
                    point = Point(2f, 10f),
                    color = ExpenseRed,
//                    gradientColorList = listOf(Color.Transparent, ExpenseRed, DarkRedCircle),
//                    label = "Third"
                )
            ),
            label = "Third"
        ),
    )


    val xAxisData = AxisData.Builder()
        .startDrawPadding(42.dp) // check it out
        .axisStepSize(50.dp)
        .backgroundColor(MaterialTheme.colorScheme.primaryContainer)
        .steps(groupedBars.size - 1)
        .labelData { i -> groupedBars[i].label }
        .labelAndAxisLinePadding(10.dp)
        .axisLabelColor(DarkestColor)
        .axisLineColor(DarkestColor)
        .shouldDrawAxisLineTillEnd(true) // ???
        .build()

    val yAxisData = AxisData.Builder()
        .topPadding(42.dp) // check it out
        .steps(4)
        .backgroundColor(MaterialTheme.colorScheme.primaryContainer)
        .labelData { i ->
            val yScale = 100 / 4
            (i * yScale).toString()
        }
        .labelAndAxisLinePadding(20.dp)
        .axisLabelColor(DarkestColor)
        .axisLineColor(DarkestColor)
        .build()

    val sum = stringResource(id = R.string.sum)

    val barPlotData = BarPlotData(
        groupBarList = groupedBars,
        barColorPaletteList = listOf(IncomeGreen, ExpenseRed),
        barStyle = BarStyle(
            isGradientEnabled = true,
            selectionHighlightData = SelectionHighlightData(
                groupBarPopUpLabel = { _, y ->
                    val amount = y.toDouble()
                    "$sum $amount"
                }
            )
        )
    )

    val groupBarChartData = GroupBarChartData(
        barPlotData = barPlotData,
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
        groupSeparatorConfig = GroupSeparatorConfig(showSeparator = false)
    )

    GroupBarChart(modifier = modifier, groupBarChartData = groupBarChartData)
}
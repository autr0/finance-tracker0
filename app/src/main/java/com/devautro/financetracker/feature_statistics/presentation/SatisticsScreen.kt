package com.devautro.financetracker.feature_statistics.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devautro.financetracker.R
import com.devautro.financetracker.core.presentation.components.TopTabsSorting
import com.devautro.financetracker.core.util.Const
import com.devautro.financetracker.feature_statistics.presentation.components.Chart
import com.devautro.financetracker.feature_statistics.presentation.components.ChartDescription

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen(
    vm: ChartsViewModel = hiltViewModel(),
    bottomPaddingValues: PaddingValues,
    currency: String = ""
) {
    val state by vm.chartsState.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.chart_top_bar),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                modifier = Modifier.clip(RoundedCornerShape(15.dp)),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { topPaddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = topPaddingValues.calculateTopPadding(),
                    bottom = bottomPaddingValues.calculateBottomPadding()
                )
                .verticalScroll(state = rememberScrollState()), // for LandscapeMode
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopTabsSorting(
                tabItems = Const.filterTags.map { stringResource(id = it) },
                selectedTabIndex = state.selectedPeriodIndex,
                onSelectedTabClick = { tabIndex ->
                    vm.onEvent(ChartsEvent.PeriodTabClick(tabIndex))
                }
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                shape = RoundedCornerShape(15.dp),
                elevation = CardDefaults.elevatedCardElevation(
                    defaultElevation = 4.dp
                )
            ) {
                Chart(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                )
            }
            ChartDescription(incomesSum = "${currency}1200.00", expensesSum = "${currency}3500.99")

            Spacer(modifier = Modifier.weight(0.2f))
        }

    }
}
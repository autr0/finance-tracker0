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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.devautro.financetracker.core.presentation.components.TopTabsSorting
import com.devautro.financetracker.core.util.Const
import com.devautro.financetracker.feature_statistics.presentation.components.Chart

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen(
    bottomPaddingValues: PaddingValues,
    currency: String = ""
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Charts",
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
                .verticalScroll(state = rememberScrollState()) // for LandscapeMode
        ) {
            TopTabsSorting(
                tabItems = Const.filterTags.map { stringResource(id = it) },
                selectedTabIndex = 0,
                onSelectedTabClick = { tabIndex ->

                }
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
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
            Spacer(modifier = Modifier.weight(0.1f))
        }

    }
}
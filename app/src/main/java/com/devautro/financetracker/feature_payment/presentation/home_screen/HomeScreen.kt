package com.devautro.financetracker.feature_payment.presentation.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.devautro.financetracker.core.presentation.components.TopTabsSorting
import com.devautro.financetracker.feature_payment.presentation.home_screen.components.InfoCard
import com.devautro.financetracker.ui.theme.DarkGreenCircle
import com.devautro.financetracker.ui.theme.DarkRedCircle
import com.devautro.financetracker.ui.theme.FinanceTrackerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    bottomPadding: PaddingValues,
    navigateToIncomes: () -> Unit,
    navigateToExpenses: () -> Unit
) {

    val tabItems = listOf("DAY", "WEEK", "MONTH")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Home",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                modifier = Modifier.clip(RoundedCornerShape(15.dp)),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.clip(RoundedCornerShape(15.dp))
            ) {

            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = bottomPadding.calculateBottomPadding()
                )
                .background(MaterialTheme.colorScheme.background),
        ) {
            TopTabsSorting(tabItems = tabItems, defaultTabIndex = 2)
            LazyColumn(
                modifier = Modifier.fillMaxWidth()

            ) {
                item {
                    InfoCard(
                        modifier = Modifier.clickable {
                            /*TODO: navigate to IncomesScreen*/
                            navigateToIncomes()
                        },
                        text = "Incomes",
                        amount = "2300 $",
                        color = DarkGreenCircle,
                    )
                }
                item {
                    InfoCard(
                        modifier = Modifier.clickable {
                            /*TODO: navigate to ExpensesScreen*/
                            navigateToExpenses()
                        },
                        text = "Expenses",
                        amount = "1000 $",
                        color = DarkRedCircle
                    )
                }
                item {
                    InfoCard(
                        text = "Budget",
                        amount = "-1300 $",
                        color = MaterialTheme.colorScheme.background
                    )
                }
            }
        }

    }
}

@Preview(
    showBackground = true
)
@Composable
fun HomePreview() {
    FinanceTrackerTheme {
        HomeScreen(
            bottomPadding = PaddingValues(0.dp),
            navigateToIncomes = {},
            navigateToExpenses = {}
        )
    }
}
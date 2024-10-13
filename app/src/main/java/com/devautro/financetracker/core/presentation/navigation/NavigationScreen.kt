package com.devautro.financetracker.core.presentation.navigation

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.devautro.financetracker.core.presentation.TabBarItem
import com.devautro.financetracker.core.presentation.components.BottomNavigationBar
import com.devautro.financetracker.feature_payment.presentation.add_payment.AddPaymentBottomSheet
import com.devautro.financetracker.feature_payment.presentation.home_screen.HomeScreen
import com.devautro.financetracker.feature_payment.presentation.payments_type_list.PaymentsTypeList
import com.devautro.financetracker.ui.theme.ExpenseRed
import com.devautro.financetracker.ui.theme.FinanceTrackerTheme
import com.devautro.financetracker.ui.theme.IncomeGreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationScreen() {

    val homeTab = TabBarItem(
        title = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    )
    val chartsTab = TabBarItem(
        title = "Charts",
        selectedIcon = Icons.Filled.BarChart,
        unselectedIcon = Icons.Outlined.BarChart
    )
    val accountsTab = TabBarItem(
        title = "Accounts",
        selectedIcon = Icons.Filled.AccountBalanceWallet,
        unselectedIcon = Icons.Outlined.AccountBalanceWallet
    )
    val settingsTab = TabBarItem(
        title = "Settings",
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings
    )
    val barItems = listOf(homeTab, chartsTab, accountsTab, settingsTab)

    val navController = rememberNavController()
    var showBottomBar by rememberSaveable { mutableStateOf(true) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    showBottomBar = when (navBackStackEntry?.destination?.route) {
        "oops" -> false
        else -> true
    }

    // FAB bottomSheetState ->
    val showBottomSheet = remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(
                    barItems = barItems,
                    navController = navController,
                    currentDestination = currentDestination
                )
            }
        },
        floatingActionButton = {
            if (showBottomBar) {
                FloatingActionButton(
                    onClick = {
                        showBottomSheet.value = true
                    },
                    containerColor = MaterialTheme.colorScheme.primary,
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 6.dp
                    ),
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier
                        .offset(y = 32.dp)
                        .zIndex(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { bottomNavPadding ->

        /* TODO -> App Navigation (new type-safe way?) */
        NavHost(navController = navController, startDestination = "1") {
            composable(route = "1") {
                HomeScreen(
                    bottomPadding = bottomNavPadding,
                    navigateToIncomes = { navController.navigate("2") },
                    navigateToExpenses = { navController.navigate("3") }
                )


                /*TODO: -> not state but HOmeScreen viewModel? */
                if (showBottomSheet.value) {
                    AddPaymentBottomSheet(
                        sheetState = sheetState,
                        navigateBack = {
                            showBottomSheet.value = false
                        }
                    )
                }
            }
            composable(route = "2") {
                PaymentsTypeList(
                    paymentTypeText = "IncomesTest",
                    navigateBack = { navController.navigateUp() },
                    navBarPadding = bottomNavPadding,
                    cardColor = IncomeGreen
                )
            }

            composable(route = "3") {
                PaymentsTypeList(
                    paymentTypeText = "ExpensesTest",
                    navigateBack = { navController.navigateUp() },
                    navBarPadding = bottomNavPadding,
                    cardColor = ExpenseRed
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomBarPreview() {
    FinanceTrackerTheme {
//        val homeTab = TabBarItem(
//            title = "Home",
//            selectedIcon = Icons.Filled.Home,
//            unselectedIcon = Icons.Outlined.Home
//        )
//        val chartsTab = TabBarItem(
//            title = "Charts",
//            selectedIcon = Icons.Filled.PieChart,
//            unselectedIcon = Icons.Outlined.PieChart
//        )
//        val accountsTab = TabBarItem(
//            title = "Accounts",
//            selectedIcon = Icons.Filled.AccountBalanceWallet,
//            unselectedIcon = Icons.Outlined.AccountBalanceWallet
//        )
//        val settingsTab = TabBarItem(
//            title = "Settings",
//            selectedIcon = Icons.Filled.Settings,
//            unselectedIcon = Icons.Outlined.Settings
//        )
//        val barItems = listOf(homeTab, chartsTab, accountsTab, settingsTab)
//
//        val navController = rememberNavController()
//        var showBottomBar by rememberSaveable { mutableStateOf(true) }
//        val navBackStackEntry by navController.currentBackStackEntryAsState()
//        val currentDestination = navBackStackEntry?.destination
//
//        showBottomBar = when (navBackStackEntry?.destination?.route) {
//            "oops" -> false
//            else -> true
//        }
////        showBottomBar = false
//
//        Scaffold(
//            bottomBar = {
//                if (showBottomBar) {
//                    BottomNavigationBar(
//                        barItems = barItems,
//                        navController = navController,
//                        currentDestination = currentDestination
//                    )
//                }
//            },
//            floatingActionButton = {
//                if (showBottomBar) {
//                    FloatingActionButton(
//                        onClick = { /*TODO*/ },
//                        containerColor = MaterialTheme.colorScheme.primary,
//                        elevation = FloatingActionButtonDefaults.elevation(
//                            defaultElevation = 6.dp
//                        ),
//                        shape = RoundedCornerShape(15.dp),
//                        modifier = Modifier
//                            .offset(y = 32.dp)
//                            .zIndex(1f)
//                    ) {
//                        Icon(
//                            imageVector = Icons.Default.Add,
//                            contentDescription = "Add",
//                            tint = MaterialTheme.colorScheme.onBackground
//                        )
//                    }
//                }
//            },
//            floatingActionButtonPosition = FabPosition.Center
//        ) { bottomNavPadding ->
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(PinkAccentCard)
//                    .padding(bottomNavPadding)
//            ) {
//                InfoCard(
//                    modifier = Modifier.clickable {
//                        /*TODO: navigate to IncomesScreen*/
//                    },
//                    text = "Incomes",
//                    amount = "2300 $",
//                    color = DarkGreenCircle,
//                )
//                InfoCard(
//                    modifier = Modifier.clickable {
//                        /*TODO: navigate to IncomesScreen*/
//                    },
//                    text = "Incomes",
//                    amount = "2300 $",
//                    color = DarkGreenCircle,
//                )
//                InfoCard(
//                    modifier = Modifier.clickable {
//                        /*TODO: navigate to IncomesScreen*/
//                    },
//                    text = "Incomes",
//                    amount = "2300 $",
//                    color = DarkGreenCircle,
//                )
//                InfoCard(
//                    modifier = Modifier.clickable {
//                        /*TODO: navigate to IncomesScreen*/
//                    },
//                    text = "Incomes",
//                    amount = "2300 $",
//                    color = DarkGreenCircle,
//                )
//            }
//
//        }
        NavigationScreen()
    }
}
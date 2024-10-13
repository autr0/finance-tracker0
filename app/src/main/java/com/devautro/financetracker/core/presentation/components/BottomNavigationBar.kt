package com.devautro.financetracker.core.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.devautro.financetracker.core.presentation.TabBarItem
import com.devautro.financetracker.core.presentation.util.AutoResizedText
import com.devautro.financetracker.ui.theme.UnChosenTextColor

@Composable
fun BottomNavigationBar(
    barItems: List<TabBarItem>,
    navController: NavController,
    currentDestination: NavDestination?
) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth()
    ) {
        barItems.forEach { item ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == item.title } == true,
                onClick = {
                    navController.navigate(item.title) {
                        popUpTo(item.title)
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (
                            currentDestination?.hierarchy?.any { it.route == item.title } == true
                        ) {
                            item.selectedIcon
                        } else {
                            item.unselectedIcon
                        },
                        contentDescription = item.title
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onBackground,
                    unselectedIconColor = UnChosenTextColor,
                    selectedTextColor = MaterialTheme.colorScheme.onBackground,
                    unselectedTextColor = UnChosenTextColor,
                    indicatorColor = MaterialTheme.colorScheme.primary
                ),
                label = {
                    AutoResizedText(text = item.title)
                }
            )
        }

    }
}
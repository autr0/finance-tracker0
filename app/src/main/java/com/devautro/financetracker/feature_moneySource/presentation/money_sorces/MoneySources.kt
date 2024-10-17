package com.devautro.financetracker.feature_moneySource.presentation.money_sorces

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.SwitchLeft
import androidx.compose.material.icons.filled.SwitchRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devautro.financetracker.core.presentation.components.ActionIcon
import com.devautro.financetracker.core.util.Const
import com.devautro.financetracker.feature_moneySource.presentation.money_sorces.components.DeleteDialog
import com.devautro.financetracker.feature_moneySource.presentation.money_sorces.components.MoneySourceCard
import com.devautro.financetracker.feature_moneySource.presentation.money_sorces.components.TableDataItem
import com.devautro.financetracker.core.presentation.components.SwipeableItem
import com.devautro.financetracker.feature_payment.util.formatDoubleToString
import com.devautro.financetracker.ui.theme.AccentBlue
import com.devautro.financetracker.ui.theme.FinanceTrackerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoneySources(
    bottomNavPadding: PaddingValues,
    navigateToAddEditScreen: () -> Unit
) {
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    val switchScreenState = remember {
        mutableStateOf(false)
    }

    val isItemRevealed = remember {
        mutableStateOf(false)
    }

    val showDeleteDialog = remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Money Sources",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                modifier = Modifier.clip(RoundedCornerShape(15.dp)),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                actions = {
                    ActionIcon(
                        onClick = {
                            /*TODO*/
                            navigateToAddEditScreen()
                        },
                        backgroundColor = AccentBlue,
                        icon = Icons.Filled.AddCircleOutline,
                        tint = MaterialTheme.colorScheme.background,
                        modifier = Modifier
                            .shadow(
                                elevation = 4.dp,
                                shape = RoundedCornerShape(15.dp)
                            )
                            .clip(RoundedCornerShape(15.dp))
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    ActionIcon(
                        modifier = Modifier
                            .clip(RoundedCornerShape(15.dp)),
                        onClick = {
                            /*TODO*/
                            switchScreenState.value = !switchScreenState.value
                        },
                        backgroundColor = AccentBlue,
                        tint = MaterialTheme.colorScheme.background,
                        icon = if (switchScreenState.value || isLandscape) {
                            Icons.Filled.SwitchLeft
                        } else Icons.Filled.SwitchRight,
                        isEnabled = !isLandscape
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                }
            )
        },

        ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = bottomNavPadding.calculateBottomPadding()
                )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(0.8f),
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp),
                        text = "Total amount of money:",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .padding(start = 20.dp, end = 10.dp),
                        text = "6234.56 $",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            LazyColumn(
                modifier = Modifier.weight(1f),
                state = rememberLazyListState()
            ) {
                itemsIndexed(
                    items = listOf<MoneySourceItem>(
                        MoneySourceItem(
                            id = 0,
                            name = "Tink Debit card",
                            amount = 3456.09,
                            isRevealed = false
                        ),
                        MoneySourceItem(
                            id = 1,
                            name = "Sber Debit card",
                            amount = 15000.0,
                            isRevealed = true
                        ),
                        MoneySourceItem(
                            id = 2,
                            name = "Vtb Debit card",
                            amount = 32456.78,
                            isRevealed = false
                        ),
                    ),
                    key = { index, _ -> index },
                ) { index, item ->
                    SwipeableItem(
                        isRevealed = item.isRevealed,
                        actions = {
                            ActionIcon(
                                modifier = Modifier.clip(RoundedCornerShape(15.dp)),
                                onClick = { showDeleteDialog.value = true },
                                backgroundColor = MaterialTheme.colorScheme.errorContainer,
                                icon = Icons.Default.Delete,
                                contentDescription = "delete moneySource",
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        },
                        onExpanded = {
                            isItemRevealed.value = true
                            // update value of the item
                        },
                        onCollapsed = {
                            isItemRevealed.value = false
                            // update value of the item
                        }
                    ) {
                        if (switchScreenState.value || isLandscape) {
                            TableDataItem(
                                index = index,
                                sourceName = item.name,
                                amount = formatDoubleToString(item.amount),
                                onEditClick = {
                                    /*TODO: EditIconClickEvent(navigateToEditScreen) */
                                    navigateToAddEditScreen()
                                },
                                backgroundColor = Const.sourcePaleColors[index]
                            )
                        } else {
                            MoneySourceCard(
                                modifier = Modifier.height(220.dp),
                                cardPaleColor = Const.sourcePaleColors[index],
                                cardAccentColor = Const.sourceAccentColors[index],
                                index = index,
                                sourceName = item.name,
                                amount = formatDoubleToString(item.amount),
                                onEditIconClick = {
                                    /* TODO: EditIconClickEvent(navigateToEditScreen) */
                                    navigateToAddEditScreen()
                                }
                            )
                        }
                    }
                }

            }
            if (showDeleteDialog.value) {
                DeleteDialog(
                    onConfirmClick = {
                        /*TODO*/
                        showDeleteDialog.value = false
                    },
                    onDismissClick = { showDeleteDialog.value = false }
                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun MoneySourcesPreview() {
//    FinanceTrackerTheme {
//        MoneySources(bottomNavPadding = PaddingValues(0.dp))
//    }
//}
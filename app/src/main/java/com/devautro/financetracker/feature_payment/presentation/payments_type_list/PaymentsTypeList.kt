package com.devautro.financetracker.feature_payment.presentation.payments_type_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devautro.financetracker.core.presentation.components.ActionIcon
import com.devautro.financetracker.feature_payment.presentation.payments_type_list.components.MonthTagsDrawerMenu
import com.devautro.financetracker.feature_payment.presentation.payments_type_list.components.PaymentTypeCard
import com.devautro.financetracker.feature_payment.presentation.payments_type_list.components.SelectedMonthContainer
import com.devautro.financetracker.feature_payment.presentation.payments_type_list.components.SwipeableItem
import com.devautro.financetracker.feature_payment.presentation.payments_type_list.components.TotalAmountCard
import com.devautro.financetracker.feature_payment.util.convertMillisToDate
import com.devautro.financetracker.ui.theme.DarkestColor
import com.devautro.financetracker.ui.theme.FinanceTrackerTheme
import com.devautro.financetracker.ui.theme.IncomeGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentsTypeList(
    paymentTypeText: String,
    navigateBack: () -> Unit,
    navBarPadding: PaddingValues,
    cardColor: Color
) {
    val onDismissMenu = remember {
        mutableStateOf(false)
    }
    val selectedItem = remember {
        mutableStateOf("")
    }

    val pList = remember {
        mutableStateListOf(
            PaymentsListItem(
                isRevealed = false,
                id = 0L,
                description = "pack of chips",
                amount = 1234.0
            ),
            PaymentsListItem(
                isRevealed = false,
                id = 1L,
                description = "A long text to check wtf is happening even know",
                amount = 234.0
            ),
            PaymentsListItem(
                isRevealed = false,
                id = 2L,
                description = "Short desc of pack of chips",
                amount = 34.0
            ),
            PaymentsListItem(
                isRevealed = false,
                id = 3L,
                description = "",
                amount = 4.9
            ),
            PaymentsListItem(
                isRevealed = false,
                id = 4L,
                description = "nice",
                amount = 12237234.03
            ),
            PaymentsListItem(
                isRevealed = false,
                id = 5L,
                description = "loan",
                amount = 17.99
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (selectedItem.value.isEmpty() || selectedItem.value.isBlank()) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = paymentTypeText)
                        }
                    } else {
                        SelectedMonthContainer(monthTag = selectedItem.value)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { /* TODO -> NavigateBack() */
                        navigateBack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "arrow back"
                        )
                    }
                },
                actions = {
                    Row(horizontalArrangement = Arrangement.Center) {
                        if (selectedItem.value.isNotBlank()) {
                            IconButton(onClick = { /* TODO -> openDrawerEvent() */
                                onDismissMenu.value = false
                                selectedItem.value = ""
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Clear,
                                    contentDescription = "cancel"
                                )
                            }
                        }
                        IconButton(onClick = { /* TODO -> openDrawerEvent() */
                            onDismissMenu.value = true
                        }) {
                            Icon(
                                imageVector = Icons.Filled.FilterAlt,
                                contentDescription = "filter"
                            )
                        }
                        MonthTagsDrawerMenu(
                            isExpanded = onDismissMenu.value,
                            onDismissMenu = { /*TODO*/ onDismissMenu.value = false },
                            selectedItem = selectedItem.value,
                            onSelectedItem = { month ->
                                /*TODO -> SelectMonthEvent()*/
                                selectedItem.value = month
                            }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = navBarPadding.calculateBottomPadding()
                )
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TotalAmountCard(
                modifier = Modifier.padding(horizontal = 20.dp),
                amount = pList.map { it.amount }.sumOf { it }.toString()
            )
            LazyColumn(
                modifier = Modifier.weight(1f),
                state = rememberLazyListState(),
                reverseLayout = true
            ) {
                itemsIndexed(
                    items = pList,
//                    key = { index, payment -> payment.id } -> 'cause mutable list
                ) { index, payment ->
                    SwipeableItem(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .padding(vertical = 16.dp)
                        ,
                        isRevealed = payment.isRevealed,
                        actions = {
                            ActionIcon(
                                onClick = { pList[index] = payment.copy(isRevealed = false) },
                                backgroundColor = DarkestColor,
                                icon = Icons.Default.Edit,
                                contentDescription = "edit payment",
                                modifier = Modifier.clip(RoundedCornerShape(15.dp))
                            )
                            ActionIcon(
                                onClick = { pList[index] = payment.copy(isRevealed = false) },
                                backgroundColor = MaterialTheme.colorScheme.errorContainer,
                                icon = Icons.Default.Delete,
                                contentDescription = "delete payment",
                                modifier = Modifier.clip(RoundedCornerShape(15.dp))
                            )
                        },
                        onExpanded = { pList[index] = payment.copy(isRevealed = true) },
                        onCollapsed = { pList[index] = payment.copy(isRevealed = false) }
                    ) {
                        PaymentTypeCard(
//                            modifier = Modifier
//                                .fillMaxWidth(0.8f)
//                                .padding(vertical = 16.dp),
                            description = payment.description,
                            amount = payment.amount.toString(),
                            monthTag = payment.monthTag,
                            date = convertMillisToDate(payment.date),
                            color = cardColor
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PaymentsTypeListPreview() {
    FinanceTrackerTheme {
        PaymentsTypeList(
            paymentTypeText = "Incomes",
            navigateBack = {},
            navBarPadding = PaddingValues(0.dp),
            cardColor = IncomeGreen
        )
    }
}
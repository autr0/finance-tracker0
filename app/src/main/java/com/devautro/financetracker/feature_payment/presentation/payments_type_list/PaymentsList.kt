package com.devautro.financetracker.feature_payment.presentation.payments_type_list

import android.util.Log
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devautro.financetracker.core.presentation.components.ActionIcon
import com.devautro.financetracker.feature_payment.presentation.edit_payment.EditPaymentSheet
import com.devautro.financetracker.feature_payment.presentation.payments_type_list.components.MonthTagsDrawerMenu
import com.devautro.financetracker.feature_payment.presentation.payments_type_list.components.PaymentTypeCard
import com.devautro.financetracker.feature_payment.presentation.payments_type_list.components.SelectedMonthContainer
import com.devautro.financetracker.feature_payment.presentation.payments_type_list.components.SwipeableItem
import com.devautro.financetracker.feature_payment.presentation.payments_type_list.components.TotalAmountCard
import com.devautro.financetracker.feature_payment.util.convertMillisToDate
import com.devautro.financetracker.feature_payment.util.formatDoubleToString
import com.devautro.financetracker.ui.theme.DarkestColor
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentsList(
    viewModel: PaymentsListViewModel = hiltViewModel(),
    paymentTypeText: String,
    navigateBack: () -> Unit,
    navBarPadding: PaddingValues,
    cardColor: Color,
    isExpense: Boolean
) {
    val state by viewModel.paymentsState.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val lazyList = rememberLazyListState()

    LaunchedEffect(key1 = true) {
        viewModel.onEvent(PaymentsListEvent.GetInitialPaymentType(isExpense = isExpense))

        viewModel.sideEffects.collectLatest { effect ->
            when (effect) {
                is PaymentsListSideEffects.NavigateBack -> navigateBack()
                is PaymentsListSideEffects.ShowSnackBar -> {
                    Log.d("MyLog", "Showing Snackbar: ${effect.message}")
                    snackbarHostState.showSnackbar(
                        message = effect.message,
                        actionLabel = "Undo"
                    ).let { res ->
                        if (res == SnackbarResult.ActionPerformed) {
                            viewModel.onEvent(PaymentsListEvent.RestorePayment)
                        }
                    }

                }
            }
        }
    }

    LaunchedEffect(key1 = state.paymentItemsList.size) {
        if (state.paymentItemsList.isNotEmpty()) lazyList.scrollToItem(0)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    if (state.selectedMonthTag.isEmpty() || state.selectedMonthTag.isBlank()) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = paymentTypeText)
                        }
                    } else {
                        SelectedMonthContainer(monthTag = state.selectedMonthTag)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        viewModel.onEvent(PaymentsListEvent.NavigateBack)
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "arrow back",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                actions = {
                    Row(horizontalArrangement = Arrangement.Center) {
                        if (state.selectedMonthTag.isNotBlank()) {
                            IconButton(onClick = {
                                viewModel.onEvent(PaymentsListEvent.ClearIconCLick(isExpense = isExpense))
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Clear,
                                    contentDescription = "clear",
                                    tint = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                        IconButton(onClick = {
                            viewModel.onEvent(PaymentsListEvent.FilterIconClick)
                        }) {
                            Icon(
                                imageVector = Icons.Filled.FilterAlt,
                                contentDescription = "filter",
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                        MonthTagsDrawerMenu(
                            isExpanded = state.isMonthTagMenuVisible,
                            onDismissMenu = {
                                viewModel.onEvent(PaymentsListEvent.DismissMonthTagMenu)
                            },
                            selectedItem = state.selectedMonthTag,
                            onSelectedItem = { month ->
                                viewModel.onEvent(
                                    PaymentsListEvent.MonthTagSelected(
                                        monthTag = month,
                                        isExpense = isExpense
                                    )
                                )
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
                amount = state.totalAmount
            )
            LazyColumn(
                modifier = Modifier.weight(1f),
                state = lazyList,
                reverseLayout = true
            ) {
                itemsIndexed(
                    items = state.paymentItemsList,
                    key = { index, paymentItem -> paymentItem.id ?: index }
                ) { _, payment ->
                    SwipeableItem(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .padding(vertical = 16.dp),
                        isRevealed = payment.isRevealed,
                        actions = {
                            ActionIcon(
                                onClick = {
                                    viewModel.onEvent(
                                        PaymentsListEvent.ItemRevealed(
                                            id = payment.id!!,
                                            isRevealed = false
                                        )
                                    )
                                    viewModel.onEvent(
                                        PaymentsListEvent.EditIconClick(
                                            paymentItem = payment
                                        )
                                    )
                                },
                                backgroundColor = DarkestColor ,
                                tint = MaterialTheme.colorScheme.onBackground,
                                icon = Icons.Default.Edit,
                                contentDescription = "edit payment",
                                modifier = Modifier.clip(RoundedCornerShape(15.dp))
                            )
                            ActionIcon(
                                onClick = {
                                    viewModel.onEvent(
                                        PaymentsListEvent.ItemRevealed(
                                            id = payment.id!!,
                                            isRevealed = false
                                        )
                                    )
                                    viewModel.onEvent(
                                        PaymentsListEvent.DeleteIconClick(
                                            paymentItem = payment
                                        )
                                    )
                                },
                                backgroundColor = MaterialTheme.colorScheme.errorContainer,
                                tint = MaterialTheme.colorScheme.onBackground,
                                icon = Icons.Default.Delete,
                                contentDescription = "delete payment",
                                modifier = Modifier.clip(RoundedCornerShape(15.dp))
                            )
                        },
                        onExpanded = {
                            viewModel.onEvent(
                                PaymentsListEvent.ItemRevealed(
                                    id = payment.id!!,
                                    isRevealed = true
                                )
                            )
                        },
                        onCollapsed = {
                            viewModel.onEvent(
                                PaymentsListEvent.ItemRevealed(
                                    id = payment.id!!,
                                    isRevealed = false
                                )
                            )
                        }
                    ) {
                        PaymentTypeCard(
                            description = payment.description,
                            amount = formatDoubleToString(payment.amountNew),
                            monthTag = payment.monthTag,
                            date = convertMillisToDate(payment.date),
                            color = cardColor
                        )
                    }
                }
            }
            if (state.isEditBottomSheetVisible) {
                EditPaymentSheet(
                    sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
                    navigateBack = { viewModel.onEvent(PaymentsListEvent.ShowEditBottomSheet) },
                    initialPayment = state.paymentForSheet
                )
            }
        }
    }
}
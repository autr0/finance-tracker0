package com.devautro.financetracker.feature_moneySource.presentation.add_edit_money_source.edit_money_source

import android.content.res.Configuration
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devautro.financetracker.core.presentation.components.DualOptionButtonsRow
import com.devautro.financetracker.core.util.Const
import com.devautro.financetracker.feature_moneySource.presentation.add_edit_money_source.AddEditMoneySourceEvent
import com.devautro.financetracker.feature_moneySource.presentation.add_edit_money_source.AddEditSourceSideEffects
import com.devautro.financetracker.feature_moneySource.presentation.add_edit_money_source.components.IncludeInTotalRow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditMoneySource(
    viewModel: EditMoneySourceViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    initialColor: Int
) {
    val state by viewModel.editMoneySourceState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val cardBackgroundAnimatable = remember {
        Animatable(
            initialValue = Color(initialColor)
        )
    }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.sideEffects.collectLatest { effect ->
            when (effect) {
                is AddEditSourceSideEffects.ApproveButtonClicked -> navigateBack()
                is AddEditSourceSideEffects.CancelButtonClicked -> navigateBack()
                is AddEditSourceSideEffects.Showsnackbar -> {
                    snackbarHostState.showSnackbar(message = effect.message)
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Edit Money Source",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                modifier = Modifier.clip(RoundedCornerShape(15.dp)),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { topPaddingValues ->
        Column(
            modifier = Modifier
                .padding(top = topPaddingValues.calculateTopPadding())
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState()), // for landscape mode
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IncludeInTotalRow(
                checkedValue = state.includedInTotal,
                onCheckedChange = {
                    viewModel.onEvent(AddEditMoneySourceEvent.IncludeInTotalToggled)
                }
            )
            Spacer(modifier = Modifier.weight(0.1f))
            Card(
                modifier = Modifier
                    .fillMaxWidth(
                        if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            0.8f
                        } else 1f
                    )
                    .height(220.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(15.dp),
                colors = CardDefaults.cardColors(
                    contentColor = MaterialTheme.colorScheme.background,
                    containerColor = cardBackgroundAnimatable.value
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 3.dp,
                    pressedElevation = 6.dp
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(0.7f),
                        value = state.name,
                        onValueChange = {
                            viewModel.onEvent(AddEditMoneySourceEvent.SourceNameChanged(name = it))
                        },
                        singleLine = true,
                        label = {
                            Text(text = "Name of source")
                        },
                        textStyle = MaterialTheme.typography.headlineMedium,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                            focusedBorderColor = MaterialTheme.colorScheme.background,
                            unfocusedLabelColor = MaterialTheme.colorScheme.background,
                            focusedLabelColor = MaterialTheme.colorScheme.background,
                            focusedTextColor = MaterialTheme.colorScheme.background,
                            unfocusedTextColor = MaterialTheme.colorScheme.background
                        )
                    )
                }
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(0.7f),
                        value = state.amount,
                        onValueChange = {
                            viewModel.onEvent(AddEditMoneySourceEvent.SourceAmountChanged(amount = it))
                        },
                        singleLine = true,
                        label = {
                            Text(text = "Amount of money")
                        },
                        textStyle = MaterialTheme.typography.headlineMedium,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                            focusedBorderColor = MaterialTheme.colorScheme.background,
                            unfocusedLabelColor = MaterialTheme.colorScheme.background,
                            focusedLabelColor = MaterialTheme.colorScheme.background,
                            focusedTextColor = MaterialTheme.colorScheme.background,
                            unfocusedTextColor = MaterialTheme.colorScheme.background
                        )
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.7f),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Const.sourcePaleColors.forEachIndexed { index, color ->
                            val colorInt = color.toArgb()
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .shadow(15.dp, CircleShape)
                                    .clip(CircleShape)
                                    .background(color)
                                    .border(
                                        width = 2.dp,
                                        color = if (state.paleColor == colorInt) {
                                            MaterialTheme.colorScheme.background
                                        } else Color.Transparent,
                                        shape = CircleShape
                                    )
                                    .clickable {
                                        scope.launch {
                                            cardBackgroundAnimatable.animateTo(
                                                targetValue = color,
                                                animationSpec = tween(
                                                    durationMillis = 500
                                                )
                                            )
                                        }
                                        viewModel.onEvent(
                                            AddEditMoneySourceEvent.NewColorPicked(
                                                paleColor = colorInt,
                                                accentColor = Const.sourceAccentColors[index].toArgb()
                                            )
                                        )
                                    }
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.weight(0.5f))
            DualOptionButtonsRow(
                dismissText = "Cancel",
                approveText = "Save",
                onDismiss = {
                    viewModel.onEvent(AddEditMoneySourceEvent.CancelButtonClicked)
                },
                onApprove = {
                    viewModel.onEvent(AddEditMoneySourceEvent.ApproveButtonClicked)
                },
                isApproveEnabled = state.amount.isNotBlank() && state.name.isNotBlank()
            )
        }
    }
}
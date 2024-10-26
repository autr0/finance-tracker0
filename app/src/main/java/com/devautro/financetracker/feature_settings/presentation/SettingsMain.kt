package com.devautro.financetracker.feature_settings.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.GTranslate
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devautro.financetracker.R
import com.devautro.financetracker.feature_settings.presentation.components.DropDownLanguageMenu
import com.devautro.financetracker.feature_settings.presentation.components.SettingsItem
import com.devautro.financetracker.ui.theme.CancelButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsMain(
    viewModel: SettingsViewModel = hiltViewModel(),
    bottomPadding: PaddingValues
) {

    val state by viewModel.settingsState.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

//    val showDropDownLanguageMenu = remember {
//        mutableStateOf(false)
//    }
//    val selectedLanguageId = remember {
//        mutableIntStateOf(R.drawable.gb)
//    }

    val isDarkThemeChosen = remember {
        mutableStateOf(false)
    }
    val showDeleteDialog = remember {
        mutableStateOf(false)
    }
    val showCurrencyDialog = remember {
        mutableStateOf(false)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.settings_header),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { topPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = topPadding.calculateTopPadding(),
                    bottom = bottomPadding.calculateBottomPadding()
                )
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .height(IntrinsicSize.Min)
                            .fillMaxWidth()
                    ) {
                        SettingsItem(
                            headerText = stringResource(id = R.string.language),
                            bodyText = stringResource(id = R.string.choose_language_text),
                            icon = Icons.Filled.GTranslate,
                            contentDescription = stringResource(id = R.string.icon_language_description),
                            onCardClick = {
                                viewModel.onEvent(SettingsEvent.ShowLanguageMenu)
                            },
                            switcher = {
                                Icon(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(RoundedCornerShape(15.dp)),
                                    painter = painterResource(id = state.selectedLanguageImageId),
                                    contentDescription = stringResource(id = R.string.icon_flag_description),
                                    tint = Color.Unspecified // for correct drawable color
                                )
                            }
                        )
                        DropDownLanguageMenu(
                            modifier = Modifier.fillMaxWidth(),
                            isExpanded = state.isLanguageMenuShown,
                            onDismissMenu = { viewModel.onEvent(SettingsEvent.DismissLanguageMenu) },
                            selectedItem = state.selectedLanguageImageId,
                            onSelectedItemIdChange = { flagItemId, locale ->
                                viewModel.onEvent(SettingsEvent.NewLanguageSelected(
                                    flagImage = flagItemId,
                                    locale = locale
                                ))
                            }
                        )
                    }
                }
                item {
                    SettingsItem(
                        headerText = stringResource(id = R.string.theme),
                        bodyText = stringResource(id = R.string.theme_body),
                        icon = Icons.Default.NightsStay,
                        contentDescription = stringResource(id = R.string.icon_theme_description),
                        switcher = {
                            Switch(
                                checked = true,
                                onCheckedChange = {  },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = MaterialTheme.colorScheme.onBackground,
                                    uncheckedThumbColor = MaterialTheme.colorScheme.primary,
                                    checkedTrackColor = MaterialTheme.colorScheme.secondary,
                                    uncheckedTrackColor = CancelButton
                                )
                            )
                        },
                        onCardClick = {  }
                    )
                }
                item {
                    SettingsItem(
                        headerText = stringResource(id = R.string.delete_settings),
                        bodyText = stringResource(id = R.string.delete_settings_body),
                        icon = Icons.Filled.Delete,
                        contentDescription = stringResource(id = R.string.icon_delete_all_description),
                        onCardClick = {  }
                    )
                }
                item {
                    SettingsItem(
                        headerText = stringResource(id = R.string.currency),
                        bodyText = stringResource(id = R.string.currency_body),
                        icon = Icons.Filled.MonetizationOn,
                        contentDescription = stringResource(id = R.string.icon_currency_description),
                        onCardClick = {  }
                    )
                }
            }
        }
    }
}
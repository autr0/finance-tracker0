package com.devautro.financetracker

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devautro.financetracker.core.presentation.navigation.NavigationScreen
import com.devautro.financetracker.feature_settings.presentation.SettingsEvent
import com.devautro.financetracker.feature_settings.presentation.SettingsViewModel
import com.devautro.financetracker.ui.theme.FinanceTrackerTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {

            val vm = hiltViewModel<SettingsViewModel>()
            val theme = vm.settingsState.collectAsStateWithLifecycle()

            FinanceTrackerTheme(darkTheme = theme.value.isDarkTheme) {
                NavigationScreen(vm)
            }
        }
    }

}

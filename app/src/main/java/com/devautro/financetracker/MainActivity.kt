package com.devautro.financetracker

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devautro.financetracker.core.presentation.navigation.NavigationScreen
import com.devautro.financetracker.feature_settings.presentation.SettingsViewModel
import com.devautro.financetracker.ui.theme.FinanceTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {

            val vm = hiltViewModel<SettingsViewModel>()
            val state = vm.settingsState.collectAsStateWithLifecycle()

            FinanceTrackerTheme(darkTheme = state.value.isDarkTheme) {
                NavigationScreen(
                    settingsViewModel = vm,
                    currencySign = state.value.selectedCurrency
                )
            }
        }
    }

}
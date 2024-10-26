package com.devautro.financetracker

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.devautro.financetracker.core.presentation.navigation.NavigationScreen
import com.devautro.financetracker.ui.theme.FinanceTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            FinanceTrackerTheme {
                NavigationScreen()
            }
        }
    }
}

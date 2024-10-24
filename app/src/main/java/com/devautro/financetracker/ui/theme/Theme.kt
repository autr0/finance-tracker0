package com.devautro.financetracker.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = secondary, // not sure...
    onPrimary = OnBackgroundColor,
    secondary = AccentBlue,
    onSecondary = OnAccentBlue,
    background = BackgroundColor,
    onBackground = OnBackgroundColor,
    primaryContainer = SurfaceColor,
    onPrimaryContainer = Color.Black

)

//private val LightColorScheme = lightColorScheme(
//    primary = Purple40,
//    secondary = PurpleGrey40,
//    tertiary = Pink40
//
//    /* Other default colors to override
//    background = Color(0xFFFFFBFE),
//    surface = Color(0xFFFFFBFE),
//    onPrimary = Color.White,
//    onSecondary = Color.White,
//    onTertiary = Color.White,
//    onBackground = Color(0xFF1C1B1F),
//    onSurface = Color(0xFF1C1B1F),
//    */
//)

@Composable
fun FinanceTrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
//    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {

//    TODO: --> return this code in life action
//    val view = LocalView.current
//    SideEffect {
//        val window = (view.context as Activity).window
//        window.statusBarColor = DarkColorScheme.background.toArgb()
//        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
//    }

    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
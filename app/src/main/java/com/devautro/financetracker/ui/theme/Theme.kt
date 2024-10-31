package com.devautro.financetracker.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
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
    tertiary = CancelButton,
    onTertiary = UnChosenTextColor,
    background = BackgroundColor,
    onBackground = OnBackgroundColor,
    primaryContainer = SurfaceColor,
    onPrimaryContainer = BackgroundColor,
    errorContainer = DarkRedCircle,
    onErrorContainer = OnBackgroundColor,
    error = LightRed
)

// Mock Light Theme -->
private val LightColorScheme = lightColorScheme(
    primary = LightSecondary,
    onPrimary = LightOnBackground,
    secondary = LightAccentBlue,
    onSecondary = LightOnAccentBlue,
    tertiary = LightCancelButton,
    onTertiary = LightUnChosenTextColor,
    background = LightBackground,
    onBackground = LightOnBackground,
    primaryContainer = LightSurfaceColor,
    onPrimaryContainer = Color.Black,
    errorContainer = DarkRedCircle,
    onErrorContainer = OnBackgroundColor,
    error = DarkRedCircle

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun FinanceTrackerTheme(
    darkTheme: Boolean,
    // Dynamic color is available on Android 12+
//    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    SideEffect {
        val window = (view.context as Activity).window
        window.statusBarColor = colorScheme.background.toArgb()
//        window.navigationBarColor = colorScheme.background.toArgb()
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
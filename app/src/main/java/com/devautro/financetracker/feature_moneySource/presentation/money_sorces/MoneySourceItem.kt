package com.devautro.financetracker.feature_moneySource.presentation.money_sorces

import androidx.compose.ui.graphics.toArgb
import com.devautro.financetracker.core.util.Const

data class MoneySourceItem(
    val id: Long? = null,
    val name: String = "",
    val amount: Double = 0.0,
    val paleColor: Int = paleColors[currentIndex].toArgb(),
    val accentColor: Int = accentColors[currentIndex].toArgb(),
    val isRevealed: Boolean = false
) {
    companion object {
        val paleColors = Const.sourcePaleColors
        val accentColors = Const.sourceAccentColors

        val currentIndex = paleColors.indices.random()
    }
}

package com.devautro.financetracker.feature_moneySource.presentation.add_edit_money_source

import androidx.compose.ui.graphics.toArgb
import com.devautro.financetracker.core.util.Const

data class AddEditMoneySourceState(
    val id: Long? = null,
    val name: String = "",
    val amount: String = "",
    val paleColor: Int = paleColors[currentIndex].toArgb(),
    val accentColor: Int = accentColors[currentIndex].toArgb(),
    val includedInTotal: Boolean = true
) {
    companion object {
        val paleColors = Const.sourcePaleColors
        val accentColors = Const.sourceAccentColors

        val currentIndex = paleColors.indices.random()
    }
}

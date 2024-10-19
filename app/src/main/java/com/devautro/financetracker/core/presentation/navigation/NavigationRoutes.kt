package com.devautro.financetracker.core.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object Home

//@Serializable
//object AddBottomSheet

@Serializable
object Incomes

@Serializable
object Expenses

@Serializable
object Charts

@Serializable
object Accounts

@Serializable
object AddAccount

@Serializable
data class EditAccount(val id: Long)

@Serializable
object Settings

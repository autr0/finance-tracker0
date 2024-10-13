package com.devautro.financetracker.feature_moneySource.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MoneySourceEntity(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    val name: String,
    val amount: Double,
    val paleColor: Int,
    val accentColor: Int
)

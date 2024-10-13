package com.devautro.financetracker.feature_moneySource.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.devautro.financetracker.feature_moneySource.data.model.MoneySourceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoneySourceDao {

    @Query("SELECT * FROM MoneySourceEntity")
    fun getMoneySources(): Flow<List<MoneySourceEntity>>

    @Query("SELECT * FROM MoneySourceEntity WHERE id = :id")
    suspend fun getMoneySourceById(id: Long): MoneySourceEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoneySource(moneySourceEntity: MoneySourceEntity)

    @Delete
    suspend fun deleteMoneySource(moneySourceEntity: MoneySourceEntity)
}
package com.devautro.financetracker.feature_payment.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.devautro.financetracker.feature_payment.data.model.PaymentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PaymentDao {

    @Query("SELECT * FROM paymentEntity WHERE isExpense = :isExpense")
    fun getCertainTypeOfPayments(isExpense: Boolean): Flow<List<PaymentEntity>>

    @Query("SELECT * FROM PaymentEntity WHERE id = :id")
    suspend fun getPaymentById(id: Long): PaymentEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPayment(paymentEntity: PaymentEntity)

    @Delete
    suspend fun deletePayment(paymentEntity: PaymentEntity)

    @Update
    suspend fun updatePayment(paymentEntity: PaymentEntity)

    /* TODO --> Delete all data from the table? */
}
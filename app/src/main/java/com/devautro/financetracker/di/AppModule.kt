package com.devautro.financetracker.di

import android.app.Application
import androidx.room.Room
import com.devautro.financetracker.feature_moneySource.data.repository.MoneySourceRepositoryImpl
import com.devautro.financetracker.feature_moneySource.domain.repository.MoneySourceRepository
import com.devautro.financetracker.feature_moneySource.domain.use_case.AddMoneySourceUseCase
import com.devautro.financetracker.feature_moneySource.domain.use_case.DeleteMoneySourceUseCase
import com.devautro.financetracker.feature_moneySource.domain.use_case.GetAllMoneySourcesUseCase
import com.devautro.financetracker.feature_moneySource.domain.use_case.GetMoneySourceUseCase
import com.devautro.financetracker.feature_moneySource.domain.use_case.MoneySourceUseCases
import com.devautro.financetracker.feature_payment.data.data_source.PaymentsDatabase
import com.devautro.financetracker.feature_payment.data.repository.PaymentRepositoryImpl
import com.devautro.financetracker.feature_payment.domain.repository.PaymentRepository
import com.devautro.financetracker.feature_payment.domain.use_case.AddPaymentUseCase
import com.devautro.financetracker.feature_payment.domain.use_case.DeletePaymentUseCase
import com.devautro.financetracker.feature_payment.domain.use_case.GetExpensesUseCase
import com.devautro.financetracker.feature_payment.domain.use_case.GetIncomesUseCase
import com.devautro.financetracker.feature_payment.domain.use_case.GetPaymentUseCase
import com.devautro.financetracker.feature_payment.domain.use_case.PaymentUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): PaymentsDatabase {
        return Room.databaseBuilder(
            app.applicationContext,
            PaymentsDatabase::class.java,
            PaymentsDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providePaymentRepository(db: PaymentsDatabase): PaymentRepository {
        return PaymentRepositoryImpl(db.paymentDao)
    }

    @Provides
    @Singleton
    fun provideMoneySourceRepository(db: PaymentsDatabase): MoneySourceRepository {
        return MoneySourceRepositoryImpl(db.moneySourceDao)
    }

    @Provides
    @Singleton
    fun providePaymentUseCases(repository: PaymentRepository): PaymentUseCases {
        return PaymentUseCases(
            getIncomesUseCase = GetIncomesUseCase(repository),
            getExpensesUseCase = GetExpensesUseCase(repository),
            getPaymentUseCase = GetPaymentUseCase(repository),
            addPaymentUseCase = AddPaymentUseCase(repository),
            deletePaymentUseCase = DeletePaymentUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideMoneySourceUseCases(repository: MoneySourceRepository): MoneySourceUseCases {
        return MoneySourceUseCases(
            getAllMoneySourcesUseCase = GetAllMoneySourcesUseCase(repository),
            getMoneySourceUseCase = GetMoneySourceUseCase(repository),
            addMoneySourceUseCase = AddMoneySourceUseCase(repository),
            deleteMoneySourceUseCase = DeleteMoneySourceUseCase(repository)
        )
    }

}
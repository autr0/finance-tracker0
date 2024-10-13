package com.devautro.financetracker.feature_moneySource.domain.use_case

data class MoneySourceUseCases(
    val getAllMoneySourcesUseCase: GetAllMoneySourcesUseCase,
    val getMoneySourceUseCase: GetMoneySourceUseCase,
    val addMoneySourceUseCase: AddMoneySourceUseCase,
    val deleteMoneySourceUseCase: DeleteMoneySourceUseCase
)

package com.example.coinsswap.domain.repository

import com.example.coinsswap.domain.model.CurrencyRate
import com.example.coinsswap.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    fun getCurrencyRatesList(): Flow<Resource<List<CurrencyRate>>>
}
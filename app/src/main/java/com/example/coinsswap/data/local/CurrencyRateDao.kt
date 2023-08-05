package com.example.coinsswap.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.coinsswap.data.local.entity.CurrencyRateEntity

@Dao
interface CurrencyRateDao {

    @Upsert
    suspend fun upsertAll(currencyRate: List<CurrencyRateEntity>)

    @Query("select * from currencyrateentity")
    suspend fun getAllCurrencyRates():List<CurrencyRateEntity>
}
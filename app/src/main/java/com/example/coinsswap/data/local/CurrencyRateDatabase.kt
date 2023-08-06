package com.example.coinsswap.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.coinsswap.data.local.entity.CurrencyRateEntity

@Database(
    entities = [CurrencyRateEntity::class],
    version = 2
)
abstract class CurrencyRateDatabase : RoomDatabase() {

    abstract val currencyRateDao: CurrencyRateDao

}
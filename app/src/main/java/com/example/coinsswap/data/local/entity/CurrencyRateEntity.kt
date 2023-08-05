package com.example.coinsswap.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrencyRateEntity(
    @PrimaryKey
    val id:Int,
    val code:String,
    val name:String,
    val rate:Double
)
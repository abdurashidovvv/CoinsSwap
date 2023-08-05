package com.example.coinsswap.domain.model

data class CurrencyRate(
    val id: Int,
    val code: String,
    val name: String,
    val rate: Double
)

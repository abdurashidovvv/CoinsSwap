package com.example.coinsswap.data.local.entity

import com.example.coinsswap.domain.model.CurrencyRate

fun CurrencyRateEntity.toCurrencyRate(): CurrencyRate {
    return CurrencyRate(
        id = id,
        code = code,
        name = name,
        rate = rate
    )
}

fun CurrencyRate.toCurrencyRateEntity(): CurrencyRateEntity {
    return CurrencyRateEntity(
        id = id,
        code = code,
        name = name,
        rate = rate
    )
}
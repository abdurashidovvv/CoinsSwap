package com.example.coinsswap.data.remote.dto

import com.example.coinsswap.domain.model.CurrencyRate

fun CurrencyDto.toCurrencyRates(): List<CurrencyRate> {
    val list = ArrayList<CurrencyRate>()
    for (currencyDtoItem in this) {
        list.add(
            CurrencyRate(
                id = currencyDtoItem.id,
                code = currencyDtoItem.Ccy,
                name = currencyDtoItem.CcyNm_UZ,
                rate = currencyDtoItem.Rate.toDouble()
            )
        )
    }
    return list
}
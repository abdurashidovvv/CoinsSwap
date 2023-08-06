package com.example.coinsswap.presentation.main_screen

import com.example.coinsswap.domain.model.CurrencyRate

data class MainScreenState(
    val fromCurrencyCode: String = "EUR",
    val toCurrencyCode: String = "USD",
    val fromCurrencyValue: String = "0.00",
    val toCurrencyValue: String = "0.00",
    val selection: SelectionState = SelectionState.FROM,
    val currencyRate: Map<String, CurrencyRate> = emptyMap(),
    val error: String? = null
)

enum class SelectionState {
    FROM,
    TO
}

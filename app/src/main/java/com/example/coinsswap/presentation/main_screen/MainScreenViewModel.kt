package com.example.coinsswap.presentation.main_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinsswap.domain.model.Resource
import com.example.coinsswap.domain.repository.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import javax.inject.Inject


@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val repository: CurrencyRepository
) : ViewModel() {
    var state by mutableStateOf(MainScreenState())

    init {
        getCurrencyLatestList()
    }

    fun onEvent(event: MainScreenEvent) {
        when (event) {
            MainScreenEvent.FromCurrencySelect -> {
                state = state.copy(selection = SelectionState.FROM)
            }

            MainScreenEvent.ToCurrencySelect -> {
                state = state.copy(selection = SelectionState.TO)
            }

            MainScreenEvent.SwapIconClicked -> {
                state.copy(
                    fromCurrencyCode = state.toCurrencyCode,
                    fromCurrencyValue = state.toCurrencyValue,
                    toCurrencyCode = state.fromCurrencyCode,
                    toCurrencyValue = state.fromCurrencyValue
                )
            }

            is MainScreenEvent.NumberButtonClicked -> {
                updateCurrencyValue(event.value)
            }

            is MainScreenEvent.BottomSheetItemClicked -> TODO()
        }
    }

    private fun getCurrencyLatestList() {
        viewModelScope.launch {
            repository
                .getCurrencyRatesList()
                .collectLatest { result ->
                    when (result) {
                        is Resource.Error -> {
                            state = state.copy(
                                currencyRate = result.data?.associateBy { it.code } ?: emptyMap(),
                                error = result.message
                            )
                        }

                        is Resource.Success -> {
                            state = state.copy(
                                currencyRate = result.data?.associateBy { it.code } ?: emptyMap(),
                                error = null
                            )
                        }
                    }

                }
        }
    }

    private fun updateCurrencyValue(value: String) {

        val currentCurrencyValue = when (state.selection) {
            SelectionState.FROM -> state.fromCurrencyValue
            SelectionState.TO -> state.toCurrencyValue
        }

        val fromCurrencyRate = state.currencyRate[state.fromCurrencyCode]?.rate ?: 0.0
        val toCurrencyRate = state.currencyRate[state.toCurrencyCode]?.rate ?: 0.0

        val updatedCurrencyValue = when (value) {
            "C" -> "0.00"
            else -> if (currentCurrencyValue == "0.00") value else currentCurrencyValue + value
        }

        val numberFormat = DecimalFormat("#.00")
        when (state.selection) {
            SelectionState.FROM -> {
                val fromValue = updatedCurrencyValue.toDoubleOrNull() ?: 0.0
                val toValue = fromValue / fromCurrencyRate * toCurrencyRate
                state = state.copy(
                    fromCurrencyValue = updatedCurrencyValue,
                    toCurrencyValue = numberFormat.format(toValue)
                )
            }

            SelectionState.TO -> {
                val toValue = updatedCurrencyValue.toDoubleOrNull() ?: 0.0
                val fromValue = toValue / toCurrencyRate * fromCurrencyRate
                state = state.copy(
                    fromCurrencyValue = updatedCurrencyValue,
                    toCurrencyValue = numberFormat.format(fromValue)
                )
            }
        }
    }
}
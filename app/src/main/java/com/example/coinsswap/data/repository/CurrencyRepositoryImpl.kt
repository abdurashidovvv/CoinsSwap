package com.example.coinsswap.data.repository

import com.example.coinsswap.data.local.CurrencyRateDao
import com.example.coinsswap.data.local.entity.toCurrencyRate
import com.example.coinsswap.data.local.entity.toCurrencyRateEntity
import com.example.coinsswap.data.remote.CurrencyApi
import com.example.coinsswap.data.remote.dto.CurrencyDto
import com.example.coinsswap.data.remote.dto.toCurrencyRates
import com.example.coinsswap.domain.model.CurrencyRate
import com.example.coinsswap.domain.model.Resource
import com.example.coinsswap.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class CurrencyRepositoryImpl(
    private val api: CurrencyApi,
    private val dao: CurrencyRateDao
): CurrencyRepository {

    override fun getCurrencyRatesList(): Flow<Resource<List<CurrencyRate>>> = flow {
        val localCurrencyRates = getLocalCurrencyRates()
        emit(Resource.Success(localCurrencyRates))

        try {
            val newRates = getRemoteCurrencyRates()
            updateLocalCurrencyRates(newRates)
            emit(Resource.Success(newRates))
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Couldn't reach server, check your internet connection",
                    data = localCurrencyRates
                )
            )
        } catch (e: Exception) {
            emit(
                Resource.Error(
                    message = "Oops, something went wrong! ${e.message}",
                    data = localCurrencyRates
                )
            )
        }

    }

    private suspend fun getLocalCurrencyRates(): List<CurrencyRate> {
        return dao.getAllCurrencyRates().map { it.toCurrencyRate() }
    }

    private suspend fun getRemoteCurrencyRates(): List<CurrencyRate> {
        val response = api.getLatestRates()
        return response.toCurrencyRates()
    }

    private suspend fun updateLocalCurrencyRates(currencyRates: List<CurrencyRate>) {
        dao.upsertAll(currencyRates.map { it.toCurrencyRateEntity() })
    }
}
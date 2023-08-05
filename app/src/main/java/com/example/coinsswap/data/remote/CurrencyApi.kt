package com.example.coinsswap.data.remote

import com.example.coinsswap.data.remote.dto.CurrencyDto
import retrofit2.http.GET

interface CurrencyApi {

    @GET("json/")
    suspend fun getLatestRates(): CurrencyDto

    companion object {
        val BASE_URL = "http://cbu.uz/uzc/arkhiv-kursov-valyut/"
    }
}
package com.argz.cryptoapp.data.remote

import com.argz.cryptoapp.domain.Coin
import com.argz.cryptoapp.domain.CoinWrapper
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

const val ALL_COINS_ENDPOINTS = "https://api.coincap.io/v2/assets"

class CryptoApi {
    private val httpClient = HttpClient{
        install(ContentNegotiation){
            json (
               Json {
                   ignoreUnknownKeys = true
               }
            )
        }
    }

    suspend fun fetchAllCoins(): List<Coin> {
        val response: CoinWrapper = httpClient.get(urlString = ALL_COINS_ENDPOINTS).body()
        return response.data
    }
}
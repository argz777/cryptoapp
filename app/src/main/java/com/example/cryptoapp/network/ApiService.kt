package com.example.cryptoapp.network

import com.example.cryptoapp.model.Coins
import retrofit2.http.GET

interface ApiService {
    @GET("v2/assets")
    suspend fun getCoins(): Coins
}
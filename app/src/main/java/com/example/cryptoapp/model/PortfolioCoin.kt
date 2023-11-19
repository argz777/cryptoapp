package com.example.cryptoapp.model

data class PortfolioCoin(
    var color: Long = 0,
    var quantity: Int = 0,
    var coin: Coin? = null,
)
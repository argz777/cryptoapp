package com.example.cryptoapp.model

data class Coins(
    var data        : List<Coin> = emptyList(),
    var timestamp   : Long?      = null,
)
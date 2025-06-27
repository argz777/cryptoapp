package com.argz.cryptoapp.domain

import kotlinx.serialization.Serializable

@Serializable
data class CoinWrapper(
    var data        : List<Coin> = emptyList(),
    var timestamp   : Long?      = null,
)
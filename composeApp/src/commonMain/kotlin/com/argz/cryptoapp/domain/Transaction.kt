package com.argz.cryptoapp.domain

import kotlinx.serialization.Serializable

@Serializable
data class Transaction(
    var id                    : Long = 0,
    var coinId                : String? = null,
    var rank                  : String? = null,
    var symbol                : String? = null,
    var name                  : String? = null,
    var supply                : String? = null,
    var maxSupply             : String? = null,
    var marketCapUsd          : String? = null,
    var volumeUsd24Hr         : String? = null,
    var priceUsd              : String? = null,
    var changePercent24Hr     : String? = null,
    var vwap24Hr              : String? = null,
    var action                : String? = null,
    var quantity              : String? = null,
    var price                 : String? = null,
    var transactionDate       : String? = null,
)
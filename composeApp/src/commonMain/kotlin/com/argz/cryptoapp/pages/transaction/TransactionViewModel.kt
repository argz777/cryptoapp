package com.argz.cryptoapp.pages.transaction

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.argz.cryptoapp.data.CryptoSDK
import com.argz.cryptoapp.domain.Coin
import com.argz.cryptoapp.domain.Transaction


class TransactionViewModel(
    private val sdk: CryptoSDK
): ViewModel() {
    var selectedCoin: MutableState<Coin?> = mutableStateOf(null)
        private set

    fun addTransaction(
        transaction: Transaction
    ){
        sdk.insertTransaction(transaction)
    }
}
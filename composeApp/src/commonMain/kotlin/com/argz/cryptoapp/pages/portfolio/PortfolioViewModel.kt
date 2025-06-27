package com.argz.cryptoapp.pages.portfolio

import androidx.lifecycle.ViewModel
import com.argz.cryptoapp.data.CryptoSDK
import com.argz.cryptoapp.domain.Transaction
import kotlinx.coroutines.flow.MutableStateFlow

typealias CoinTransactions = MutableMap<String, List<Transaction>>

class PortfolioViewModel(
    private val sdk: CryptoSDK
) : ViewModel() {

    var processedTransactions: MutableStateFlow<CoinTransactions> = MutableStateFlow(hashMapOf())
        private set
    var allTransactions: MutableStateFlow<List<Transaction>> = MutableStateFlow(listOf())
        private set

    init {
        refreshTransactions()
    }

    fun refreshTransactions(){
        allTransactions.value = sdk.getAllTransactions()
        generateMappingTransactionForCoins(allTransactions.value)
    }

    fun generateMappingTransactionForCoins(transactions: List<Transaction>) {
        val mapping = mutableMapOf<String, List<Transaction>>()

        transactions.forEach { transaction ->
            val existingTransactions = processedTransactions.value[transaction.coinId].orEmpty()
            mapping[transaction.coinId!!] = existingTransactions + transaction
        }

        processedTransactions.value = mapping
    }
}
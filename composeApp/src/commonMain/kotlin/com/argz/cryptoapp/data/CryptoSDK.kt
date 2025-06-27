package com.argz.cryptoapp.data

import com.argz.cryptoapp.data.local.LocalDatabase
import com.argz.cryptoapp.data.remote.CryptoApi
import com.argz.cryptoapp.domain.Coin
import com.argz.cryptoapp.domain.RequestState
import com.argz.cryptoapp.domain.Transaction

class CryptoSDK(
    private val api: CryptoApi,
    private val database: LocalDatabase,
) {
    @Throws(Exception::class)
    suspend fun getAllCoins(): RequestState<List<Coin>> {
        return try {
            RequestState.Success(
                api.fetchAllCoins()
            )
        } catch (e: Exception){
            RequestState.Error(e.message.toString())
        }
    }

    fun getFavoriteCoins(): List<Coin> {
        return database.readAllCoins()
    }

    fun getCoin(id: String): Coin? {
        return database.findCoin(id)
    }

    fun setFavorite(coin: Coin) {
        database.insertCoin(coin)
    }

    fun unsetFavorite(coin: Coin){
        database.removeCoin(coin.id!!)
    }

    fun getAllTransactions(): List<Transaction> {
        return database.readAllTransactions()
    }

    fun getAllTransactions(symbol: String): List<Transaction> {
        return database.readAllTransactions(symbol)
    }

    fun insertTransaction(transaction: Transaction) {
        database.insertTransaction(transaction)
    }

    fun deleteTransaction(transaction: Transaction) {
        database.removeTransaction(transaction.id)
    }
}
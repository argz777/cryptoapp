package com.argz.cryptoapp.data.local

import app.cash.sqldelight.db.SqlDriver
import com.argz.CoinDatabase
import com.argz.cryptoapp.domain.Coin
import com.argz.cryptoapp.domain.Transaction

interface DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}

class LocalDatabase(
    databaseDriverFactory: DatabaseDriverFactory
) {
    private val database = CoinDatabase(
        databaseDriverFactory.createDriver()
    )
    private val query = database.coinDatabaseQueries

    fun readAllCoins(): List<Coin> {
        println("INFO: Reading the cached data from the local database...")
        return query.readAllCoins()
            .executeAsList()
            .map {
                Coin(
                    id = it.id,
                    rank = it.rank,
                    symbol = it.symbol,
                    name = it.name,
                    supply = it.supply,
                    maxSupply = it.maxSupply,
                    marketCapUsd = it.marketCapUsd,
                    volumeUsd24Hr = it.volumeUsd24Hr,
                    priceUsd = it.priceUsd,
                    changePercent24Hr = it.changePercent24Hr,
                    vwap24Hr = it.vwap24Hr
                )
            }
    }

    fun findCoin(id: String): Coin? {
        val coinTable = query.readCoin(id).executeAsOneOrNull()

        if(coinTable?.id == null) {
            return null
        } else {
            return Coin(
                id = coinTable.id,
                rank = coinTable.rank,
                symbol = coinTable.symbol,
                name = coinTable.name,
                supply = coinTable.supply,
                maxSupply = coinTable.maxSupply,
                marketCapUsd = coinTable.marketCapUsd,
                volumeUsd24Hr = coinTable.volumeUsd24Hr,
                priceUsd = coinTable.priceUsd,
                changePercent24Hr = coinTable.changePercent24Hr,
                vwap24Hr = coinTable.vwap24Hr
            )
        }
    }

    fun insertCoin(it: Coin) {
        println("INFO: Inserting coin the data...")
        query.insertCoin(
            id = it.id ?: "",
            rank = it.rank ?: "",
            symbol = it.symbol ?: "",
            name = it.name ?: "",
            supply = it.supply ?: "",
            maxSupply = it.maxSupply ?: "",
            marketCapUsd = it.marketCapUsd ?: "",
            volumeUsd24Hr = it.volumeUsd24Hr ?: "",
            priceUsd = it.priceUsd ?: "",
            changePercent24Hr = it.changePercent24Hr ?: "",
            vwap24Hr = it.vwap24Hr ?: ""
        )
    }

    fun removeCoin(id: String){
        println("INFO: Remove data from cache...")
        query.removeCoin(id)
    }

    fun removeAllCoins() {
        println("INFO: Clearing all data from cache...")
        query.removeAllCoins()
    }

    fun readAllTransactions(): List<Transaction> {
        println("INFO: Reading the all transactions from the local database...")
        return query.readAllTransactions()
            .executeAsList()
            .map {
                Transaction(
                    id = it.id,
                    coinId = it.coinId,
                    rank = it.rank,
                    symbol = it.symbol,
                    name = it.name,
                    supply = it.supply,
                    maxSupply = it.maxSupply,
                    marketCapUsd = it.marketCapUsd,
                    volumeUsd24Hr = it.volumeUsd24Hr,
                    priceUsd = it.priceUsd,
                    changePercent24Hr = it.changePercent24Hr,
                    vwap24Hr = it.vwap24Hr,
                    action = it.action,
                    quantity = it.quantity,
                    price = it.price,
                    transactionDate = it.transactionDate,
                )
            }
    }

    fun readAllTransactions(symbol: String): List<Transaction> {
        println("INFO: Reading the all transactions from the local database...")
        return query.readAllTransactionsOfCoin(symbol)
            .executeAsList()
            .map {
                Transaction(
                    id = it.id,
                    coinId = it.coinId,
                    rank = it.rank,
                    symbol = it.symbol,
                    name = it.name,
                    supply = it.supply,
                    maxSupply = it.maxSupply,
                    marketCapUsd = it.marketCapUsd,
                    volumeUsd24Hr = it.volumeUsd24Hr,
                    priceUsd = it.priceUsd,
                    changePercent24Hr = it.changePercent24Hr,
                    vwap24Hr = it.vwap24Hr,
                    action = it.action,
                    quantity = it.quantity,
                    price = it.price,
                    transactionDate = it.transactionDate,
                )
            }
    }

    fun insertTransaction(it: Transaction) {
        println("INFO: Inserting transaction the data...")
        query.insertTransaction(
            coinId = it.coinId ?: "",
            rank = it.rank ?: "",
            symbol = it.symbol ?: "",
            name = it.name ?: "",
            supply = it.supply ?: "",
            maxSupply = it.maxSupply ?: "",
            marketCapUsd = it.marketCapUsd ?: "",
            volumeUsd24Hr = it.volumeUsd24Hr ?: "",
            priceUsd = it.priceUsd ?: "",
            changePercent24Hr = it.changePercent24Hr ?: "",
            vwap24Hr = it.vwap24Hr ?: "",
            action = it.action ?: "",
            quantity = it.quantity ?: "",
            price = it.price ?: "",
            transactionDate = it.transactionDate ?: "",
        )
    }

    fun removeTransaction(id: Long){
        println("INFO: Remove transaction from cache...")
        query.removeTransaction(id)
    }

    fun removeAllTransactions() {
        println("INFO: Clearing all transactions from cache...")
        query.removeAllTransactions()
    }
}
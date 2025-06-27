package com.argz.cryptoapp.data.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.argz.CoinDatabase

class IOSDatabaseDriverFactory: DatabaseDriverFactory {
    override fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            CoinDatabase.Schema,
            "coin.db"
        )
    }
}
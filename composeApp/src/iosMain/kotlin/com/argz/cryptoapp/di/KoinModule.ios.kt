package com.argz.cryptoapp.di

import com.argz.cryptoapp.data.local.DatabaseDriverFactory
import com.argz.cryptoapp.data.local.IOSDatabaseDriverFactory
import org.koin.dsl.module

actual val targetModule = module {
    single<DatabaseDriverFactory> { IOSDatabaseDriverFactory() }
}
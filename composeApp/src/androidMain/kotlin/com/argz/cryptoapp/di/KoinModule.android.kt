package com.argz.cryptoapp.di

import com.argz.cryptoapp.data.local.AndroidDatabaseDriverFactory
import com.argz.cryptoapp.data.local.DatabaseDriverFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val targetModule = module {
    single<DatabaseDriverFactory> { AndroidDatabaseDriverFactory(androidContext()) }
}
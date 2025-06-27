package com.argz.cryptoapp

import android.app.Application
import com.argz.cryptoapp.di.initializeKoin
import org.koin.android.ext.koin.androidContext

class CustomApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initializeKoin(
            config = { androidContext(this@CustomApplication) }
        )
    }
}
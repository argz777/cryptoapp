package com.argz.cryptoapp.di

import com.argz.cryptoapp.MainViewModel
import com.argz.cryptoapp.data.CryptoSDK
import com.argz.cryptoapp.data.LoginSDK
import com.argz.cryptoapp.data.local.LocalDatabase
import com.argz.cryptoapp.data.remote.CryptoApi
import com.argz.cryptoapp.pages.details.DetailViewModel
import com.argz.cryptoapp.pages.login.LoginViewModel
import com.argz.cryptoapp.pages.portfolio.PortfolioViewModel
import com.argz.cryptoapp.pages.profile.ProfileViewModel
import com.argz.cryptoapp.pages.transaction.TransactionViewModel
import com.argz.cryptoapp.pages.transaction.selectCoin.SelectCoinViewModel
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

expect val targetModule: Module

val sharedModule = module {
    single<CryptoApi> { CryptoApi() }
    single<LocalDatabase> { LocalDatabase(get()) }
    single<FirebaseAuth> { Firebase.auth }
    single<FirebaseFirestore> { Firebase.firestore }
    single<LoginSDK> { LoginSDK(get(), get()) }
    single<CryptoSDK> {
        CryptoSDK(
            api = get(),
            database = get()
        )
    }
    viewModel { MainViewModel(sdk = get()) }
    viewModel { DetailViewModel(sdk = get()) }
    viewModel { PortfolioViewModel(sdk = get()) }
    viewModel { LoginViewModel(sdk = get()) }
    viewModel { ProfileViewModel(sdk = get()) }
    viewModel { TransactionViewModel(sdk = get()) }
    viewModel { SelectCoinViewModel() }
}

fun initializeKoin(config: (KoinApplication.() -> Unit)? = null) {
    startKoin {
        config?.invoke(this)
        modules(targetModule, sharedModule)
    }
}
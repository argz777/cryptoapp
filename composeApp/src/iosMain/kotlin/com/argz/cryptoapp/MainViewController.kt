package com.argz.cryptoapp

import androidx.compose.ui.window.ComposeUIViewController
import com.argz.cryptoapp.di.initializeKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initializeKoin()
    }
) {
    App()
}
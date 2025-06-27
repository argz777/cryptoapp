package com.argz.cryptoapp.navigation

sealed class Screen(val route: String) {
    data object Login: Screen(route = "login_screen")
    data object Home: Screen(route = "home_screen")
    data object Details: Screen(route = "details_screen")
    data object Portfolio: Screen(route = "portfolio_screen")
    data object Profile: Screen(route = "profile_screen")
    data object AddTransaction: Screen(route = "add_transaction_screen")
    data object SelectCoin: Screen(route = "select_coin_screen")
}
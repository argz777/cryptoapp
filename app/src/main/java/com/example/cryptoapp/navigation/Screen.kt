package com.example.cryptoapp.navigation



const val MAIN_GRAPH_ROUTE = "main"

sealed class Screen(val route: String) {
    object CoinsListScreen: Screen("coins_list_screen")
    object PortfolioScreen: Screen("portfolioscreen")
    object CoinScreen: Screen("coin_screen")
}
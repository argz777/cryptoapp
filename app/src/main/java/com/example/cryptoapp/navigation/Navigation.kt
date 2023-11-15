package com.example.cryptoapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cryptoapp.MainViewModel
import com.example.cryptoapp.pages.coinslist.CoinsListPage
import com.example.cryptoapp.pages.portfolio.PortfolioPage


@Composable
fun Navigation() {
    val navigationController = rememberNavController()

    NavHost(
        navController = navigationController,
        startDestination = Screen.CoinsListScreen.route,
        route = MAIN_GRAPH_ROUTE
    ) {
        composable(Screen.CoinsListScreen.route){
            val parent = remember(it){
                navigationController.getBackStackEntry(MAIN_GRAPH_ROUTE)
            }
            val mainViewModel = hiltViewModel<MainViewModel>(parent)
            CoinsListPage(navController = navigationController, mainViewModel = mainViewModel)
        }
        composable(Screen.PortfolioScreen.route){
            val parent = remember(it){
                navigationController.getBackStackEntry(MAIN_GRAPH_ROUTE)
            }
            val mainViewModel = hiltViewModel<MainViewModel>(parent)
            PortfolioPage(navController = navigationController, mainViewModel = mainViewModel)
        }
    }
}

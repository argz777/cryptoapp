package com.argz.cryptoapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.argz.cryptoapp.MainViewModel
import com.argz.cryptoapp.pages.details.DetailsPage
import com.argz.cryptoapp.pages.home.HomePage
import com.argz.cryptoapp.pages.login.LoginPage
import com.argz.cryptoapp.pages.login.LoginViewModel
import com.argz.cryptoapp.pages.portfolio.PortfolioViewModel
import com.argz.cryptoapp.pages.profile.ProfilePage
import com.argz.cryptoapp.pages.transaction.AddTransactionPage
import com.argz.cryptoapp.pages.transaction.TransactionViewModel
import com.argz.cryptoapp.pages.transaction.selectCoin.SelectCoinPage
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun Navigation(navController: NavHostController) {
    val mainViewModel = koinViewModel<MainViewModel>()
    val loginViewModel = koinViewModel<LoginViewModel>()
    val transactionViewModel = koinViewModel<TransactionViewModel>()
    val portfolioViewModel = koinViewModel<PortfolioViewModel>()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route,
    ) {
        composable(route = Screen.Login.route){
            LoginPage(
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route){
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                loginViewModel = loginViewModel,
            )
        }
        composable(route = Screen.Home.route){
            HomePage(
                onBackPress = { navController.navigateUp() } ,
                onCoinSelect = {
                    mainViewModel.selectedCoin.value = it
                    navController.navigate(Screen.Details.route)
                },
                navigateToSelectCoin = {
                    navController.navigate(Screen.SelectCoin.route)
                },
                onAccountClick = {
                    navController.navigate(Screen.Profile.route)
                },
                mainViewModel = mainViewModel,
                portfolioViewModel = portfolioViewModel,
            )
        }
        composable(route = Screen.SelectCoin.route){
            SelectCoinPage(
                onSelect = { coin ->
                    transactionViewModel.selectedCoin.value = coin
                    navController.navigate(Screen.AddTransaction.route)
                },
                onBackPress = { navController.navigateUp() },
                mainViewModel = mainViewModel,
            )
        }
        composable(route = Screen.AddTransaction.route){
            AddTransactionPage(
                onBackPress = { navController.navigateUp() },
                transactionViewModel = transactionViewModel,
                onTransactionInsert = { transaction ->
                    transactionViewModel.addTransaction(transaction)
                    portfolioViewModel.refreshTransactions()
                    navController.navigate(Screen.Home.route){
                        popUpTo(Screen.SelectCoin.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(route = Screen.Details.route){
            DetailsPage(
                onBackPress = { navController.navigateUp() },
                mainViewModel = mainViewModel,
            )
        }
        composable(route = Screen.Profile.route){
            ProfilePage(
                onBackPress = { navController.navigateUp() },
                onLogout = {
                    loginViewModel.logout()
                    loginViewModel.resetState()

                    navController.navigate(Screen.Login.route){
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
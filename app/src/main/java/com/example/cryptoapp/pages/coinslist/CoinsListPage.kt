package com.example.cryptoapp.pages.coinslist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.cryptoapp.MainViewModel
import com.example.cryptoapp.model.Resource
import com.example.cryptoapp.navigation.Screen
import com.example.cryptoapp.pages.coinslist.components.CoinContent

@Composable
fun CoinsListPage(
    navController: NavController,
    mainViewModel: MainViewModel
) {
    val coins = mainViewModel.coins.collectAsState()

    Scaffold(
        floatingActionButton = {
            coins.value.let { res ->
                when(res) {
                    is Resource.Failure -> {}
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        FloatingActionButton(
                            onClick = {
                                navController.navigate(Screen.PortfolioScreen.route)
                            },
                        ) {
                            Icon(Icons.Filled.List, "Portfolio")
                        }
                    }
                }
            }
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            coins.value.let { res ->
                when(res) {
                    is Resource.Failure -> {}
                    is Resource.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    is Resource.Success -> {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            res.result.forEach {
                                item(key = it.id) {
                                    CoinContent(
                                        coin = it
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
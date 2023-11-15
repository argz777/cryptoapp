package com.example.cryptoapp.pages.coinslist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.cryptoapp.MainViewModel
import com.example.cryptoapp.navigation.Screen
import com.example.cryptoapp.pages.coinslist.components.CoinContent

@Composable
fun CoinsListPage(
    navController: NavController,
    mainViewModel: MainViewModel
) {
    val coins by mainViewModel.coins.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.PortfolioScreen.route)
                },
            ) {
                Icon(Icons.Filled.List, "Portfolio")
            }
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            if(coins.isNotEmpty()){
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    coins.forEach {
                        item(key = it.id) {
                            CoinContent(coin = it)
                        }
                    }
                }
            }
        }
    }
}
package com.example.cryptoapp.pages.portfolio.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.cryptoapp.MainViewModel
import com.example.cryptoapp.model.PortfolioCoin


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCoinDialog(
    showAddCoinDialog: MutableState<Boolean>,
    mainViewModel: MainViewModel,
){
    val coins by mainViewModel.coins.collectAsState()
    var expanded = remember { mutableStateOf(false) }
    val selectedOption = remember { mutableStateOf(coins[0]) }
    var quantity = remember { mutableIntStateOf(0) }

    CustomAlertDialog(
        flag = showAddCoinDialog,
        icon = Icons.Filled.Add,
        title = "Add a Coin",
        onConfirmText = "Add",
        onConfirm = {
            mainViewModel.addCoin(
                PortfolioCoin(
                    quantity = quantity.value,
                    coin = selectedOption.value,
                )
            )
            showAddCoinDialog.value = false
        },
        expanded = expanded,
        selectedOption = selectedOption,
        quantity = quantity,
        coins = coins,
    )
}


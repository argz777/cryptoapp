package com.example.cryptoapp.pages.portfolio.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.cryptoapp.MainViewModel
import com.example.cryptoapp.model.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteCoinDialog(
    showDeleteCoinDialog: MutableState<Boolean>,
    mainViewModel: MainViewModel,
){
    val coins by mainViewModel.coins.collectAsState()
    var expanded = remember { mutableStateOf(false) }
    val selectedOption = remember { mutableStateOf(mainViewModel.selectedCoin!!.coin!!) }
    var quantity = remember { mutableStateOf(mainViewModel.selectedCoin!!.quantity) }

    CustomAlertDialog(
        flag = showDeleteCoinDialog,
        icon = Icons.Filled.Delete,
        title = "Delete a Coin",
        onConfirmText = "Delete",
        onConfirm = {
            mainViewModel.deleteCoin()
            showDeleteCoinDialog.value = false
        },
        enabled = false,
        expanded = expanded,
        selectedOption = selectedOption,
        quantity = quantity,
        coins = (coins as Resource.Success).result,
    )
}

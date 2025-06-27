package com.argz.cryptoapp.pages.transaction

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.argz.cryptoapp.domain.Transaction
import com.argz.cryptoapp.getCurrentDate
import com.argz.cryptoapp.pages.transaction.selectCoin.components.CoinListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionPage(
    transactionViewModel: TransactionViewModel,
    onBackPress: () -> Unit,
    onTransactionInsert: (Transaction) -> Unit,
) {
    val selectedCoin by transactionViewModel.selectedCoin
    val transactionType = listOf("Buy", "Sell")
    val price = mutableStateOf(0.0f)
    val quantity = mutableStateOf(0)
    val currentDate = mutableStateOf(getCurrentDate())
    val currentType = mutableStateOf(transactionType[0])

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onBackPress()
                        }
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
        ) {
            Card(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            ) {
                CoinListItem(
                    selectedCoin!!,
                    onCoinSelect = {
                        onBackPress()
                    },
                )
            }
            Row(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                    .fillMaxWidth()
            ) {
                transactionType.forEach { type ->
                    FilterChip(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .animateContentSize(),
                        onClick = {
                            currentType.value = type
                        },
                        label = {
                            Text(type)
                        },
                        selected = (type == currentType.value),
                    )
                }

                Spacer(
                    modifier = Modifier.weight(1f)
                )

                AssistChip(
                    onClick = {  },
                    label = { Text(currentDate.value) },
                    trailingIcon = {
                        Icon(
                            Icons.Filled.DateRange,
                            contentDescription = "Date Range",
                            Modifier.size(AssistChipDefaults.IconSize)
                        )
                    }
                )
            }

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp, start = 16.dp, end = 16.dp),
                label = { Text(text = "Price") },
                value = "${price.value}",
                onValueChange = { value -> price.value = value.toFloat() }
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp, start = 16.dp, end = 16.dp),
                label = { Text(text = "Quantity") },
                value = "${quantity.value}",
                onValueChange = { value -> quantity.value = value.toInt() }
            )

            Spacer(
                modifier = Modifier.weight(1f)
            )

            Button(
                shape = RoundedCornerShape(10),
                modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp),
                onClick = {
                    onTransactionInsert(
                        Transaction(
                            coinId = selectedCoin!!.id,
                            rank = selectedCoin!!.rank,
                            symbol = selectedCoin!!.symbol,
                            name = selectedCoin!!.name,
                            supply = selectedCoin!!.supply,
                            maxSupply = selectedCoin!!.maxSupply,
                            marketCapUsd = selectedCoin!!.marketCapUsd,
                            volumeUsd24Hr = selectedCoin!!.volumeUsd24Hr,
                            priceUsd = selectedCoin!!.priceUsd,
                            changePercent24Hr = selectedCoin!!.changePercent24Hr,
                            vwap24Hr = selectedCoin!!.vwap24Hr,
                            action = currentType.value,
                            quantity = "${quantity.value}",
                            price = "${price.value}",
                            transactionDate = currentDate.value,
                        )
                    )
                }
            ) {
                Text("Add Transaction")
            }
        }
    }
}
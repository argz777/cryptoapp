package com.argz.cryptoapp.pages.portfolio

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.argz.cryptoapp.computeTotalValue
import com.argz.cryptoapp.formatCurrency
import com.argz.cryptoapp.pages.portfolio.components.CoinWithTransactionContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortfolioPage(
    portfolioViewModel: PortfolioViewModel,
    navigateToSelectCoin: () -> Unit,
) {
    val coinsWithTransactions by portfolioViewModel.processedTransactions.collectAsState()
    val allTransactions by portfolioViewModel.allTransactions.collectAsState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            textAlign = TextAlign.Center,
                            text = "Balance: $${formatCurrency(computeTotalValue(allTransactions))}"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
    ) {
        Column(
            modifier = Modifier
                .padding(top = it.calculateTopPadding(), start = 16.dp, end = 16.dp)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
            ) {
                coinsWithTransactions.forEach { coin ->
                    item(key = coin.key) {
                        Row(modifier = Modifier.animateItem().padding(top = 8.dp)){
                            CoinWithTransactionContent(
                                transactions = coin.value
                            )
                        }
                    }
                }
            }
            Spacer(
                modifier = Modifier.weight(1f)
            )

            Button(
                shape = RoundedCornerShape(10),
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    navigateToSelectCoin()
                }
            ) {
                Text(
                    text = "Add a transaction"
                )
            }
        }
    }
}
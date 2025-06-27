package com.argz.cryptoapp.pages.portfolio.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.argz.cryptoapp.computeTotalQuantity
import com.argz.cryptoapp.computeTotalValue
import com.argz.cryptoapp.domain.Transaction
import com.argz.cryptoapp.formatCurrency


@Composable
fun CoinWithTransactionContent(
    transactions: List<Transaction>
) {
    val coin = remember { transactions[0] }

    Card(
        modifier = Modifier.height(80.dp)
            .fillMaxWidth()
            .clickable{

            },
    ) {
        Column {
            Row(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AsyncImage(
                    modifier = Modifier.size(40.dp).padding(4.dp),
                    model = "https://assets.coincap.io/assets/icons/${coin.symbol!!.lowercase()}@2x.png",
                    contentDescription = null,
                )
                Text(
                    modifier = Modifier.padding(8.dp),
                    textAlign = TextAlign.Center,
                    text = "${coin.symbol}",
                    fontWeight = FontWeight.Bold
                )
                Spacer(
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "$${formatCurrency(computeTotalValue(transactions))}",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                )
            }
            Row(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier
                        .padding(8.dp),
                    text = "${coin.name}"
                )
                Spacer(
                    modifier = Modifier.weight(1f)
                )
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = formatCurrency(computeTotalQuantity(transactions))
                )
            }
        }
    }
}
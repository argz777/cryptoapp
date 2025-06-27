package com.argz.cryptoapp.pages.transaction.selectCoin.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.argz.cryptoapp.domain.Coin


@Composable
fun CoinListItem(
    coin: Coin,
    onCoinSelect: (Coin) -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .height(40.dp)
            .fillMaxWidth()
            .clickable{
                onCoinSelect(coin)
            },
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
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            text = "${coin.name}"
        )
    }
}

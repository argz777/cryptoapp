package com.example.cryptoapp.pages.coinslist.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cryptoapp.model.Coin
import com.example.cryptoapp.ui.theme.CryptoappTheme
import java.text.DecimalFormat

@Composable
fun CoinContent(
    coin: Coin
) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .height(40.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            textAlign = TextAlign.Center,
            text = "${coin.rank}")
        Text(
            modifier = Modifier.padding(8.dp),
            textAlign = TextAlign.Center,
            text = "${coin.symbol}")
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            text = "${coin.name}")
        Text(
            modifier = Modifier
                .padding(8.dp),
            textAlign = TextAlign.End,
            text = "$" + DecimalFormat("#,###.00").format(coin.priceUsd!!.toDouble()))
    }
}

@Composable
@Preview
fun CoinContentPreview(){
    CryptoappTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                CoinContent(coin = Coin(
                    id = "bitcoin",
                    rank = "1",
                    symbol = "BTC",
                    name = "Bitcoin",
                    supply = "17193925.0000000000000000",
                    maxSupply = "21000000.0000000000000000",
                    marketCapUsd = "119150835874.4699281625807300",
                    volumeUsd24Hr = "2927959461.1750323310959460",
                    priceUsd = "6929.8217756835584756",
                    changePercent24Hr = "-0.8101417214350335",
                    vwap24Hr = "7175.0663247679233209",
                ))
            }
        }
    }
}


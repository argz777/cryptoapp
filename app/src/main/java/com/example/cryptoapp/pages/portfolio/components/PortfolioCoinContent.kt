package com.example.cryptoapp.pages.portfolio.components

import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cryptoapp.model.Coin
import com.example.cryptoapp.model.PortfolioCoin
import com.example.cryptoapp.ui.theme.CryptoappTheme
import java.text.DecimalFormat


@Composable
fun PortfolioCoinContent(
    portfolioCoin: PortfolioCoin,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .height(60.dp)
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            textAlign = TextAlign.Start,
            text = "${portfolioCoin.coin!!.name}(${portfolioCoin.coin!!.symbol})\n${portfolioCoin.quantity} ${portfolioCoin.coin!!.symbol!!.uppercase()}")
        Text(
            modifier = Modifier
                .padding(8.dp)
                .weight(1f),
            textAlign = TextAlign.End,
            text = "$" + DecimalFormat("#,###.00").format(portfolioCoin.coin!!.priceUsd!!.toDouble() * portfolioCoin.quantity))
    }
}


@Composable
@Preview
fun PortfolioCoinContentPreview(){
    val showUpdateCoinDialog = remember { mutableStateOf(false) }
    CryptoappTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                PortfolioCoinContent(
                    portfolioCoin = PortfolioCoin(
                        quantity = 5,
                        coin = Coin(
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
                        )
                    ),
                    onClick = {},
                )
            }
        }
    }
}


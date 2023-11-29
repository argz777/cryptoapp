package com.example.cryptoapp.pages.coinslist.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.cryptoapp.model.Coin
import com.example.cryptoapp.ui.theme.CryptoappTheme
import java.text.DecimalFormat

@OptIn(ExperimentalAnimationApi::class)
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
        AsyncImage(
            modifier = Modifier.size(40.dp).padding(4.dp),
            model = "https://assets.coincap.io/assets/icons/${coin.symbol!!.lowercase()}@2x.png",
            contentDescription = null,
        )
        Text(
            modifier = Modifier.padding(8.dp),
            textAlign = TextAlign.Center,
            text = "${coin.symbol}")
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            text = "${coin.name}")
        AnimatedContent(
            targetState = coin.priceUsd,
            transitionSpec = { addTimeAnimation() }, label = ""
        ) {
            Text(
                modifier = Modifier
                    .padding(8.dp),
                textAlign = TextAlign.End,
                text = "$" + DecimalFormat("#,###.0000").format(it!!.toDouble()))
        }
    }
}

@ExperimentalAnimationApi
fun addTimeAnimation(duration: Int = 600): ContentTransform {
    return slideInVertically(animationSpec = tween(durationMillis = duration)) { height -> height } + fadeIn(
        animationSpec = tween(durationMillis = duration)
    ) togetherWith slideOutVertically(animationSpec = tween(durationMillis = duration)) { height -> -height } + fadeOut(
        animationSpec = tween(durationMillis = duration)
    )
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


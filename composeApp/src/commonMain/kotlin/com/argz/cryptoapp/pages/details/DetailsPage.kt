package com.argz.cryptoapp.pages.details

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.argz.cryptoapp.MainViewModel
import com.argz.cryptoapp.formatCurrency
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsPage(
    onBackPress: () -> Unit,
    mainViewModel: MainViewModel,
) {
    val detailViewModel = koinViewModel<DetailViewModel>()
    val coin by mainViewModel.selectedCoin
    val favorite by remember { detailViewModel.favorite }

    LaunchedEffect(Unit){
        detailViewModel.refreshFavoriteState(coin!!)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        AsyncImage(
                            modifier = Modifier.size(40.dp).padding(4.dp),
                            model = "https://assets.coincap.io/assets/icons/${coin!!.symbol!!.lowercase()}@2x.png",
                            contentDescription = null,
                        )
                        Text(
                            modifier = Modifier.padding(8.dp),
                            textAlign = TextAlign.Center,
                            text = coin!!.symbol!!.uppercase()
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onBackPress()
                        }
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            detailViewModel.setFavorite(coin!!)
                        }
                    ) {
                        AnimatedContent(favorite) {
                            if (it) {
                                Icon(Icons.Filled.Favorite, contentDescription = "Favorite")
                            } else {
                                Icon(Icons.Outlined.FavoriteBorder, contentDescription = "Favorite")
                            }
                        }
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(top = it.calculateTopPadding(), start = 16.dp, end = 16.dp)
                .fillMaxSize()
        ) {
            Text("Market Stats:")
            CoinDetailContent("Name", coin!!.name)
            CoinDetailContent("Price", coin!!.priceUsd)
            CoinDetailContent("Total Volume", coin!!.volumeUsd24Hr)
            CoinDetailContent("Circulating supply", coin!!.supply)
            CoinDetailContent("Total supply", coin!!.maxSupply)
            CoinDetailContent("Rank", "No. ${coin!!.rank}")
        }
    }
}

@Composable
fun CoinDetailContent(title: String, value: String?) {
    val isNumber = value?.toDoubleOrNull() != null

    value?.let {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(title)
            Spacer(modifier = Modifier.weight(1f))
            if(isNumber){
                Text(formatCurrency(it.toDouble()))
            } else {
                Text(it)
            }
        }
    }
}
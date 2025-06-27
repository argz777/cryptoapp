package com.argz.cryptoapp.pages.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.argz.cryptoapp.MainViewModel
import com.argz.cryptoapp.domain.Coin
import com.argz.cryptoapp.domain.RequestState
import com.argz.cryptoapp.pages.home.components.ChipHeader
import com.argz.cryptoapp.pages.home.components.CoinContent
import com.argz.cryptoapp.pages.home.components.TabFooter
import com.argz.cryptoapp.pages.portfolio.PortfolioPage
import com.argz.cryptoapp.pages.portfolio.PortfolioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    onBackPress: () -> Unit,
    onAccountClick: () -> Unit,
    navigateToSelectCoin: () -> Unit,
    onCoinSelect: (Coin) -> Unit,
    mainViewModel: MainViewModel,
    portfolioViewModel: PortfolioViewModel,
) {
    val currentPage = remember { mutableStateOf(0) }
    val currentChipSelected = remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                actions = {
                    IconButton(
                        onClick = {
                            onAccountClick()
                        }
                    ) {
                        Icon(Icons.Outlined.AccountCircle, "Profile")
                    }
                }
            )
        },
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            val pagerState = rememberPagerState(
                pageCount = {
                    2
                },
                initialPage = currentPage.value
            )

            HorizontalPager(
                modifier = Modifier.weight(1f),
                state = pagerState
            ) { page ->
                when(page){
                    0 -> {
                        MainContent(
                            modifier = Modifier.weight(1f),
                            currentChipSelected,
                            mainViewModel,
                            onCoinSelect,
                        )
                    }
                    1 -> {
                        PortfolioPage(
                            portfolioViewModel = portfolioViewModel,
                            navigateToSelectCoin = { navigateToSelectCoin() },
                        )
                    }
                }
            }

            TabFooter(
                currentPage,
            )

            LaunchedEffect(currentPage.value) {
                pagerState.animateScrollToPage(currentPage.value)
            }
        }
    }
}

@Composable
fun MainContent(
    modifier: Modifier,
    currentChipSelected: MutableState<Int>,
    mainViewModel: MainViewModel,
    onCoinSelect: (Coin) -> Unit,
){
    val listState = rememberLazyListState()
    val coins = mainViewModel.filteredCoins.collectAsState()

    Column(
        modifier = modifier
    ) {
        ChipHeader(
            currentChipSelected = currentChipSelected,
            onChipSelect = { type ->
                mainViewModel.setFilter(type)
            }
        )

        coins.value.let { res ->
            when(res) {
                is RequestState.Error -> {}
                is RequestState.Idle -> {
                    Box(
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is RequestState.Success -> {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        res.data.forEach { coin ->
                            item(key = coin.id) {
                                Row(modifier = Modifier.animateItem()){
                                    CoinContent(
                                        coin = coin,
                                        onCoinSelect = { onCoinSelect(coin) },
                                    )
                                }
                            }
                        }
                    }

                    LaunchedEffect(res.data) {
                        listState.animateScrollToItem(0)
                    }
                }
                RequestState.Loading -> {
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}
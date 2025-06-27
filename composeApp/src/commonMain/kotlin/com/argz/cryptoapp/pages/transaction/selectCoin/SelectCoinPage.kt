package com.argz.cryptoapp.pages.transaction.selectCoin

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.argz.cryptoapp.MainViewModel
import com.argz.cryptoapp.domain.Coin
import com.argz.cryptoapp.pages.transaction.selectCoin.components.CoinListItem
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectCoinPage(
    mainViewModel: MainViewModel,
    onBackPress: () -> Unit,
    onSelect: (Coin) -> Unit,
) {
    val selectCoinViewModel = koinViewModel<SelectCoinViewModel>()
    val coins by mainViewModel.allCoins.collectAsState()

    LaunchedEffect(coins){
        if(coins.isSuccess())
            selectCoinViewModel.setAllCoins(coins.getSuccessData())
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        textAlign = TextAlign.Center,
                        text = "Select Coin"
                    )
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
            )
        },
    ) {
        MainContent(
            it,
            onSelect = {
                onSelect(it)
            },
            selectCoinViewModel
        )
    }
}

@Composable
fun MainContent(
    it: PaddingValues,
    onSelect: (Coin) -> Unit,
    selectCoinViewModel: SelectCoinViewModel,
){
    val searchText by selectCoinViewModel.searchText.collectAsState(initial = "")
    val isSearching by selectCoinViewModel.isSearching.collectAsState(initial = false)
    val filteredList by selectCoinViewModel.filteredList.collectAsState()
    val focusManager = LocalFocusManager.current
    val listState = rememberLazyListState()

    Column(
        modifier = Modifier
            .padding(top = it.calculateTopPadding(), start = 16.dp, end = 16.dp)
            .fillMaxSize()
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = searchText,
            onValueChange = selectCoinViewModel::onSearchTextChange,
            label = {
                Text("Search")
            },
            leadingIcon = {
                Icon(Icons.Filled.Search, contentDescription = "Search")
            },
            trailingIcon = {
                Icon(
                    Icons.Filled.Clear, contentDescription = "Clear all text",
                    modifier = Modifier.clickable {
                        selectCoinViewModel.onSearchTextChange("")
                    }
                )
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            )
        )

        AnimatedContent(targetState = isSearching, label = "") { targetState ->
            when (targetState) {
                true -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp, horizontal = 30.dp)
                    ) {
                        Icon(Icons.Filled.Search, contentDescription = "Localized description")
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Searching...")
                    }
                }
                false -> {
                    if (filteredList.isNotEmpty() && searchText.isNotEmpty()){
                        CoinListContent(
                            listState,
                            Modifier.weight(1f),
                            filteredList,
                            onCoinSelect = {
                                onSelect(it)
                            },
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CoinListContent(
    listState: LazyListState,
    modifier: Modifier,
    filteredCoins: List<Coin>,
    onCoinSelect: (Coin) -> Unit,
) {
    LazyColumn(
        state = listState,
        modifier = modifier
    ) {
        filteredCoins.forEach { coin ->
            item(key = coin.id) {
                Row(modifier = Modifier.animateItem()){
                    CoinListItem(
                        coin = coin,
                        onCoinSelect = { onCoinSelect(coin) },
                    )
                }
            }
        }
    }
}
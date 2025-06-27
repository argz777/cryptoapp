package com.argz.cryptoapp

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.argz.cryptoapp.data.CryptoSDK
import com.argz.cryptoapp.domain.Coin
import com.argz.cryptoapp.domain.RequestState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

typealias CachedCoins = MutableStateFlow<RequestState<List<Coin>>>
enum class CoinFilter {
    ALL, FAVORITES
}

class MainViewModel(
    private val sdk: CryptoSDK
) : ViewModel() {
    var filter: MutableStateFlow<CoinFilter> = MutableStateFlow(CoinFilter.ALL)
    var allCoins: CachedCoins = MutableStateFlow(RequestState.Idle)
        private set
    var selectedCoin: MutableState<Coin?> = mutableStateOf(null)
    var filteredCoins = allCoins.combine(filter) { coins, filter ->
        when(filter){
            CoinFilter.ALL -> {
                coins
            }
            CoinFilter.FAVORITES -> {
                if(coins is RequestState.Success) {
                    val favorites = sdk.getFavoriteCoins()
                    val filteredCoins = coins.data.filter { coin ->
                        favorites.any { it.symbol == coin.symbol }
                    }
                    RequestState.Success(filteredCoins)
                } else {
                    coins
                }
            }
        }
    }
    .stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        allCoins.value
    )

    init {
        viewModelScope.launch{
            withContext(Dispatchers.IO){
                allCoins.value = sdk.getAllCoins()
            }
        }
    }

    fun setFilter(coinFilter: CoinFilter) {
        filter.value = coinFilter
    }
}
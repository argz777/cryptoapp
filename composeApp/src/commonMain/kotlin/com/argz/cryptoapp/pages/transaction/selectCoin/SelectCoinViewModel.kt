package com.argz.cryptoapp.pages.transaction.selectCoin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.argz.cryptoapp.domain.Coin
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn

class SelectCoinViewModel: ViewModel(
) {
    var allCoins: MutableStateFlow<List<Coin>> = MutableStateFlow(emptyList())
        private set
    var searchText = MutableStateFlow("")
        private set
    var isSearching = MutableStateFlow(false)
        private set

    @OptIn(FlowPreview::class)
    val filteredList = searchText
        .onEach { isSearching.value = true }
        .debounce(200L)
        .combine(allCoins) { text, list ->
            if(text.isBlank() || list.isEmpty()) {
                emptyList()
            } else {
                val filtered = list.filter {
                    it.symbol!!.lowercase().contains(text.lowercase()) ||
                            it.name!!.lowercase().contains(text.lowercase())
                }
                filtered
            }
        }
        .onEach { isSearching.value = false }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun setAllCoins(
        coins: List<Coin>
    ) {
        allCoins.value = coins
    }

    fun onSearchTextChange(text: String) {
        searchText.value = text
    }
}
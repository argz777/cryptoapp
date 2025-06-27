package com.argz.cryptoapp.pages.details

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.argz.cryptoapp.data.CryptoSDK
import com.argz.cryptoapp.domain.Coin

class DetailViewModel(
    private val sdk: CryptoSDK
) : ViewModel(){
    var favorite: MutableState<Boolean> = mutableStateOf(false)

    fun refreshFavoriteState(coin: Coin){
        favorite.value = isFavorite(coin)
    }

    private fun isFavorite(coin: Coin): Boolean {
        return coin.id?.let {
            sdk.getCoin(it) != null
        } ?: false
    }

    fun setFavorite(coin: Coin) {
        val flag = isFavorite(coin)

        if(flag){
            sdk.unsetFavorite(coin)
        } else {
            sdk.setFavorite(coin)
        }

        favorite.value = !flag
    }
}
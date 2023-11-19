package com.example.cryptoapp

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.model.Coin
import com.example.cryptoapp.model.PortfolioCoin
import com.example.cryptoapp.model.Resource
import com.example.cryptoapp.network.ApiService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val TAG = "MainViewModel"

class MainViewModel: ViewModel() {
    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO){

            }
        }
    }

    fun addCoin(portfolioCoin: PortfolioCoin) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

            }
        }
    }

    fun getCoins(){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

            }
        }
    }

    fun deleteCoin(){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

            }
        }
    }

    fun updateCoin(
        portfolioCoin: PortfolioCoin,
        newQuantity: Int,
    ) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

            }
        }
    }
}
package com.example.cryptoapp

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.model.Coin
import com.example.cryptoapp.model.PortfolioCoin
import com.example.cryptoapp.network.ApiService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val TAG = "MainViewModel"

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiService: ApiService,
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
): ViewModel() {
    private lateinit var path: CollectionReference
    private lateinit var user: String
    private val _coins: MutableStateFlow<List<Coin>> = MutableStateFlow(emptyList())
    private val _portfolioCoins = MutableStateFlow<SnapshotStateList<PortfolioCoin>>(mutableStateListOf())

    val coins = _coins.asStateFlow()
    val portfolioCoins = _portfolioCoins.asStateFlow()
    var selectedCoin: PortfolioCoin? = null

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                async { _coins.value = apiService.getCoins().data }
                async {
                    firebaseAuth.signInWithEmailAndPassword("test@test.com", "123456").await()
                    user = firebaseAuth.currentUser!!.uid
                    path = firestore.collection("users/$user/coins/")
                    getCoins()
                }
            }
        }
    }

    fun addCoin(portfolioCoin: PortfolioCoin) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                path.document(portfolioCoin.coin!!.id!!).set(portfolioCoin).await()
                _portfolioCoins.update {
                    it.add(portfolioCoin)
                    it
                }
            }
        }
    }

    fun getCoins(){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val dbCoins = path.get().await()

                dbCoins.forEach { coin ->
                    _portfolioCoins.update {
                        it.add(coin.toObject(PortfolioCoin::class.java))
                        it
                    }
                }
            }
        }
    }

    fun updateCoin(
        portfolioCoin: PortfolioCoin
    ) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                path.document(portfolioCoin.coin!!.id!!).set(portfolioCoin).await()
                _portfolioCoins.update {
                    it.find { it.coin!!.id == portfolioCoin.coin!!.id }.let { currentCoin ->
                        currentCoin!!.quantity = portfolioCoin.quantity
                    }
                    it
                }
            }
        }
    }
}
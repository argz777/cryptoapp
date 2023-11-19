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

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiService: ApiService,
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
): ViewModel() {
    private lateinit var path: CollectionReference
    private lateinit var user: String
    private val _coins: MutableStateFlow<Resource<List<Coin>>> = MutableStateFlow(Resource.Loading)
    val portfolioCoins = mutableStateListOf<PortfolioCoin>()

    val coins = _coins.asStateFlow()
    var selectedCoin: PortfolioCoin? = null

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                async {
                    delay(3000)
                    try {
                        val response = apiService.getCoins().data
                        _coins.value = Resource.Success(response)
                    } catch (e : Exception) {
                        _coins.value = Resource.Failure(e)
                    }
                }
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
                portfolioCoins.add(portfolioCoin)
            }
        }
    }

    fun getCoins(){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val dbCoins = path.get().await()
                portfolioCoins.addAll(dbCoins.toObjects(PortfolioCoin::class.java))
            }
        }
    }

    fun deleteCoin(){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                path.document(selectedCoin!!.coin!!.id!!).delete().await()
                portfolioCoins.remove(selectedCoin)
            }
        }
    }

    fun updateCoin(
        portfolioCoin: PortfolioCoin,
        newQuantity: Int,
    ) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                path.document(portfolioCoin.coin!!.id!!).set(portfolioCoin).await()
                portfolioCoins[portfolioCoins.indexOf(portfolioCoin)] = portfolioCoin.copy(
                    quantity = newQuantity
                )
            }
        }
    }
}
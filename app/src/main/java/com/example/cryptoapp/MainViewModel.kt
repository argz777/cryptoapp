package com.example.cryptoapp

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.model.Coin
import com.example.cryptoapp.model.PortfolioCoin
import com.example.cryptoapp.model.Resource
import com.example.cryptoapp.network.ApiService
import com.example.cryptoapp.network.CustomWebSocketListener
import com.example.cryptoapp.utils.Constants
import com.example.cryptoapp.utils.toMap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import org.json.JSONObject
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
    private val _coins: MutableStateFlow<Resource<SnapshotStateList<Coin>>> = MutableStateFlow(Resource.Loading)
    val _portfolioCoins: MutableStateFlow<SnapshotStateList<PortfolioCoin>> = MutableStateFlow(mutableStateListOf())

    var selectedCoin: PortfolioCoin? = null

    private lateinit var ws: WebSocket
    private var _updates: MutableStateFlow<Map<String, *>> = MutableStateFlow(emptyMap<String, String>())
    private var updates = _updates
        .buffer(capacity = 5, onBufferOverflow = BufferOverflow.DROP_OLDEST)
        .transform { value ->
            delay(500)
            emit(value)
        }
    val coins = updates
        .buffer()
        .debounce(500L)
        .combine(_coins) { updates, coins ->
            if(coins is Resource.Success){
                val allCoins = coins.result
                updates.forEach { (key, value) ->
                    for(n in allCoins.indices){
                        if(allCoins[n].id == key) {
                            allCoins[n] = allCoins[n].copy(
                                priceUsd = value as String
                            )
                            break
                        }
                    }
                }
                Resource.Success(allCoins)
            } else {
                coins
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _coins.value
        )
    val portfolioCoins = updates
        .buffer()
        .debounce(500L)
        .combine(_portfolioCoins) { updates, coins ->
            updates.forEach { (key, value) ->
                for(n in coins.indices){
                    if(coins[n].coin!!.id == key) {
                        coins[n] = coins[n].copy(
                            coin = coins[n].coin!!.copy(
                                priceUsd = value as String
                            )
                        )
                        break
                    }
                }
            }
            coins
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _portfolioCoins.value
        )

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                async {
                    delay(3000)
                    try {
                        val response = apiService.getCoins().data
                        _coins.value = Resource.Success(response.toMutableStateList())
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
                async {
                    val request = Request
                        .Builder()
                        .url(Constants.WEB_SOCKET_BASE_URL)
                        .build()

                    val listener = CustomWebSocketListener()
                    listener.listener = {

                        Log.d(TAG, it)
                        _coins.value.let { res ->
                            when(res){
                                is Resource.Success -> {
                                    val jsonObj = JSONObject(it)
                                    _updates.value = jsonObj.toMap()
                                }
                                else -> {}
                            }
                        }
                    }
                    ws = OkHttpClient().newWebSocket(request, listener)
                }
            }
        }
    }

    fun addCoin(portfolioCoin: PortfolioCoin) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                path.document(portfolioCoin.coin!!.id!!).set(portfolioCoin).await()
                _portfolioCoins.value.add(portfolioCoin)
            }
        }
    }

    fun getCoins(){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val dbCoins = path.get().await()
                _portfolioCoins.value.addAll(dbCoins.toObjects(PortfolioCoin::class.java))
            }
        }
    }

    fun deleteCoin(){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                path.document(selectedCoin!!.coin!!.id!!).delete().await()
                _portfolioCoins.value.remove(selectedCoin)
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

                for(n in _portfolioCoins.value.indices){
                    if(portfolioCoin.coin!!.id == _portfolioCoins.value[n].coin!!.id){
                        _portfolioCoins.value[n] = portfolioCoin.copy(
                            quantity = newQuantity
                        )
                        break
                    }
                }
            }
        }
    }
}
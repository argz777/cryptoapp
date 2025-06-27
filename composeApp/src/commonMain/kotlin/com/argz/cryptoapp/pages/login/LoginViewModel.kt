package com.argz.cryptoapp.pages.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.argz.cryptoapp.data.LoginSDK
import com.argz.cryptoapp.domain.RequestState
import dev.gitlive.firebase.auth.FirebaseUser
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

typealias CurrentUser = MutableStateFlow<RequestState<FirebaseUser>>

class LoginViewModel(
    private val sdk: LoginSDK,
): ViewModel() {
    var currentUser: CurrentUser = MutableStateFlow(RequestState.Loading)
        private set

    init {
        viewModelScope.launch {
            currentUser.value = sdk.tryLoginCachedSession()
        }
    }

    fun resetState(){
        currentUser.value = RequestState.Idle
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            currentUser.value = RequestState.Loading
            delay(2000)
            currentUser.value = sdk.login(email, password)
        }
    }

    fun logout() {
        viewModelScope.launch {
            sdk.logout()
        }
    }
}
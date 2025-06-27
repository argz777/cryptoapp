package com.argz.cryptoapp.pages.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.argz.cryptoapp.data.LoginSDK
import com.argz.cryptoapp.domain.RequestState
import com.argz.cryptoapp.domain.UserInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

typealias CachedUserInfo = MutableStateFlow<RequestState<UserInfo>>

class ProfileViewModel(
    private val sdk: LoginSDK,
): ViewModel(){
    val userDetails: CachedUserInfo = MutableStateFlow(RequestState.Idle)

    init {
        retrieveUserInfo()
    }

    fun retrieveUserInfo(){
        viewModelScope.launch {
            userDetails.value = sdk.retrieveUserInfo()
        }
    }
}
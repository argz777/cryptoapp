package com.argz.cryptoapp.domain

import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
    val name: String? = "",
    val email: String? = "",
)
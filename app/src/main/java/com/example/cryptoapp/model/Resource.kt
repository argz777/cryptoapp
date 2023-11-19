package com.example.cryptoapp.model

import java.lang.Exception

sealed class Resource<out T> {
    class Success<out T>(val result: T): Resource<T>()
    class Failure(val exception: Exception): Resource<Nothing>()
    object Loading : Resource<Nothing>()
}
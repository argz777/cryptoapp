package com.argz.cryptoapp.data

import com.argz.cryptoapp.domain.RequestState
import com.argz.cryptoapp.domain.UserInfo
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.firestore.FirebaseFirestore

class LoginSDK(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
) {
    suspend fun login(email: String, password: String): RequestState<FirebaseUser> {
        return try {
            val response = firebaseAuth.signInWithEmailAndPassword(email, password)
            RequestState.Success(response.user!!)
        } catch (e: Exception) {
            RequestState.Error(e.message!!)
        }
    }

    fun tryLoginCachedSession(): RequestState<FirebaseUser> {
        return try {
            RequestState.Success(firebaseAuth.currentUser!!)
        } catch (e: Exception) {
            RequestState.Idle
        }
    }

    suspend fun logout() {
        firebaseAuth.signOut()
    }

    suspend fun retrieveUserInfo(): RequestState<UserInfo>{
        return try {
            val response = firestore.collection("user").document(firebaseAuth.currentUser!!.uid).get()
            return RequestState.Success(
                UserInfo(
                    name = response.get<String>("name"),
                    email = response.get<String>("email")
                )
            )
        } catch (e: Exception) {
            RequestState.Error(e.message!!)
        }
    }
}
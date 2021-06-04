package com.celiluysal.ecommerceproductsapp.ui.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.celiluysal.ecommerceproductsapp.models.User
import com.celiluysal.ecommerceproductsapp.utils.SessionManager

class ProfileViewModel : ViewModel() {
    private val sessionManager = SessionManager.shared
    val user = MutableLiveData<User>()

    init {
        if (sessionManager.user != null)
            user.value = sessionManager.user
        else
            fetchUser()
    }

    private fun fetchUser() {
        sessionManager.fetchUser() {success, error ->
            if (success) {
                user.value = sessionManager.user
            } else {
                Log.e("ProfileViewModel", error!!)
            }
        }
    }

    fun logout() {
        SessionManager.shared.logOut()
    }
}
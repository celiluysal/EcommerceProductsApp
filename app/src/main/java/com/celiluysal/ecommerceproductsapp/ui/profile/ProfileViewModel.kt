package com.celiluysal.ecommerceproductsapp.ui.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.celiluysal.ecommerceproductsapp.base.BaseViewModel
import com.celiluysal.ecommerceproductsapp.models.User
import com.celiluysal.ecommerceproductsapp.session_manager.SessionManager

class ProfileViewModel : BaseViewModel() {
    private val sessionManager = SessionManager.shared
    val user = MutableLiveData<User>()

    init {
        if (sessionManager.user != null)
            user.value = sessionManager.user
        else
            fetchUser()
    }

    private fun fetchUser() {
        startLoading()
        sessionManager.fetchUser() {success, error ->
            startLoading()
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
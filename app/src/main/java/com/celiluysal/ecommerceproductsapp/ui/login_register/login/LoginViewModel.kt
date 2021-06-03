package com.celiluysal.ecommerceproductsapp.ui.login_register.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.celiluysal.ecommerceproductsapp.firebase.FirebaseManager
import com.celiluysal.ecommerceproductsapp.utils.SessionManager

class LoginViewModel : ViewModel() {
    val loggedIn = MutableLiveData<Boolean>().apply { postValue(false) }

    fun login(email: String, password: String) {
        FirebaseManager.shared.login(email, password) { user, error ->
            if (user != null) {
                SessionManager.shared.loggedIn(user)
                loggedIn.value = true
            }
        }
    }
}
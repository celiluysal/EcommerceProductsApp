package com.celiluysal.ecommerceproductsapp.ui.login_register.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.celiluysal.ecommerceproductsapp.firebase.FirebaseManager
import com.celiluysal.ecommerceproductsapp.firebase.RegisterRequestModel
import com.celiluysal.ecommerceproductsapp.models.User
import com.celiluysal.ecommerceproductsapp.utils.SessionManager

class RegisterViewModel : ViewModel() {

    val loadError = MutableLiveData<Boolean>().apply { postValue(false) }

    fun register(request: RegisterRequestModel){
        FirebaseManager.shared.register(request) {user, error ->
            if (user != null)
                login(request.email, request.password)
            else
                loadError.value = true
        }
    }

    private fun login(email: String, password: String) {
        FirebaseManager.shared.login(email, password) { user: User?, error: String? ->
            if (user != null) {
                loadError.postValue(false)
                SessionManager.shared.loggedIn(user)
            } else {
                loadError.value = true
            }
        }
    }
}
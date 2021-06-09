package com.celiluysal.ecommerceproductsapp.ui.login_register.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.celiluysal.ecommerceproductsapp.firebase.FirebaseManager
import com.celiluysal.ecommerceproductsapp.utils.SessionManager
import java.lang.Error

class LoginViewModel : ViewModel() {
    fun login(email: String, password: String,  Result:(success:Boolean, error:String?) -> Unit) {
        FirebaseManager.shared.login(email, password) { user, error ->
            if (user != null) {
                SessionManager.shared.loggedIn(user)
                Result.invoke(true, error)
            } else
                Result.invoke(false ,error)
        }
    }
}
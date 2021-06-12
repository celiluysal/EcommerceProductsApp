package com.celiluysal.ecommerceproductsapp.ui.login_register.login

import com.celiluysal.ecommerceproductsapp.base.BaseViewModel
import com.celiluysal.ecommerceproductsapp.firebase.FirebaseManager
import com.celiluysal.ecommerceproductsapp.session_manager.SessionManager

class LoginViewModel : BaseViewModel() {

    fun login(email: String, password: String,  Result:(success:Boolean, error:String?) -> Unit) {
        startLoading()
        FirebaseManager.shared.login(email, password) { user, error ->
            stopLoading()
            if (user != null) {
                SessionManager.shared.loggedIn(user)
                Result.invoke(true, error)
            } else
                Result.invoke(false ,error)
        }
    }
}
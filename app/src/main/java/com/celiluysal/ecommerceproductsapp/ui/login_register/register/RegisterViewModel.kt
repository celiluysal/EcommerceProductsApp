package com.celiluysal.ecommerceproductsapp.ui.login_register.register

import com.celiluysal.ecommerceproductsapp.base.BaseViewModel
import com.celiluysal.ecommerceproductsapp.firebase.FirebaseManager
import com.celiluysal.ecommerceproductsapp.models.RegisterRequestModel
import com.celiluysal.ecommerceproductsapp.models.User
import com.celiluysal.ecommerceproductsapp.session_manager.SessionManager

class RegisterViewModel : BaseViewModel() {

    fun registerAndLogin(request: RegisterRequestModel, Result:(success:Boolean, error:String?) -> Unit) {
        startLoading()
        FirebaseManager.shared.register(request) {success, error ->
            stopLoading()
            if (success == true) {
                login(request.email, request.password, Result)
                Result.invoke(true, error)
            } else
                Result.invoke(false, error)
        }
    }

    private fun login(email: String, password: String, Result:(success:Boolean, error:String?) -> Unit) {
        startLoading()
        FirebaseManager.shared.login(email, password) { user: User?, error: String? ->
            stopLoading()
            if (user != null) {
                SessionManager.shared.loggedIn(user)
                Result.invoke(true, error)
            } else {
                Result.invoke(false, error)
            }
        }
    }
}
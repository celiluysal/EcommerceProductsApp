package com.celiluysal.ecommerceproductsapp.ui.login_register.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.celiluysal.ecommerceproductsapp.firebase.FirebaseManager
import com.celiluysal.ecommerceproductsapp.models.RegisterRequestModel
import com.celiluysal.ecommerceproductsapp.models.User
import com.celiluysal.ecommerceproductsapp.utils.SessionManager

class RegisterViewModel : ViewModel() {

    val loadError = MutableLiveData<Boolean>().apply { postValue(false) }

    fun registerAndLogin(request: RegisterRequestModel, Result:(success:Boolean, error:String?) -> Unit) {
        FirebaseManager.shared.register(request) {success, error ->
            if (success == true) {
                login(request.email, request.password, Result)
                Result.invoke(true, error)
            } else
                Result.invoke(false, error)
        }
    }

    private fun login(email: String, password: String, Result:(success:Boolean, error:String?) -> Unit) {
        FirebaseManager.shared.login(email, password) { user: User?, error: String? ->
            if (user != null) {
                SessionManager.shared.loggedIn(user)
                Result.invoke(true, error)
            } else {
                Result.invoke(false, error)
            }
        }
    }
}
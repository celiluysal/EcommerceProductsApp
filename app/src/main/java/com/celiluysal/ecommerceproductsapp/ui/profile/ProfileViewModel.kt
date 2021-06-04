package com.celiluysal.ecommerceproductsapp.ui.profile

import androidx.lifecycle.ViewModel
import com.celiluysal.ecommerceproductsapp.utils.SessionManager

class ProfileViewModel : ViewModel() {
    fun logout() {
        SessionManager.shared.logOut()
    }
}
package com.celiluysal.ecommerceproductsapp.utils

import android.util.Log
import com.celiluysal.ecommerceproductsapp.firebase.FirebaseManager
import com.celiluysal.ecommerceproductsapp.models.User

class SessionManager {
    companion object {
        val shared = SessionManager()
    }

    private var firebaseManager = FirebaseManager.shared
    var user: User? = null

    fun isLoggedIn(): Boolean = firebaseManager.getCurrentUser() != null

    fun loggedIn(user: User) {
        this.user = user
        Log.e("SessionManager", "loggedIn" + user.email)
    }

    fun logOut() {
        firebaseManager.signOut()
    }

    fun fetchUser(Result: (success: Boolean, error: String?) -> Unit) {
        val currentUser = FirebaseManager.shared.getCurrentUser()

        currentUser?.let { firebaseUser ->
            firebaseManager.fetchUser(firebaseUser.uid) { wtUser: User?, error: String? ->
                if (wtUser != null) {
                    loggedIn(wtUser)
                    Result.invoke(true, "")
                } else {
                    Result.invoke(false, error)
                }
            }

        }
    }
}
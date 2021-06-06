package com.celiluysal.ecommerceproductsapp.utils

import android.util.Log
import com.celiluysal.ecommerceproductsapp.firebase.FirebaseManager
import com.celiluysal.ecommerceproductsapp.models.Category
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
            firebaseManager.fetchUser(firebaseUser.uid) { user: User?, error: String? ->
                if (user != null) {
                    loggedIn(user)
                    Result.invoke(true, "")
                } else {
                    Result.invoke(false, error)
                }
            }

        }
    }

    private var categories: List<Category>? = null

    fun loadCategories(){
        getCategories {  }
    }

    fun getCategoryName(id: String, Result: (categoryName: String?, error: String?) -> Unit) {
        getCategories { categories ->
            val category = categories.find { category -> id == category.id }
            if (category != null)
                Result.invoke(category.name, null)
        }
    }

    fun getCategoryNameList(Result: (categoryNameList: ArrayList<String>) -> Unit) {
        val categoryNameList: ArrayList<String> = arrayListOf()
        getCategories { categories ->
            if (categories.isNotEmpty()) {
                for (category in categories){
                    categoryNameList.add(category.name)
                }
                if (categoryNameList.isNotEmpty())
                    Result.invoke(categoryNameList)
            }
        }
    }

    private fun getCategories(Result: (categories: List<Category>) -> Unit) {
        if (this.categories.isNullOrEmpty())
            FirebaseManager.shared.fetchCategories {categories, error ->
                if (categories != null) {
                    this.categories = categories
                    Result.invoke(categories)
                }
            }
        else
            Result.invoke(this.categories!!)
    }
}
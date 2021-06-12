package com.celiluysal.ecommerceproductsapp.ui.categories

import androidx.lifecycle.MutableLiveData
import com.celiluysal.ecommerceproductsapp.base.BaseViewModel
import com.celiluysal.ecommerceproductsapp.models.Category
import com.celiluysal.ecommerceproductsapp.session_manager.SessionManager

class CategoriesViewModel : BaseViewModel() {
    val categories = MutableLiveData<List<Category>>()



    fun getCategories() {
        SessionManager.shared.loadCategories()
        startLoading()
        SessionManager.shared.getCategories { categories ->
            stopLoading()
            if (!categories.isNullOrEmpty()) {
                this.categories.value = categories
            }
        }
    }
}
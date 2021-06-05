package com.celiluysal.ecommerceproductsapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.celiluysal.ecommerceproductsapp.firebase.FirebaseManager
import com.celiluysal.ecommerceproductsapp.models.Product

class HomeViewModel : ViewModel() {
    private val fm = FirebaseManager.shared

    val products = MutableLiveData<MutableList<Product>>()

    fun fetchProductOrderByPrice() {
        fm.fetchOrderedProducts("price") { products, error ->
            if (products != null)
                this.products.value = products
        }
    }

}
package com.celiluysal.ecommerceproductsapp.ui.products_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.celiluysal.ecommerceproductsapp.firebase.FirebaseManager
import com.celiluysal.ecommerceproductsapp.models.Product

class ProductsListViewModel : ViewModel() {
    private val fm = FirebaseManager.shared

    val products = MutableLiveData<MutableList<Product>>()

    fun fetchProductOrderByPrice(categoryId: String?) {
        if (categoryId != null) {
            fm.fetchProductsByCategoryId(categoryId) {products, error ->
                if (products != null)
                    this.products.value = products
            }
        } else {
            fm.fetchOrderedProducts("price") { products, error ->
                if (products != null)
                    this.products.value = products
            }
        }
    }

}
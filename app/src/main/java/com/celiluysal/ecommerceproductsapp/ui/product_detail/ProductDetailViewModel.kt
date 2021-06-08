package com.celiluysal.ecommerceproductsapp.ui.product_detail

import androidx.lifecycle.ViewModel
import com.celiluysal.ecommerceproductsapp.firebase.FirebaseManager

class ProductDetailViewModel : ViewModel() {

    fun deleteProduct(productId: String, Result: ((success: Boolean, error: String?) -> Unit)) {
        FirebaseManager.shared.deleteProduct(productId, Result)
    }
}
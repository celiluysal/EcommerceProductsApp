package com.celiluysal.ecommerceproductsapp.ui.product_detail

import androidx.lifecycle.ViewModel
import com.celiluysal.ecommerceproductsapp.base.BaseViewModel
import com.celiluysal.ecommerceproductsapp.firebase.FirebaseManager

class ProductDetailViewModel : BaseViewModel() {

    fun deleteProduct(productId: String, Result: ((success: Boolean, error: String?) -> Unit)) {
        startLoading()
        FirebaseManager.shared.deleteProduct(productId) {success, error ->
            stopLoading()
            error?.let { setErrorMessage(it) }
            Result.invoke(success, error)
        }
    }
}
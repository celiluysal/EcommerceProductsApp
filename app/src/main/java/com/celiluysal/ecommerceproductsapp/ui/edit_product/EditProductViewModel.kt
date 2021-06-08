package com.celiluysal.ecommerceproductsapp.ui.edit_product

import androidx.lifecycle.ViewModel
import com.celiluysal.ecommerceproductsapp.firebase.FirebaseManager
import com.celiluysal.ecommerceproductsapp.models.Product

class EditProductViewModel : ViewModel() {

    fun updateProduct(
        product: Product,
        Result: ((success: Boolean, error: String?) -> Unit)
    ) {
        FirebaseManager.shared.updateProduct(product, Result)
    }
}
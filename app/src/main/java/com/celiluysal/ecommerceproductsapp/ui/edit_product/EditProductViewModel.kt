package com.celiluysal.ecommerceproductsapp.ui.edit_product

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.celiluysal.ecommerceproductsapp.firebase.FirebaseManager
import com.celiluysal.ecommerceproductsapp.models.Product
import com.celiluysal.ecommerceproductsapp.models.ProductRequestModel
import com.celiluysal.ecommerceproductsapp.utils.Utils
import java.util.*

class EditProductViewModel : ViewModel() {

    fun uploadPhoto(photo: Bitmap, Result: (photoUrl: String?, error: String?) -> Unit) {

    }

    fun updateProduct(
        product: Product,
        Result: ((success: Boolean, error: String?) -> Unit)
    ) {
        FirebaseManager.shared.updateProduct(product, Result)
    }

    fun addProduct(
        request: ProductRequestModel,
        Result: ((product: Product?, error: String?) -> Unit)
    ) {
        val productId = UUID.randomUUID().toString()
        FirebaseManager.shared.uploadPhoto(productId, request.photo) {photoUrl, error ->
            if (photoUrl != null) {
                val product = Product(
                    id = productId,
                    name = request.name,
                    description = request.description,
                    updateDate = Utils.shared.dateTimeStamp(),
                    imageUrl = photoUrl,
                    categoryId = request.categoryId,
                    price = request.price
                )
                FirebaseManager.shared.addProduct(product) { success, error ->
                    if (success)
                        Result.invoke(product, null)
                    else
                        Result.invoke(null, error)
                }
            }

        }
    }
}
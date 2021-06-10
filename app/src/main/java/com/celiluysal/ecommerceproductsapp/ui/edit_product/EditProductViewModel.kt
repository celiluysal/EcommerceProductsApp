package com.celiluysal.ecommerceproductsapp.ui.edit_product

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.celiluysal.ecommerceproductsapp.firebase.FirebaseManager
import com.celiluysal.ecommerceproductsapp.models.Product
import com.celiluysal.ecommerceproductsapp.models.ProductRequestModel
import com.celiluysal.ecommerceproductsapp.utils.Utils
import java.util.*

class EditProductViewModel : ViewModel() {

    private fun uploadPhoto(
        productId: String,
        photo: Bitmap,
        Result: (photoUrl: String?, error: String?) -> Unit
    ) {
        FirebaseManager.shared.uploadPhoto(productId, photo, Result)
    }

    fun updateProduct(
        product: Product,
        photo: Bitmap?,
        Result: (product: Product?, error: String?) -> Unit
    ) {
        if (photo != null)
            Result.invoke(null, "photo does not exist")

        uploadPhoto(product.id, photo!!) { photoUrl, error ->
            if (photoUrl != null) {
                product.imageUrl = photoUrl
                product.updateDate = Utils.shared.dateTimeStamp()
                FirebaseManager.shared.updateProduct(product) { success, error ->
                    if (success)
                        Result.invoke(product, null)
                    else
                        Result.invoke(null, error)
                }
            }
            Result.invoke(null, error)
        }
    }

    fun addProduct(
        request: ProductRequestModel,
        Result: ((product: Product?, error: String?) -> Unit)
    ) {
        val productId = UUID.randomUUID().toString()
        uploadPhoto(productId, request.photo) { photoUrl, error ->
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
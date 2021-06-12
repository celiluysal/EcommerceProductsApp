package com.celiluysal.ecommerceproductsapp.ui.edit_product

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.celiluysal.ecommerceproductsapp.base.BaseViewModel
import com.celiluysal.ecommerceproductsapp.firebase.FirebaseManager
import com.celiluysal.ecommerceproductsapp.models.Product
import com.celiluysal.ecommerceproductsapp.models.ProductRequestModel
import com.celiluysal.ecommerceproductsapp.utils.Utils
import java.util.*

class EditProductViewModel : BaseViewModel() {

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
        startLoading()
        if (photo != null) {
            uploadPhoto(product.id, photo) { photoUrl, error ->
                stopLoading()
                if (photoUrl != null) {
                    startLoading()
                    product.imageUrl = photoUrl
                    product.updateDate = Utils.shared.dateTimeStamp()
                    FirebaseManager.shared.updateProduct(product) { success, error ->
                        stopLoading()
                        if (success)
                            Result.invoke(product, null)
                        else
                            Result.invoke(null, error)
                    }
                }
                Result.invoke(null, error)
            }
        } else {
            product.updateDate = Utils.shared.dateTimeStamp()
            FirebaseManager.shared.updateProduct(product) { success, error ->
                stopLoading()
                if (success)
                    Result.invoke(product, null)
                else
                    Result.invoke(null, error)
            }
        }
    }

    fun addProduct(
        request: ProductRequestModel,
        Result: ((product: Product?, error: String?) -> Unit)
    ) {
        startLoading()
        val productId = UUID.randomUUID().toString()
        uploadPhoto(productId, request.photo) { photoUrl, error ->
            stopLoading()
            if (photoUrl != null) {
                startLoading()
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
                    stopLoading()
                    if (success)
                        Result.invoke(product, null)
                    else
                        Result.invoke(null, error)
                }
            }

        }
    }
}
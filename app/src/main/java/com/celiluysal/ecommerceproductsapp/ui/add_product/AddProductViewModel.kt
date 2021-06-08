package com.celiluysal.ecommerceproductsapp.ui.add_product

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import com.celiluysal.ecommerceproductsapp.firebase.FirebaseManager
import com.celiluysal.ecommerceproductsapp.models.Product
import com.celiluysal.ecommerceproductsapp.models.ProductRequestModel
import com.celiluysal.ecommerceproductsapp.utils.SessionManager
import com.celiluysal.ecommerceproductsapp.utils.Utils
import java.util.*

class AddProductViewModel : ViewModel() {
    init {
        SessionManager.shared.getCategoryNameList { categoryNameList ->
            for (item in categoryNameList)
                Log.e("categories:", "$item\n")
        }
    }

//    fun uploadPhoto(
//        name: String, photo: Bitmap, Result: (photoUrl: String?, error: String?) -> Unit
//    ) {
//        FirebaseManager.shared.uploadPhoto(name, photo, Result)
//    }

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
                    updateDate = Utils.shared.dayTimeStamp(),
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
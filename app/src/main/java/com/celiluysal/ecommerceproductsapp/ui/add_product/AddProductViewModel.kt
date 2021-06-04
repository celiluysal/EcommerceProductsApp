package com.celiluysal.ecommerceproductsapp.ui.add_product

import android.util.Log
import androidx.lifecycle.ViewModel
import com.celiluysal.ecommerceproductsapp.firebase.FirebaseManager
import com.celiluysal.ecommerceproductsapp.models.Product
import java.util.*

class AddProductViewModel : ViewModel() {
    init {
//        FirebaseManager.shared.fetchProductById("123") {product, error -> }
//        FirebaseManager.shared.fetchProductsByName("Dankek") {products, error ->
//            products
//        }

        FirebaseManager.shared.fetchProductsByCategoryId(0) {products, error ->
            products
        }

        val product = Product(
            id = "102",//UUID.randomUUID().toString(),
            name = "Gazoz",
            description = "Kek",
            updateDate = "2021.06.05 12:50:00",
            imageUrl = "",
            categoryId = 0,
            price = 3.99
        )

        Log.e("price", product.price.toString())
        FirebaseManager.shared.addProduct(
            product
        ) {success, error ->

        }
    }
}
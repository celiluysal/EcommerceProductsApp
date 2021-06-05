package com.celiluysal.ecommerceproductsapp.ui.add_product

import android.util.Log
import androidx.lifecycle.ViewModel
import com.celiluysal.ecommerceproductsapp.firebase.FirebaseManager
import com.celiluysal.ecommerceproductsapp.models.Product
import java.util.*

class AddProductViewModel : ViewModel() {
    init {

        val product = Product(
            id = "103",//UUID.randomUUID().toString(),
            name = "Gazoz 3",
            description = "Kek",
            updateDate = "2021.06.05 12:50:00",
            imageUrl = "",
            categoryId = 0,
            price = 5.99
        )

//        FirebaseManager.shared.addProduct(
//            product
//        ) {success, error -> }
    }
}
package com.celiluysal.ecommerceproductsapp.models

import android.graphics.Bitmap

data class ProductRequestModel(
    var name: String,
    var description: String,
    var categoryId: String,
    var price: Double,
    var photo: Bitmap
)

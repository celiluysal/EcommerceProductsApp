package com.celiluysal.ecommerceproductsapp.models

data class Product(
        var id: String,
        var title: String,
        var description: String,
        var updateDate: String,
        var imageUrl: String,
        var categoryId: Int,
        var price: Float
)

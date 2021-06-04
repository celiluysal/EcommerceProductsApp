package com.celiluysal.ecommerceproductsapp.models

data class Product(
    var id: String,
    var name: String,
    var description: String,
    var updateDate: String,
    var imageUrl: String,
    var categoryId: Int,
    var price: Double
) {
    fun toDict(): HashMap<*, *> {
        return hashMapOf(
            "id" to id,
            "name" to name,
            "description" to description,
            "updateDate" to updateDate,
            "imageUrl" to imageUrl,
            "categoryId" to categoryId,
            "price" to price
        )
    }
}

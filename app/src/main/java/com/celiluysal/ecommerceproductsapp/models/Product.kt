package com.celiluysal.ecommerceproductsapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class Product(
    var id: String,
    var name: String,
    var description: String,
    var updateDate: String,
    var imageUrl: String,
    var categoryId: String,
    var price: Double
) : Parcelable {
    fun toDict(): HashMap<String, *> {
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

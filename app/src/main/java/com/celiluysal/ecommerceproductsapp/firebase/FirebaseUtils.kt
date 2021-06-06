package com.celiluysal.ecommerceproductsapp.firebase

import com.celiluysal.ecommerceproductsapp.models.Category
import com.celiluysal.ecommerceproductsapp.models.Product
import com.celiluysal.ecommerceproductsapp.models.User
import com.google.firebase.database.DataSnapshot

class FirebaseUtils {
    companion object {
        val shared = FirebaseUtils()
    }


    fun snapshotToCategories(categoriesSnapshot: DataSnapshot): MutableList<Category> {
        val categories = mutableListOf<Category>()
        for (child in categoriesSnapshot.children.iterator()) {
            val category = snapshotToCategory(child)
            category?.let { categories.add(category) }
        }
        return categories
    }

    fun snapshotToCategory(categorySnapshot: DataSnapshot): Category? {
        val categoryDict = if (categorySnapshot.value != null)
            categorySnapshot.value as HashMap<*, *> else return null

        return Category(
            id = categoryDict["id"] as String,
            name = categoryDict["name"] as String,
            imageUrl = categoryDict["imageUrl"] as String
        )
    }

    fun snapshotToProducts(productsSnapshot: DataSnapshot): MutableList<Product> {
        val products = mutableListOf<Product>()
        for (child in productsSnapshot.children.iterator()) {
            val product = snapshotToProduct(child)
            product?.let { products.add(product) }
        }
        return products
    }

    fun snapshotToProduct(productSnapshot: DataSnapshot): Product? {
        val productDict = if (productSnapshot.value != null)
            productSnapshot.value as HashMap<*, *> else return null

        return Product(
            id = productDict["id"] as String,
            name = productDict["name"] as String,
            description = productDict["description"] as String,
            updateDate = productDict["updateDate"] as String,
            imageUrl = productDict["imageUrl"] as String,
            categoryId = productDict["categoryId"] as String,
            price = productDict["price"].toString().toDouble(),
        )
    }

    fun snapshotToUser(usersSnapshot: DataSnapshot): User? {
        val userDict = if (usersSnapshot.value != null)
            usersSnapshot.value as HashMap<*, *> else return null

        return User(
            id = userDict["id"] as String,
            fullName = userDict["fullName"] as String,
            email = userDict["email"] as String
        )
    }

}
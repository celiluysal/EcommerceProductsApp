package com.celiluysal.ecommerceproductsapp.firebase

import com.celiluysal.ecommerceproductsapp.models.Product
import com.celiluysal.ecommerceproductsapp.models.User
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseManager {
    companion object {
        val shared = FirebaseManager()
    }

    private val dbRef = Firebase.database.reference
    private val productsRef = dbRef.child("Products")
    private val usersRef = dbRef.child("Users")
    private val categoriesRef = dbRef.child("Categories")

    private val auth = Firebase.auth

    fun addProduct(
        product: Product,
        Result: ((success: Boolean, error: String?) -> Unit)
    ) {
        productsRef.child(product.id).setValue(product.toDict())
            .addOnSuccessListener {

            }
            .addOnFailureListener {

            }
    }

    fun fetchProductsByName(
        name: String,
        count: Int = 10,
        Result: ((products: MutableList<Product>?, error: String?) -> Unit)
    ) {
        productsRef.orderByChild("name").limitToFirst(count).equalTo(name).get()
            .addOnSuccessListener {
                val products = FirebaseUtils.shared.snapshotToProducts(it)
                if (products.isNotEmpty())
                    Result.invoke(products,null)
                else
                    Result.invoke(null, "Result is empty")
            }
            .addOnFailureListener {
                Result.invoke(null, it.localizedMessage)
            }
    }

    fun fetchProductsByCategoryId(
        categoryId: Int,
        count: Int = 10,
        Result: ((products: MutableList<Product>?, error: String?) -> Unit)
    ) {
        productsRef.orderByChild("categoryId").limitToFirst(count).equalTo(categoryId.toDouble()).get()
            .addOnSuccessListener {
                val products = FirebaseUtils.shared.snapshotToProducts(it)
                if (products.isNotEmpty())
                    Result.invoke(products,null)
                else
                    Result.invoke(null, "Result is empty")
            }
            .addOnFailureListener {
                Result.invoke(null, it.localizedMessage)
            }
    }

    fun fetchProductById(id: String, Result: ((product: Product?, error: String?) -> Unit)) {
        productsRef.child(id).get()
            .addOnSuccessListener {
                val product = FirebaseUtils.shared.snapshotToProduct(it)
                Result.invoke(product, null)
            }
            .addOnFailureListener {
                Result.invoke(null, it.localizedMessage)
            }
    }

    fun fetchUser(
        userId: String,
        Result: (user: User?, error: String?) -> Unit
    ) {
        usersRef.child(userId).get()
            .addOnSuccessListener { dataSnapshot ->
                val dict = dataSnapshot.value as HashMap<*, *>
                val user = User(
                    id = userId,
                    fullName = dict["fullName"] as String,
                    email = dict["email"] as String,
                )
                Result.invoke(user, null)
            }
            .addOnFailureListener {
                Result.invoke(null, it.localizedMessage)
            }
    }

    fun register(
        request: RegisterRequestModel,
        Result: (user: User?, error: String?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(request.email, request.password)
            .addOnSuccessListener {
                auth.currentUser?.let { firebaseUser ->
                    saveUser(request, firebaseUser.uid) { success, error ->
                        if (success) {
                            val user = User(
                                id = firebaseUser.uid,
                                fullName = request.fullName,
                                email = request.email
                            )
                            Result.invoke(user, null)
                        } else Result.invoke(null, error)
                    }
                }
            }
            .addOnFailureListener {
                Result.invoke(null, it.localizedMessage)
            }
    }

    private fun saveUser(
        request: RegisterRequestModel,
        uid: String,
        Result: ((success: Boolean, error: String?) -> Unit)
    ) {
        usersRef.child(uid).setValue(
            hashMapOf<String, Any?>(
                "userId" to uid,
                "fullName" to request.fullName,
                "email" to request.email
            )
        )
            .addOnSuccessListener {
                Result.invoke(true, null)
            }
            .addOnFailureListener {
                Result.invoke(false, it.localizedMessage)
            }

    }

    fun login(
        email: String,
        password: String,
        Result: (user: User?, error: String?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                authResult.user?.let {
                    fetchUser(it.uid, Result)
                }
            }
            .addOnFailureListener {
                Result.invoke(null, it.localizedMessage)
            }
    }

    fun getCurrentUser(): FirebaseUser? = auth.currentUser

    fun signOut() = auth.signOut()

}
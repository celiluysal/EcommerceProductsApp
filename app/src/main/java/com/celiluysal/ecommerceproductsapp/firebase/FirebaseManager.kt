package com.celiluysal.ecommerceproductsapp.firebase

import android.graphics.Bitmap
import android.util.Log
import com.celiluysal.ecommerceproductsapp.models.Category
import com.celiluysal.ecommerceproductsapp.models.Product
import com.celiluysal.ecommerceproductsapp.models.RegisterRequestModel
import com.celiluysal.ecommerceproductsapp.models.User
import com.celiluysal.ecommerceproductsapp.utils.SessionManager
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

class FirebaseManager {
    companion object {
        val shared = FirebaseManager()
    }

    private val dbRef = Firebase.database.reference
    private val storageRef = Firebase.storage.reference
    private val productsRef = dbRef.child("Products")
    private val usersRef = dbRef.child("Users")
    private val categoriesRef = dbRef.child("Categories")


    private val auth = Firebase.auth

    fun observeCategoriesChild() {
        categoriesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                SessionManager.shared.loadCategories()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun fetchCategories(Result: (categories: List<Category>?, error: String?) -> Unit) {
        categoriesRef.get()
            .addOnSuccessListener {
                val categories = FirebaseUtils.shared.snapshotToCategories(it)
                if (categories.isNotEmpty())
                    Result.invoke(categories, null)
                else
                    Result.invoke(null, "Empty list")
            }
            .addOnFailureListener {
                Result.invoke(null, it.localizedMessage)
            }
    }

    fun uploadPhoto(
        name: String,
        photo: Bitmap,
        Result: (photoUrl: String?, error: String?) -> Unit
    ) {
        val photoRef = storageRef.child("images/products/$name.jpeg")
        val baos = ByteArrayOutputStream()
        photo.compress(Bitmap.CompressFormat.JPEG, 100, baos)

        var uploadTask = photoRef.putBytes(baos.toByteArray())
            .addOnSuccessListener {
                storageRef.child("images/products/$name.jpeg").downloadUrl
                    .addOnSuccessListener {
                        Result.invoke(it.toString(), null)
                    }.addOnFailureListener {
                        // Handle any errors
                    }
            }
            .addOnFailureListener {

            }


    }

    fun addProduct(
        product: Product,
        Result: ((success: Boolean, error: String?) -> Unit)
    ) {
        productsRef.child(product.id).setValue(product.toDict())
            .addOnSuccessListener {
                Result.invoke(true, null)
            }
            .addOnFailureListener {
                Result.invoke(false, it.localizedMessage)
            }
    }

    fun deleteProduct(productId: String, Result: ((success: Boolean, error: String?) -> Unit)) {
        productsRef.child(productId).removeValue()
            .addOnSuccessListener {
                Result.invoke(true, null)
            }
            .addOnFailureListener {
                Result.invoke(false, it.localizedMessage)
            }
    }

    fun updateProduct(
        product: Product,
        Result: ((success: Boolean, error: String?) -> Unit)
    ) {
        fetchProductById(product.id) { _product, error ->
            if (_product != null) {
                productsRef.child(product.id).updateChildren(product.toDict().toMutableMap())
                    .addOnSuccessListener {
                        Result.invoke(true, null)
                    }
                    .addOnFailureListener {
                        Result.invoke(false, it.localizedMessage)
                    }

            } else
                Result.invoke(false, error)
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
                    Result.invoke(products, null)
                else
                    Result.invoke(null, "Result is empty")
            }
            .addOnFailureListener {
                Result.invoke(null, it.localizedMessage)
            }
    }

    fun fetchOrderedProducts(
        orderBy: String,
        count: Int = 100,
        Result: ((products: MutableList<Product>?, error: String?) -> Unit)
    ) {
        productsRef.limitToFirst(count).get()
            .addOnSuccessListener {
                val products = FirebaseUtils.shared.snapshotToProducts(it)
                if (products.isNotEmpty())
                    Result.invoke(products, null)
                else
                    Result.invoke(null, "Result is empty")
            }
            .addOnFailureListener {
                Result.invoke(null, it.localizedMessage)
            }
    }

    fun fetchProductsByCategoryId(
        categoryId: String?,
        count: Int = 100,
        Result: ((products: MutableList<Product>?, error: String?) -> Unit)
    ) {
        val query =
            if (categoryId != null) productsRef.orderByChild("categoryId").limitToFirst(count)
                .equalTo(categoryId)
            else productsRef
        query.get()
            .addOnSuccessListener {
                val products = FirebaseUtils.shared.snapshotToProducts(it)
                if (products.isNotEmpty())
                    Result.invoke(products, null)
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
        Result: (success: Boolean?, error: String?) -> Unit
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
                        }
                        Result.invoke(success, error)
                    }
                }
            }
            .addOnFailureListener {
                Result.invoke(false, it.localizedMessage)
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
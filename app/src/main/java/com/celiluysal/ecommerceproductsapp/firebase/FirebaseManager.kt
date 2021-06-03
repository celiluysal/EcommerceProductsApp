package com.celiluysal.ecommerceproductsapp.firebase

import com.celiluysal.ecommerceproductsapp.models.User
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseManager {
    companion object {
        val shared = FirebaseManager()
    }

    private var dbRef = Firebase.database.reference
    private var auth = Firebase.auth


    fun fetchUser(
        userId: String,
        Result: (user: User?, error: String?) -> Unit
    ) {
        dbRef.child("Users").child(userId).get()
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
        dbRef.child("Users").child(uid).setValue(
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
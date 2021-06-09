package com.celiluysal.ecommerceproductsapp.utils

import android.content.res.Resources
import android.util.Patterns
import android.widget.Spinner
import com.celiluysal.ecommerceproductsapp.R
import com.google.android.material.textfield.TextInputEditText

fun TextInputEditText.isEmpty(): Boolean = !text.isNullOrBlank()
fun TextInputEditText.isEmail(): Boolean = Patterns.EMAIL_ADDRESS.matcher(text.toString()).matches()
fun TextInputEditText.isPassword(): Boolean {
    return if (!text.isNullOrBlank())
        return !(text.toString().length < 6 || text.toString().length > 18)
     else
        false
}

fun TextInputEditText.isAgainPassword(password: String): Boolean = text.toString() == password
fun Spinner.isEmpty(): Boolean = selectedItem != Resources.getSystem().getString(R.string.category)






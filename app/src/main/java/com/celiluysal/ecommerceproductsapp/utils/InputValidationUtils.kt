package com.celiluysal.ecommerceproductsapp.utils

import android.content.Context
import android.content.res.Resources
import android.util.Patterns
import android.widget.Spinner
import com.celiluysal.ecommerceproductsapp.R
import com.google.android.material.textfield.TextInputEditText

fun TextInputEditText.isEmpty(c:Context?): String? {
    return if (text.isNullOrBlank()) c?.getString(R.string.field_cant_be_empty) else null
}

fun TextInputEditText.isEmail(c:Context?): String? {
    isEmpty(c)?.let { return it}
    return if (!Patterns.EMAIL_ADDRESS.matcher(text.toString()).matches())
        c?.getString(R.string.wrong_type)
    else
        null
}

fun TextInputEditText.isPassword(c:Context?): String? {
    isEmpty(c)?.let { return it}
    return if(text.toString().length < 6 || text.toString().length > 18)
        c?.getString(R.string.wrong_type)
    else
        null
}

fun TextInputEditText.isAgainPassword(c:Context?, password: String): String? {
    isEmpty(c)?.let { return it}
    return if (text.toString() != password)
        c?.getString(R.string.did_not_match_passwords)
    else
        null
}

fun Spinner.isEmpty(c:Context?): String? {
    return if(selectedItem == c?.getString(R.string.category)) c?.getString(R.string.empty_category) else null
}





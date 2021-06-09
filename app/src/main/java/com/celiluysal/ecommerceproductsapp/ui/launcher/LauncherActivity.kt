package com.celiluysal.ecommerceproductsapp.ui.launcher

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.celiluysal.ecommerceproductsapp.firebase.FirebaseManager
import com.celiluysal.ecommerceproductsapp.ui.main.MainActivity
import com.celiluysal.ecommerceproductsapp.ui.login_register.LoginRegisterActivity
import com.celiluysal.ecommerceproductsapp.utils.SessionManager

class LauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState)

        SessionManager.shared.loadCategories()
        FirebaseManager.shared.observeCategoriesChild()

        if (SessionManager.shared.isLoggedIn()) {
            SessionManager.shared.fetchUser { success, error ->
                if (success) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    startActivity(Intent(this, LoginRegisterActivity::class.java))
                    Log.e("LauncherActivity", error!!)
                    finish()
                }
            }
        } else {
            startActivity(Intent(this, LoginRegisterActivity::class.java))
            finish()
        }
    }
}
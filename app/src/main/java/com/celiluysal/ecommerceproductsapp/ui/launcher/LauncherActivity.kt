package com.celiluysal.ecommerceproductsapp.ui.launcher

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.celiluysal.ecommerceproductsapp.ui.MainActivity
import com.celiluysal.ecommerceproductsapp.ui.login_register.LoginRegisterActivity

class LauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState)

        startActivity(Intent(this, MainActivity::class.java))
        finish()

    }
}
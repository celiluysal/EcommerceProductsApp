package com.celiluysal.ecommerceproductsapp.ui.login_register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.celiluysal.ecommerceproductsapp.base.BaseActivity
import com.celiluysal.ecommerceproductsapp.databinding.ActivityLoginRegisterBinding

class LoginRegisterActivity : BaseActivity<ActivityLoginRegisterBinding, ViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun getViewBinding(): ActivityLoginRegisterBinding {
        return ActivityLoginRegisterBinding.inflate(layoutInflater)
    }
}
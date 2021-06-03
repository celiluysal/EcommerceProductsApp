package com.celiluysal.ecommerceproductsapp.ui.login_register

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import com.celiluysal.ecommerceproductsapp.R
import com.celiluysal.ecommerceproductsapp.base.BaseActivity
import com.celiluysal.ecommerceproductsapp.databinding.ActivityLoginRegisterBinding

class LoginRegisterActivity : BaseActivity<ActivityLoginRegisterBinding, ViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val navController = findNavController(R.id.fragmentLoginRegisterNavHost)

//        binding.bottomNavBar.setupWithNavController(navController)


    }

    override fun getViewBinding(): ActivityLoginRegisterBinding {
        return ActivityLoginRegisterBinding.inflate(layoutInflater)
    }
}
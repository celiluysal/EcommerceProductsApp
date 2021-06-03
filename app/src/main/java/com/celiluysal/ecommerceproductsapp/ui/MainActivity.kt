package com.celiluysal.ecommerceproductsapp.ui

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.celiluysal.ecommerceproductsapp.R
import com.celiluysal.ecommerceproductsapp.base.BaseActivity
import com.celiluysal.ecommerceproductsapp.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding, ViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navController = findNavController(R.id.fragmentMainNavHost)
        binding.bottomNavigationViewHome.setupWithNavController(navController)
    }

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }
}
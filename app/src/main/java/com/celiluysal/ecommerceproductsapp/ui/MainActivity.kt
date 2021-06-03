package com.celiluysal.ecommerceproductsapp.ui

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.celiluysal.ecommerceproductsapp.base.BaseActivity
import com.celiluysal.ecommerceproductsapp.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding, ViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }
}
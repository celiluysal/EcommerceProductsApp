package com.celiluysal.ecommerceproductsapp.base

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB: ViewBinding, VM: ViewModel>: AppCompatActivity() {
    protected lateinit var binding: VB
    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = getViewBinding()

        setContentView(binding.root)
    }

    protected abstract fun getViewBinding(): VB
}
package com.celiluysal.ecommerceproductsapp.ui

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.celiluysal.ecommerceproductsapp.MainNavigationDirections
import com.celiluysal.ecommerceproductsapp.R
import com.celiluysal.ecommerceproductsapp.base.BaseActivity
import com.celiluysal.ecommerceproductsapp.databinding.ActivityMainBinding
import com.celiluysal.ecommerceproductsapp.ui.product_detail.ProductDetailFragment
import com.celiluysal.ecommerceproductsapp.utils.Utils
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : BaseActivity<ActivityMainBinding, ViewModel>() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navController = findNavController(R.id.fragmentMainNavHost)
        binding.bottomNavigationViewHome.setupWithNavController(navController)
        setupBottomNavBar()

        binding.includeToolbar.imageViewBack.visibility = ImageView.GONE
        binding.includeToolbar.imageViewSearch.visibility = ImageView.GONE
        binding.includeToolbar.imageViewBack.setOnClickListener {
            onBackPressed()
        }

    }

    fun toolbarBackIconVisibility(isVisible: Boolean) {
        binding.includeToolbar.imageViewBack.visibility = if (isVisible) ImageView.VISIBLE else ImageView.GONE
    }

//    private fun setupToolbar() {
//        binding.includeToolbar.imageViewBack.visibility = ImageView.GONE
//        binding.includeToolbar.imageViewSearch.visibility = ImageView.GONE
//
//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            when (destination.id) {
//                R.id.productDetailFragment -> {
//                    binding.includeToolbar.imageViewBack.visibility = ImageView.VISIBLE
//                }
//                R.id.editProductFragment -> {
//                    binding.includeToolbar.imageViewBack.visibility = ImageView.VISIBLE
//                }
//                else -> {
//                    binding.includeToolbar.imageViewBack.visibility = ImageView.GONE
//                }
//            }
//        }
//
//        binding.includeToolbar.imageViewBack.setOnClickListener {
//            onBackPressed()
//        }
//    }

    private fun setupBottomNavBar(){
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.productDetailFragment -> {
                    binding.bottomNavigationViewHome.visibility = BottomNavigationView.GONE
                }
                R.id.editProductFragment -> {
                    binding.bottomNavigationViewHome.visibility = BottomNavigationView.GONE
                }
                R.id.addProductFragment -> {
                    binding.bottomNavigationViewHome.visibility = BottomNavigationView.VISIBLE
                    keyboardSizeListener()
                }
                else -> {
                    binding.bottomNavigationViewHome.visibility = BottomNavigationView.VISIBLE
                }
            }
        }
    }

    private fun keyboardSizeListener() {
        binding.root.viewTreeObserver.addOnGlobalLayoutListener {
            val heightDiff = binding.root.rootView.height - binding.root.height
            if (heightDiff > Utils.shared.dpToPx(this, 200f)) {
                binding.bottomNavigationViewHome.visibility = BottomNavigationView.GONE
            } else {
                binding.bottomNavigationViewHome.visibility = BottomNavigationView.VISIBLE
            }
        }
    }

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }
}
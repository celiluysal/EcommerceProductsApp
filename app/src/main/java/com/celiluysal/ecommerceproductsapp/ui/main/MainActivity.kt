package com.celiluysal.ecommerceproductsapp.ui.main

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.PopupMenu
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
        binding.includeToolbar.imageViewRight.visibility = ImageView.GONE
        binding.includeToolbar.imageViewBack.setOnClickListener {
            onBackPressed()
        }
    }

    fun setupSortMenu(Result: (item: MenuItem) -> Unit) {
        toolbarRightIconVisibility(true)
        toolbarRightIconDrawable(R.drawable.ic_sort)
        binding.includeToolbar.imageViewRight.setOnClickListener {
            val popupMenu = PopupMenu(this, binding.includeToolbar.imageViewRight)
            popupMenu.menuInflater.inflate(R.menu.product_sort_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                Result.invoke(item)
                true
            })
            popupMenu.show()
        }
    }

    fun toolbarRightIconVisibility(isVisible: Boolean) {
        binding.includeToolbar.imageViewRight.visibility = if (isVisible) ImageView.VISIBLE else ImageView.GONE
    }

    fun toolbarRightIconDrawable(resId: Int) {
        binding.includeToolbar.imageViewRight.setImageResource(resId)
    }

    fun toolbarRightIconClickListener(onClick:()->Unit) {
        binding.includeToolbar.imageViewRight.setOnClickListener {
            Log.e("MainActivity", "toolbarRightIconClickListener")
            onClick.invoke()
        }
    }

    fun toolbarBackIconVisibility(isVisible: Boolean) {
        binding.includeToolbar.imageViewBack.visibility = if (isVisible) ImageView.VISIBLE else ImageView.GONE
    }

    private fun setupBottomNavBar(){
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.root.viewTreeObserver.removeOnGlobalLayoutListener(globalLayoutListener)
            when (destination.id) {
                R.id.productDetailFragment -> {
                    Log.e("MainActivity", "productDetailFragment")
                    binding.bottomNavigationViewHome.visibility = BottomNavigationView.GONE
                }
                R.id.editProductFragment -> {
                    Log.e("MainActivity", "editProductFragment")
                    binding.bottomNavigationViewHome.visibility = BottomNavigationView.GONE
                }
                R.id.addProductFragment -> {
                    binding.bottomNavigationViewHome.visibility = BottomNavigationView.VISIBLE
                    keyboardSizeListener {
                        binding.bottomNavigationViewHome.visibility = if (it) BottomNavigationView.GONE else BottomNavigationView.VISIBLE
                    }
                }
                else -> {
                    binding.bottomNavigationViewHome.visibility = BottomNavigationView.VISIBLE
                }
            }
        }
    }


    var globalLayoutListener: ViewTreeObserver.OnGlobalLayoutListener? = null

    private fun keyboardSizeListener(Result: (isSoftKeyActive:Boolean) -> Unit) {

        globalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
            val heightDiff = binding.root.rootView.height - binding.root.height
            Log.e("MainActivity", "keyboardSizeListener")
            Result.invoke(heightDiff > Utils.shared.dpToPx(this, 200f))
        }
        binding.root.viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)


    }

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }
}
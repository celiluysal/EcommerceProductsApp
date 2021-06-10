package com.celiluysal.ecommerceproductsapp.ui.main

import android.annotation.SuppressLint
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
import com.celiluysal.ecommerceproductsapp.R
import com.celiluysal.ecommerceproductsapp.base.BaseActivity
import com.celiluysal.ecommerceproductsapp.databinding.ActivityMainBinding
import com.celiluysal.ecommerceproductsapp.utils.Utils
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : BaseActivity<ActivityMainBinding, ViewModel>() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = findNavController(R.id.fragmentMainNavHost)
        binding.bottomNavigationViewHome.setupWithNavController(navController)
        setupVisibilities()

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
        binding.includeToolbar.imageViewRight.visibility =
            if (isVisible) ImageView.VISIBLE else ImageView.GONE
    }

    fun toolbarRightIconDrawable(resId: Int) {
        binding.includeToolbar.imageViewRight.setImageResource(resId)
    }

    fun toolbarRightIconClickListener(onClick: () -> Unit) {
        binding.includeToolbar.imageViewRight.setOnClickListener {
            Log.e("MainActivity", "toolbarRightIconClickListener")
            onClick.invoke()
        }
    }

    fun toolbarBackIconVisibility(isVisible: Boolean) {
        binding.includeToolbar.imageViewBack.visibility =
            if (isVisible) ImageView.VISIBLE else ImageView.GONE
    }

    fun bottomNavBarVisibility(isVisible: Boolean) {
        binding.bottomNavigationViewHome.visibility =
            if (isVisible) BottomNavigationView.VISIBLE else BottomNavigationView.GONE
    }

    private fun setupVisibilities() {
        navController.addOnDestinationChangedListener { _a, destination, _b ->
            toolbarRightIconVisibility(false)
            bottomNavBarVisibility(true)

            binding.root.viewTreeObserver.removeOnGlobalLayoutListener(globalLayoutListener)
            when (destination.id) {
                R.id.productsListFragment -> {
                    toolbarRightIconVisibility(true)
                }
                R.id.productDetailFragment -> {
                    bottomNavBarVisibility(false)
                }
                R.id.editProductFragment -> {
                    bottomNavBarVisibility(false)
                }
//                R.id.addProductFragment -> {
//                    bottomNavBarVisibility(true)
////                    keyboardSizeListener {
////                        if (it) bottomNavBarVisibility(false) else bottomNavBarVisibility(true)
////                    }
//                }
            }
        }
    }


    var globalLayoutListener: ViewTreeObserver.OnGlobalLayoutListener? = null

    private fun keyboardSizeListener(Result: (isSoftKeyActive: Boolean) -> Unit) {

        globalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
            val heightDiff = binding.root.rootView.height - binding.root.height
            Result.invoke(heightDiff > Utils.shared.dpToPx(this, 200f))
        }
        binding.root.viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)


    }

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }
}
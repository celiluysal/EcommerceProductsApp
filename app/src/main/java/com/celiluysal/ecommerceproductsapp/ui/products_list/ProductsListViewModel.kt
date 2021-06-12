package com.celiluysal.ecommerceproductsapp.ui.products_list

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.celiluysal.ecommerceproductsapp.base.BaseViewModel
import com.celiluysal.ecommerceproductsapp.firebase.FirebaseManager
import com.celiluysal.ecommerceproductsapp.models.Category
import com.celiluysal.ecommerceproductsapp.models.Product
import com.celiluysal.ecommerceproductsapp.session_manager.SessionManager
import com.celiluysal.ecommerceproductsapp.utils.SortItem

class ProductsListViewModel : BaseViewModel() {
    private val fm = FirebaseManager.shared

    val products = MutableLiveData<MutableList<Product>>()
    val sortItem = MutableLiveData(SortItem.PriceAsc)
    val categories = MutableLiveData<List<Category>>()


    init {
        startLoading()
        SessionManager.shared.getCategories { categories ->
            stopLoading()
            if (!categories.isNullOrEmpty()) {
                this.categories.value = categories
            }
        }
    }

    fun fetchAndSortProducts(categoryId: String?) {
        startLoading()
        fm.fetchProductsByCategoryId(categoryId) { products, error ->
            if (products != null) {
                sortProductsByChild(products) { sortedProducts ->
                    stopLoading()
                    this.products.value = sortedProducts
                }
            }
        }

    }

    private fun sortProductsByChild(
        products: MutableList<Product>,
        Result: (sortedProducts: MutableList<Product>) -> Unit
    ) {
        when (sortItem.value) {
            SortItem.PriceDsc -> {
                Result.invoke(
                    products.sortedByDescending { product -> product.price }.toMutableList()
                )
            }
            SortItem.PriceAsc -> {
                Result.invoke(
                    products.sortedBy { product -> product.price }.toMutableList()
                )
            }
            SortItem.DateAsc -> {
                Result.invoke(
                    products.sortedBy { product -> product.updateDate }.toMutableList()
                )
            }
            SortItem.DateDsc -> {
                Result.invoke(
                    products.sortedByDescending { product -> product.updateDate }.toMutableList()
                )
            }
        }
    }
}
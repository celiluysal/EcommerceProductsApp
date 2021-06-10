package com.celiluysal.ecommerceproductsapp.ui.products_list

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.celiluysal.ecommerceproductsapp.firebase.FirebaseManager
import com.celiluysal.ecommerceproductsapp.models.Product
import com.celiluysal.ecommerceproductsapp.utils.SortItem

class ProductsListViewModel : ViewModel() {
    private val fm = FirebaseManager.shared

    val products = MutableLiveData<MutableList<Product>>()
    val sortItem = MutableLiveData(SortItem.PriceAsc)

    fun fetchAndSortProducts(categoryId: String?) {
        Log.e("ProductsListViewModel", "fetchAndSortProducts")

        fm.fetchProductsByCategoryId(categoryId) { products, error ->
            if (products != null) {
                sortProductsByChild(products) { sortedProducts ->
                    this.products.value = sortedProducts
                }
            }
        }

    }

    private fun sortProductsByChild(
        products: MutableList<Product>,
        Result: (sortedProducts: MutableList<Product>) -> Unit
    ) {
        Log.e("ProductsListViewModel", "sortProductsByChild")
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
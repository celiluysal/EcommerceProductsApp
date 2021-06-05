package com.celiluysal.ecommerceproductsapp.ui.home

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.celiluysal.ecommerceproductsapp.R
import com.celiluysal.ecommerceproductsapp.base.BaseFragment
import com.celiluysal.ecommerceproductsapp.databinding.HomeFragmentBinding
import com.celiluysal.ecommerceproductsapp.models.Product
import com.celiluysal.ecommerceproductsapp.ui.login_register.login.ProductsRecyclerViewAdapter

class HomeFragment : BaseFragment<HomeFragmentBinding, HomeViewModel>() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var productsRecyclerViewAdapter: ProductsRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        viewModel.fetchProductOrderByPrice()

        observeViewModel()

        binding.recyclerViewProducts.layoutManager = GridLayoutManager(activity, 3)


        return binding.root
    }

    private fun observeViewModel() {
        viewModel.products.observe(viewLifecycleOwner, { products ->
            productsRecyclerViewAdapter = ProductsRecyclerViewAdapter(
                products,
                object : ProductsRecyclerViewAdapter.ProductAdapterClickListener {
                    @SuppressLint("ShowToast")
                    override fun onProductCardClick(item: Product, position: Int) {
                        Toast.makeText(context, "selected item: ${item.name}", Toast.LENGTH_SHORT)
                        Log.e("click", "selected item: ${item.name}")

                    }
                })
            binding.recyclerViewProducts.adapter = productsRecyclerViewAdapter
        })
    }


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): HomeFragmentBinding {
        return HomeFragmentBinding.inflate(inflater, container, false)
    }

}
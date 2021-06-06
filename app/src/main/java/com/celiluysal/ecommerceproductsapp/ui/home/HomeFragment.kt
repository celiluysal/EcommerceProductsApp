package com.celiluysal.ecommerceproductsapp.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.celiluysal.ecommerceproductsapp.MainNavigationDirections
import com.celiluysal.ecommerceproductsapp.base.BaseFragment
import com.celiluysal.ecommerceproductsapp.databinding.HomeFragmentBinding
import com.celiluysal.ecommerceproductsapp.models.Product

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
                    override fun onProductCardClick(item: Product, position: Int) {
                        findNavController().navigate(MainNavigationDirections.actionToProductDetailFragment(item))
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
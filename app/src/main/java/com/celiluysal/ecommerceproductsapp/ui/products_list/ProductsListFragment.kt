package com.celiluysal.ecommerceproductsapp.ui.products_list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.celiluysal.ecommerceproductsapp.MainNavigationDirections
import com.celiluysal.ecommerceproductsapp.R
import com.celiluysal.ecommerceproductsapp.base.BaseFragment
import com.celiluysal.ecommerceproductsapp.databinding.ProductsListFragmentBinding
import com.celiluysal.ecommerceproductsapp.ui.message_dialog.MessageDialog
import com.celiluysal.ecommerceproductsapp.models.Product
import com.celiluysal.ecommerceproductsapp.ui.MainActivity
import com.celiluysal.ecommerceproductsapp.ui.home.ProductsRecyclerViewAdapter
import com.celiluysal.ecommerceproductsapp.ui.loading_dialog.LoadingDialog
import com.celiluysal.ecommerceproductsapp.utils.SessionManager

class ProductsListFragment : BaseFragment<ProductsListFragmentBinding, ProductsListViewModel>() {

    private val args: ProductsListFragmentArgs by navArgs()

    companion object {
        fun newInstance() = ProductsListFragment()
    }

    private lateinit var productsRecyclerViewAdapter: ProductsRecyclerViewAdapter
//    private lateinit var loadingDialog: LoadingDialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductsListViewModel::class.java)

        val categoryId = args.categoryId

        viewModel.fetchProductOrderByPrice(categoryId)
//        loadingDialog = LoadingDialog()
//        activity?.supportFragmentManager?.let {
//            loadingDialog.show(it, "LoadingDialog")
//        }

        startLoading()

        observeViewModel()

        if (categoryId == null)
            binding.textViewListContent.text = getString(R.string.all_products)
        else
            SessionManager.shared.getCategoryName(categoryId) { categoryName, error ->
                binding.textViewListContent.text = categoryName
                (activity as MainActivity).toolbarBackIconVisibility(true)
            }

        binding.swipeRefreshLayoutProducts.setOnRefreshListener {
            viewModel.fetchProductOrderByPrice(categoryId)
        }
        binding.recyclerViewProducts.layoutManager = GridLayoutManager(activity, 3)

        return binding.root
    }

    private fun observeViewModel() {
        viewModel.products.observe(viewLifecycleOwner, { products ->
            binding.swipeRefreshLayoutProducts.isRefreshing = false
//            loadingDialog.dismiss()
            stopLoading()
            productsRecyclerViewAdapter = ProductsRecyclerViewAdapter(
                products,
                object : ProductsRecyclerViewAdapter.ProductAdapterClickListener {
                    override fun onProductCardClick(item: Product, position: Int) {
                        findNavController().navigate(
                            MainNavigationDirections.actionToProductDetailFragment(
                                item
                            )
                        )
                    }
                })
            binding.recyclerViewProducts.adapter = productsRecyclerViewAdapter
        })
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ProductsListFragmentBinding {
        return ProductsListFragmentBinding.inflate(inflater, container, false)
    }

    interface ProductsListFragmentListener {
        fun onProductRefreshListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).toolbarBackIconVisibility(false)
    }

}
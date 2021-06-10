package com.celiluysal.ecommerceproductsapp.ui.products_list

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.celiluysal.ecommerceproductsapp.MainNavigationDirections
import com.celiluysal.ecommerceproductsapp.R
import com.celiluysal.ecommerceproductsapp.base.BaseFragment
import com.celiluysal.ecommerceproductsapp.databinding.ProductsListFragmentBinding
import com.celiluysal.ecommerceproductsapp.models.Product
import com.celiluysal.ecommerceproductsapp.ui.main.MainActivity
import com.celiluysal.ecommerceproductsapp.utils.SessionManager
import com.celiluysal.ecommerceproductsapp.utils.SortItem

class ProductsListFragment : BaseFragment<ProductsListFragmentBinding, ProductsListViewModel>(), LifecycleObserver {

    private val args: ProductsListFragmentArgs by navArgs()

    companion object {
        fun newInstance() = ProductsListFragment()
    }

    private lateinit var productsRecyclerViewAdapter: ProductsRecyclerViewAdapter
    private var categoryId: String? = null

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreated(){
        (activity as MainActivity).let { mainActivity ->
            mainActivity.setupSortMenu { item ->
                showLoading()
                when (item.itemId) {
                    R.id.sortByPriceDsc -> viewModel.sortItem.value = SortItem.PriceDsc
                    R.id.sortByPriceAsc -> viewModel.sortItem.value = SortItem.PriceAsc
                    R.id.sortByNewToOld -> viewModel.sortItem.value = SortItem.DateDsc
                    R.id.sortByOldToNew -> viewModel.sortItem.value = SortItem.DateAsc
                }
            }
        }
        activity?.lifecycle?.removeObserver(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.lifecycle?.addObserver(this)
    }

    override fun onDetach() {
        super.onDetach()

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ProductsListViewModel::class.java)

        categoryId = args.categoryId

        showLoading()
        observeViewModel()

        if (categoryId == null)
            binding.textViewListContent.text = getString(R.string.all_products)
        else
            SessionManager.shared.getCategoryName(categoryId!!) { categoryName, error ->
                binding.textViewListContent.text = categoryName
                (activity as MainActivity).toolbarBackIconVisibility(true)
            }

        binding.swipeRefreshLayoutProducts.setOnRefreshListener {
            viewModel.fetchAndSortProducts(categoryId)
        }
        binding.recyclerViewProducts.layoutManager = GridLayoutManager(activity, 3)

        return binding.root
    }

    private fun observeViewModel() {
        viewModel.sortItem.observe(viewLifecycleOwner, {
            viewModel.fetchAndSortProducts(categoryId)
        })

        viewModel.products.observe(viewLifecycleOwner, { products ->
            binding.swipeRefreshLayoutProducts.isRefreshing = false
            dismissLoading()
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
package com.celiluysal.ecommerceproductsapp.ui.categories

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.celiluysal.ecommerceproductsapp.MainNavigationDirections
import com.celiluysal.ecommerceproductsapp.base.BaseFragment
import com.celiluysal.ecommerceproductsapp.databinding.CategoriesFragmentBinding
import com.celiluysal.ecommerceproductsapp.models.Category

class CategoriesFragment : BaseFragment<CategoriesFragmentBinding, CategoriesViewModel>() {

    companion object {
        fun newInstance() = CategoriesFragment()
    }

    private lateinit var categoriesRecyclerViewAdapter: CategoriesRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        viewModel = ViewModelProvider(this).get(CategoriesViewModel::class.java)
        viewModel.getCategories()

        observeErrorMessage(viewLifecycleOwner)
        observeLoading(viewLifecycleOwner)

        observeViewModel()

        return binding.root
    }

    override fun observeViewModel() {
        super.observeViewModel()
        viewModel.categories.observe(viewLifecycleOwner, { categories ->
            binding.recyclerViewCategories.layoutManager = GridLayoutManager(context,3)
            categoriesRecyclerViewAdapter = CategoriesRecyclerViewAdapter(
                categories,
                object : CategoriesRecyclerViewAdapter.CategoryAdapterClickListener {
                    override fun onCategoryCardClick(item: Category, position: Int) {
                        findNavController().navigate(MainNavigationDirections.actionToProductsListFragment(item.id))
                    }

                })
            binding.recyclerViewCategories.adapter = categoriesRecyclerViewAdapter
        })
    }


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): CategoriesFragmentBinding {
        return CategoriesFragmentBinding.inflate(inflater, container, false)
    }

}
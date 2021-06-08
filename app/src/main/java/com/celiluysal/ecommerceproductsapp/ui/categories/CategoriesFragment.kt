package com.celiluysal.ecommerceproductsapp.ui.categories

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.celiluysal.ecommerceproductsapp.MainNavigationDirections
import com.celiluysal.ecommerceproductsapp.R
import com.celiluysal.ecommerceproductsapp.base.BaseFragment
import com.celiluysal.ecommerceproductsapp.databinding.CategoriesFragmentBinding
import com.celiluysal.ecommerceproductsapp.models.Category
import com.celiluysal.ecommerceproductsapp.utils.SessionManager

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

        binding.recyclerViewCategories.layoutManager = GridLayoutManager(context,3)

        SessionManager.shared.getCategories { categories ->
            categoriesRecyclerViewAdapter = CategoriesRecyclerViewAdapter(
                categories,
                object : CategoriesRecyclerViewAdapter.CategoryAdapterClickListener {
                    override fun onCategoryCardClick(item: Category, position: Int) {
                        findNavController().navigate(MainNavigationDirections.actionToProductsListFragment(item.id))
                    }

                })
            binding.recyclerViewCategories.adapter = categoriesRecyclerViewAdapter
        }

        return binding.root
    }


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): CategoriesFragmentBinding {
        return CategoriesFragmentBinding.inflate(inflater, container, false)
    }

}
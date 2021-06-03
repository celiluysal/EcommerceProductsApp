package com.celiluysal.ecommerceproductsapp.ui.categories

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.celiluysal.ecommerceproductsapp.R
import com.celiluysal.ecommerceproductsapp.base.BaseFragment
import com.celiluysal.ecommerceproductsapp.databinding.CategoriesFragmentBinding

class CategoriesFragment : BaseFragment<CategoriesFragmentBinding, CategoriesViewModel>() {

    companion object {
        fun newInstance() = CategoriesFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CategoriesViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): CategoriesFragmentBinding {
        return CategoriesFragmentBinding.inflate(inflater, container, false)
    }

}
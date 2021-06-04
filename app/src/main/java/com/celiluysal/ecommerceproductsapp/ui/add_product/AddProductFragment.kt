package com.celiluysal.ecommerceproductsapp.ui.add_product

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.celiluysal.ecommerceproductsapp.R
import com.celiluysal.ecommerceproductsapp.base.BaseFragment
import com.celiluysal.ecommerceproductsapp.databinding.AddProductFragmentBinding

class AddProductFragment : BaseFragment<AddProductFragmentBinding, AddProductViewModel>() {

    companion object {
        fun newInstance() = AddProductFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddProductViewModel::class.java)


        return binding.root
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): AddProductFragmentBinding {
        return AddProductFragmentBinding.inflate(inflater, container, false)
    }

}
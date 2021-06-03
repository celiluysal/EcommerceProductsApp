package com.celiluysal.ecommerceproductsapp.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.celiluysal.ecommerceproductsapp.R
import com.celiluysal.ecommerceproductsapp.base.BaseFragment
import com.celiluysal.ecommerceproductsapp.databinding.HomeFragmentBinding

class HomeFragment : BaseFragment<HomeFragmentBinding, HomeViewModel>() {

    companion object {
        fun newInstance() = HomeFragment()
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
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): HomeFragmentBinding {
        return HomeFragmentBinding.inflate(inflater, container, false)
    }

}
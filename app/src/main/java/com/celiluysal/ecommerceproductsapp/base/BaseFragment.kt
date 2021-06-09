package com.celiluysal.ecommerceproductsapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.celiluysal.ecommerceproductsapp.ui.loading_dialog.LoadingDialog

abstract class BaseFragment<VB: ViewBinding, VM: ViewModel>: Fragment() {
    protected lateinit var binding: VB
    lateinit var viewModel: VM
    private lateinit var loadingDialog: LoadingDialog


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getViewBinding(inflater, container)
        return binding.root
    }

    protected abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    fun startLoading() {
        loadingDialog = LoadingDialog()
        activity?.supportFragmentManager?.let {
            loadingDialog.show(it, "LoadingDialog")
        }
    }

    fun stopLoading() {
        loadingDialog.dismiss()
    }

}
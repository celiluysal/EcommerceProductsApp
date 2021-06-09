package com.celiluysal.ecommerceproductsapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.celiluysal.ecommerceproductsapp.R
import com.celiluysal.ecommerceproductsapp.ui.loading_dialog.LoadingDialog
import com.celiluysal.ecommerceproductsapp.ui.message_dialog.MessageDialog

abstract class BaseFragment<VB: ViewBinding, VM: ViewModel>: Fragment() {
    protected lateinit var binding: VB
    lateinit var viewModel: VM

    var messageDialog: MessageDialog? = null
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

    fun initSimpleMessageDialog() {
        messageDialog = MessageDialog(
            object : MessageDialog.MessageDialogListener {
                override fun onLeftButtonClick() {
                    messageDialog?.dismiss()
                }
            })
        messageDialog?.leftButtonText = getString(R.string.okay)
        messageDialog?.rightButtonText = null
    }

    fun showMessageDialog(message: String) {
        messageDialog?.setMessage(message)
        activity?.supportFragmentManager?.let {
            messageDialog?.show(
                it,
                "MessageDialog"
            )
        }
    }

    fun showLoading() {
        loadingDialog = LoadingDialog()
        activity?.supportFragmentManager?.let {
            loadingDialog.show(it, "LoadingDialog")
        }
    }

    fun dismissLoading() {
        loadingDialog.dismiss()
    }

}
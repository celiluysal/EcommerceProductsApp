package com.celiluysal.ecommerceproductsapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.celiluysal.ecommerceproductsapp.R
import com.celiluysal.ecommerceproductsapp.ui.loading_dialog.LoadingDialog
import com.celiluysal.ecommerceproductsapp.ui.message_dialog.MessageDialog
import com.celiluysal.ecommerceproductsapp.utils.LOADING_DIALOG_TAG
import com.celiluysal.ecommerceproductsapp.utils.MESSAGE_DIALOG_TAG

abstract class BaseFragment<VB: ViewBinding, VM: BaseViewModel>: Fragment() {
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
        loadingDialog = LoadingDialog()


        return binding.root
    }

    fun observeLoading(lifecycleOwner: LifecycleOwner) {
        viewModel.isLoading.observe(lifecycleOwner, {
            if (it) showLoading() else dismissLoading()
        })
    }

    fun observeErrorMessage(lifecycleOwner: LifecycleOwner) {
        viewModel.errorMessage.observe(lifecycleOwner, {
            initSimpleMessageDialog()
            showMessageDialog(it)
        })
    }

    protected abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    open fun observeViewModel() {}

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
                MESSAGE_DIALOG_TAG
            )
        }
    }

    private fun showLoading() {
        loadingDialog = LoadingDialog()
        activity?.supportFragmentManager?.let {
            loadingDialog.show(it, LOADING_DIALOG_TAG)
        }
    }

    private fun dismissLoading() {
        if (loadingDialog.isVisible)
            loadingDialog.dismiss()
    }

}
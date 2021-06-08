package com.celiluysal.ecommerceproductsapp.ui.loading_dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.celiluysal.ecommerceproductsapp.R
import com.celiluysal.ecommerceproductsapp.databinding.DialogLoadingBinding

class LoadingDialog(): DialogFragment() {
    private lateinit var binding: DialogLoadingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.dialog_background)
        binding = DialogLoadingBinding.inflate(layoutInflater)

        return binding.root
    }
}
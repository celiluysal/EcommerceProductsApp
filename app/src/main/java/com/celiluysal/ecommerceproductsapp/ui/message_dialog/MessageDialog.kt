package com.celiluysal.ecommerceproductsapp.ui.message_dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.celiluysal.ecommerceproductsapp.R
import com.celiluysal.ecommerceproductsapp.databinding.DialogMessageBinding

class MessageDialog(val messageText: String, val clickListener: MessageDialogListener): DialogFragment() {
    private lateinit var binding: DialogMessageBinding

    var leftButtonText: String? = null
    var rightButtonText: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.dialog_background)
        binding = DialogMessageBinding.inflate(layoutInflater)

        binding.textViewDialogMessage.text = messageText

        binding.buttonLeft.visibility = Button.GONE
        binding.buttonRight.visibility = Button.GONE

        leftButtonText?.let { text ->
            binding.buttonLeft.visibility = Button.VISIBLE
            binding.buttonLeft.text = text
            binding.buttonLeft.setOnClickListener { clickListener.onLeftButtonClick() }
        }

        rightButtonText?.let { text ->
            binding.buttonRight.visibility = Button.VISIBLE
            binding.buttonRight.text = text
            binding.buttonRight.setOnClickListener { clickListener.onRightButtonClick() }
        }
        return binding.root
    }

    interface MessageDialogListener {
        fun onLeftButtonClick() {}
        fun onRightButtonClick() {}
    }
}
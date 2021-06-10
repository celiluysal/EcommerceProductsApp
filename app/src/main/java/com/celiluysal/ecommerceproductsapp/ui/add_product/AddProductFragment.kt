package com.celiluysal.ecommerceproductsapp.ui.add_product

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.celiluysal.ecommerceproductsapp.MainNavigationDirections
import com.celiluysal.ecommerceproductsapp.R
import com.celiluysal.ecommerceproductsapp.base.BaseFragment
import com.celiluysal.ecommerceproductsapp.databinding.AddProductFragmentBinding
import com.celiluysal.ecommerceproductsapp.models.Product
import com.celiluysal.ecommerceproductsapp.models.ProductRequestModel
import com.celiluysal.ecommerceproductsapp.ui.main.MainActivity
import com.celiluysal.ecommerceproductsapp.ui.message_dialog.MessageDialog
import com.celiluysal.ecommerceproductsapp.utils.SessionManager
import com.celiluysal.ecommerceproductsapp.utils.Utils
import com.celiluysal.ecommerceproductsapp.utils.isEmpty
import java.io.File

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

        findNavController().navigate(AddProductFragmentDirections.actionAddProductFragmentToEditProductFragment())

        return binding.root
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): AddProductFragmentBinding {
        return AddProductFragmentBinding.inflate(inflater, container, false)
    }
}
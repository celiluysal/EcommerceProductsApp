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
import com.celiluysal.ecommerceproductsapp.R
import com.celiluysal.ecommerceproductsapp.base.BaseFragment
import com.celiluysal.ecommerceproductsapp.databinding.AddProductFragmentBinding
import com.celiluysal.ecommerceproductsapp.models.ProductRequestModel
import com.celiluysal.ecommerceproductsapp.ui.message_dialog.MessageDialog
import com.celiluysal.ecommerceproductsapp.utils.SessionManager
import com.celiluysal.ecommerceproductsapp.utils.Utils
import com.celiluysal.ecommerceproductsapp.utils.isEmpty
import java.io.File

class AddProductFragment : BaseFragment<AddProductFragmentBinding, AddProductViewModel>() {

    companion object {
        fun newInstance() = AddProductFragment()
    }

    private var price: Double? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddProductViewModel::class.java)

        setSpinnerCategory()
        initSimpleMessageDialog()

        binding.includeProductFields.buttonSave.setOnClickListener {
            binding.includeProductFields.textInputEditTextProductName.isEmpty(context)?.let {
                showMessageDialog(getString(R.string.product_name) + " " + it)
                return@setOnClickListener
            }

            binding.includeProductFields.textInputEditTextProductDescription.isEmpty(context)?.let {
                showMessageDialog(getString(R.string.product_description) + " " + getString(R.string.field_cant_be_empty))
                return@setOnClickListener
            }

            binding.includeProductFields.textInputEditTextProductPrice.isEmpty(context)?.let {
                showMessageDialog(getString(R.string.price) + " " + getString(R.string.field_cant_be_empty))
                return@setOnClickListener
            }

            if (photo == null) {
                showMessageDialog(getString(R.string.product_photo) + " " + getString(R.string.field_cant_be_empty))
                return@setOnClickListener
            }

            showLoading()
            viewModel.addProduct(
                ProductRequestModel(
                    name = binding.includeProductFields.textInputEditTextProductName.text.toString(),
                    description = binding.includeProductFields.textInputEditTextProductDescription.text.toString(),
                    categoryId = binding.includeProductFields.spinnerCategory.selectedItemPosition.toString(),
                    price = binding.includeProductFields.textInputEditTextProductPrice.text.toString().toDouble(),
                    photo = this.photo!!
                )
            ) { product, error ->
                dismissLoading()
                if (product != null) {
                    messageDialog = MessageDialog(
                        object : MessageDialog.MessageDialogListener {
                            override fun onLeftButtonClick() {
                                clearFields()
                                messageDialog?.dismiss()
                            }
                        })
                    messageDialog?.leftButtonText = getString(R.string.okay)
                    showMessageDialog(getString(R.string.add_product_success))
                }
            }


        }

        binding.includeProductFields.imageViewProduct.setOnClickListener {
            if (hasNoPermissions()) {
                requestPermission()
            } else
                takePhoto()
        }

        return binding.root
    }

    fun clearFields() {
        binding.includeProductFields.textInputEditTextProductName.text?.clear()
        binding.includeProductFields.textInputEditTextProductDescription.text?.clear()
        binding.includeProductFields.textInputEditTextProductPrice.text?.clear()
        binding.includeProductFields.imageViewProduct.setImageResource(R.drawable.im_take_photo)
    }

    private val permissions = arrayOf(android.Manifest.permission.CAMERA)
    private fun hasNoPermissions(): Boolean {
        if (context == null)
            return false
        return ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.CAMERA
        ) != PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(context as Activity, permissions, 0)
    }

    private lateinit var photoFile: File
    private var photo: Bitmap? = null
    private val photoFileName = "photo.jpeg"

    @SuppressLint("QueryPermissionsNeeded")
    fun takePhoto() {
        if (context == null)
            return

        val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE)
        val storageDirectory = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        photoFile = File(storageDirectory, photoFileName)
        val fileProvider = FileProvider.getUriForFile(
            requireContext(),
            "com.celiluysal.ecommerceproductsapp.fileprovider",
            photoFile
        )

        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
        if (takePhotoIntent.resolveActivity(requireContext().packageManager) != null) {
            resultLauncher.launch(takePhotoIntent)
        } else {
            Toast.makeText(requireContext(), "Unable to open camera", Toast.LENGTH_SHORT).show()
        }
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                var photo = BitmapFactory.decodeFile(photoFile.absolutePath)
                photo = Utils.shared.originalRotate(photo, photoFile)
                photo = Utils.shared.resizeBitmap(photo, 1024)

                this.photo = photo
                binding.includeProductFields.imageViewProduct.setImageBitmap(photo)
            }
        }

    private fun setSpinnerCategory() {
        SessionManager.shared.getCategoryNameList { categoryNameList ->
            binding.includeProductFields.spinnerCategory.adapter =
                context?.let {
                    ArrayAdapter(
                        it,
                        android.R.layout.simple_list_item_1,
                        categoryNameList
                    )
                }
            binding.includeProductFields.spinnerCategory.bringToFront()
            binding.includeProductFields.spinnerCategory.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(p0: AdapterView<*>?) {}

                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        if (categoryNameList[p2] != getString(R.string.category)) {
                            binding.includeProductFields.textInputEditTextSpinnerCategory.setText(" ")
                            binding.includeProductFields.textInputSpinnerCategory.hint =
                                getString(R.string.category)
                        }
                    }
                }
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): AddProductFragmentBinding {
        return AddProductFragmentBinding.inflate(inflater, container, false)
    }

}
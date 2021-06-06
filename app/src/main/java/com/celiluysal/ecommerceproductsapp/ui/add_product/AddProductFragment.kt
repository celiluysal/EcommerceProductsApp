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
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.util.Util
import com.celiluysal.ecommerceproductsapp.R
import com.celiluysal.ecommerceproductsapp.base.BaseFragment
import com.celiluysal.ecommerceproductsapp.databinding.AddProductFragmentBinding
import com.celiluysal.ecommerceproductsapp.models.ProductRequestModel
import com.celiluysal.ecommerceproductsapp.utils.SessionManager
import com.celiluysal.ecommerceproductsapp.utils.Utils
import java.io.File
import java.util.jar.Manifest

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

        binding.buttonSave.setOnClickListener {
            if (checkFields()) {
                viewModel.addProduct(
                    ProductRequestModel(
                        name = binding.textInputEditTextProductName.text.toString(),
                        description = binding.textInputEditTextProductDescription.text.toString(),
                        categoryId = binding.spinnerCategory.selectedItemPosition.toString(),
                        price = binding.textInputEditTextProductPrice.text.toString().toDouble(),
                        photo = this.photo!!
                    )
                )

            }
        }

        binding.imageViewProduct.setOnClickListener {
            if (hasNoPermissions()) {
                requestPermission()
            }
            else
                takePhoto()
        }

        return binding.root
    }

    val permissions = arrayOf(android.Manifest.permission.CAMERA)
//    val permissions = arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)

    private fun hasNoPermissions(): Boolean{
        if (context == null)
            return false
        return ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission(){
        ActivityCompat.requestPermissions(context as Activity, permissions,0)
    }


    private lateinit var photoFile: File
    private var photo: Bitmap? = null
    private val PHOTO_FILE_NAME = "photo.jpeg"

    @SuppressLint("QueryPermissionsNeeded")
    fun takePhoto() {
        if (context == null)
            return

        val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE)
        val storageDirectory = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        photoFile = File(storageDirectory, PHOTO_FILE_NAME)
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
                binding.imageViewProduct.setImageBitmap(photo)
            }
        }

    private fun setSpinnerCategory() {
        SessionManager.shared.getCategoryNameList { categoryNameList ->
            binding.spinnerCategory.adapter =
                context?.let {
                    ArrayAdapter(
                        it,
                        android.R.layout.simple_list_item_1,
                        categoryNameList
                    )
                }
            binding.spinnerCategory.bringToFront()
            binding.spinnerCategory.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(p0: AdapterView<*>?) {}

                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        if (categoryNameList[p2] != getString(R.string.category)) {
                            binding.textInputEditTextSpinnerCategory.setText(" ")
                            binding.textInputSpinnerCategory.hint = getString(R.string.category)
                        }
                    }
                }
        }


    }

    private fun checkFields(): Boolean {
        fun toast(text: String) = Toast.makeText(context, text, Toast.LENGTH_SHORT).show()

        when {
            photo == null -> {
                toast("alan boş olamaz")
                return false
            }
            binding.textInputEditTextProductName.text.toString().isBlank() -> {
                toast("alan boş olamaz")
                return false
            }
            binding.textInputEditTextProductDescription.text.toString().isBlank() -> {
                toast("alan boş olamaz")
                return false
            }
            binding.spinnerCategory.selectedItem == getString(R.string.category) -> {
                toast("alan boş olamaz")
                return false
            }
            binding.textInputEditTextProductPrice.text.toString().isBlank() -> {
                toast("alan boş olamaz")
                return false
            }
            else -> {
                val priceValue = binding.textInputEditTextProductPrice.text.toString().toDouble()
                price = priceValue
            }
        }
        return true
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): AddProductFragmentBinding {
        return AddProductFragmentBinding.inflate(inflater, container, false)
    }

}
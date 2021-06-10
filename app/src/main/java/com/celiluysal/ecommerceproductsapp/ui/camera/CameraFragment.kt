package com.celiluysal.ecommerceproductsapp.ui.camera

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
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.celiluysal.ecommerceproductsapp.R
import com.celiluysal.ecommerceproductsapp.utils.Utils
import java.io.File

class CameraFragment : Fragment() {

    companion object {
        fun newInstance() = CameraFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.camera_fragment, container, false)
    }

    private val permissions = arrayOf(android.Manifest.permission.CAMERA)
    private fun hasNoPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.CAMERA
        ) != PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(context as Activity, permissions, 0)
    }

    private lateinit var photoFile: File
    //    private var photo: Bitmap? = null
    private val photoFileName = "photo.jpeg"

    @SuppressLint("QueryPermissionsNeeded")
    fun takePhoto(Result:(photo: Bitmap)->Unit) {
        if (context == null)
            return

        if (hasNoPermissions()) {
            requestPermission()
            return
        }

        val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE)
        val storageDirectory = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        photoFile = File(storageDirectory, photoFileName)
        val fileProvider = FileProvider.getUriForFile(
            requireContext(),
            "com.celiluysal.ecommerceproductsapp.fileprovider",
            photoFile
        )

        val resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    var photo = BitmapFactory.decodeFile(photoFile.absolutePath)
                    photo = Utils.shared.originalRotate(photo, photoFile)
                    photo = Utils.shared.resizeBitmap(photo, 1024)

                    Result.invoke(photo)

//                    this.photo = photo
//                binding.includeProductFields.imageViewProduct.setImageBitmap(photo)
                }
            }

        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
        if (takePhotoIntent.resolveActivity(requireContext().packageManager) != null) {
            resultLauncher.launch(takePhotoIntent)
        } else {
            Toast.makeText(requireContext(), "Unable to open camera", Toast.LENGTH_SHORT).show()
        }
    }
}
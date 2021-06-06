package com.celiluysal.ecommerceproductsapp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.ExifInterface
import android.util.TypedValue
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class Utils {
    companion object {
        val shared = Utils()
    }
    @SuppressLint("SimpleDateFormat")
    private val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm:ss")

    fun dayTimeStamp(): String = simpleDateFormat.format(Date())

    fun dpToPx(context: Context, valueInDp: Float): Float {
        val metrics = context.resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics)
    }

    fun originalRotate(photo: Bitmap, file: File): Bitmap {
        var bitmap = photo
        val exif = ExifInterface(file.path)
        var orientation = exif.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL
        )
        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> bitmap = rotateBitmap(bitmap, 90f)
            ExifInterface.ORIENTATION_ROTATE_180 -> bitmap = rotateBitmap(bitmap, 180f)
            ExifInterface.ORIENTATION_ROTATE_270 -> bitmap = rotateBitmap(bitmap, 270f)
        }
        return bitmap
    }

    fun rotateBitmap(photo: Bitmap, degree: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree)
        return Bitmap.createBitmap(photo, 0, 0, photo.width, photo.height, matrix, true)
    }

    fun resizeBitmap(photo: Bitmap, maxLength: Int = 1024): Bitmap {
        val photoMaxLength = if (photo.width > photo.height) photo.width else photo.height
        val ratio = photoMaxLength.toFloat() / maxLength.toFloat()
        return Bitmap.createScaledBitmap(
            photo,
            (photo.width.toFloat() / ratio).toInt(),
            (photo.height.toFloat() / ratio).toInt(),
            false
        )
    }
}
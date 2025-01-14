package com.example.readers_app.core.utils

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import java.io.File

fun saveBitmapToFile(context: Context, bitmap: ImageBitmap): File? {
    return try {
        val file = File(context.cacheDir, "profile_image.jpg")

        file.outputStream().use { stream ->
            bitmap.asAndroidBitmap().compress(Bitmap.CompressFormat.JPEG, 100, stream)
        }
        file
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
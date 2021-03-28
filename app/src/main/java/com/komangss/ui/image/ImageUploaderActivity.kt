  package com.komangss.ui.image

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.komangss.R
import com.komangss.datasource.network.instance.RetrofitBuilder
import kotlinx.android.synthetic.main.activity_image_uploader.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import pub.devrel.easypermissions.EasyPermissions
import java.io.File


class ImageUploaderActivity : AppCompatActivity() {
    private val PICK_FROM_GALLERY: Int = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_uploader)

        btn_choose_file.setOnClickListener {
//            Dont Forget to add storage permission
//            FilePickerBuilder.instance.pickFile(this@ImageUploaderActivity)
//            Error Permission Needed - Solution :
            takeImageTask()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            PICK_FROM_GALLERY -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val selectedImage = data.data // uri
                    val path = getPath(this@ImageUploaderActivity, selectedImage)
                    val file = File(path)
                    Glide.with(this@ImageUploaderActivity).load(file).into(imageView)
                    val requestFile: RequestBody = RequestBody.create(
                        MediaType.parse(contentResolver.getType(selectedImage!!)),
                        file
                    )
                    val body = MultipartBody.Part.createFormData("image", file.name, requestFile)
                    lifecycleScope.launch {
                        val res = RetrofitBuilder.authServices.uploadImage(body)
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@ImageUploaderActivity, res, Toast.LENGTH_SHORT)
                                .show()
                            Log.d("result", res)
                        }
                    }
                }
            }
        }
    }


    private fun getPath(context: Context, uri: Uri?): String {
        var result: String? = null
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? =
            context.contentResolver.query(uri!!, projection, null, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val columnIndex: Int = cursor.getColumnIndexOrThrow(projection[0])
                result = cursor.getString(columnIndex)
            }
            cursor.close()
        }
        if (result == null) {
            result = "Not found"
        }
        return result
    }

    override fun onRequestPermissionsResult(requestCode:Int,
                                            permissions:Array<String>,
                                            grantResults:IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }


    private fun hasStoragePermission() : Boolean {
        return EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    fun takeImageTask() {
        if(hasStoragePermission()) {
            val galleryIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, PICK_FROM_GALLERY)
        } else {

            EasyPermissions.requestPermissions(
                this,
                "This app needs access to your storage so you can take pictures.",
                PICK_FROM_GALLERY,
                Manifest.permission.READ_EXTERNAL_STORAGE

            )
        }
    }
}
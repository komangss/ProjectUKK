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
            try {
                if (ActivityCompat.checkSelfPermission(this@ImageUploaderActivity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                } else {
                    val galleryIntent =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(galleryIntent, PICK_FROM_GALLERY)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
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
                            Log.d("result", res.toString())
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PICK_FROM_GALLERY ->                 // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] === PackageManager.PERMISSION_GRANTED) {
                    val galleryIntent =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(galleryIntent, PICK_FROM_GALLERY)

                    EasyPermissions.onRequestPermissionsResult(
                        requestCode,
                        permissions,
                        grantResults,
                        this
                    )
                } else {
                    //do something like displaying a message that he didn`t allow the app to access gallery and you wont be able to let him select from gallery
                }
        }
    }
}
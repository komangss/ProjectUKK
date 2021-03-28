package com.komangss.ui.pengaduan

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.komangss.R
import com.komangss.datasource.network.instance.RetrofitBuilder.pengaduanServices
import kotlinx.android.synthetic.main.activity_create_pengaduan.*
import kotlinx.android.synthetic.main.activity_masyarakat_dashboard.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.io.File

class CreatePengaduanActivity : AppCompatActivity(),
    EasyPermissions.PermissionCallbacks,
    EasyPermissions.RationaleCallbacks {

    companion object {
        const val REQUEST_CODE_IMAGE = 201
    }

    private lateinit var uri: Uri
    private lateinit var imageFile : File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_pengaduan)

        createPengaduanBTN.setOnClickListener {
            val requestBody = RequestBody.create(MediaType.parse(contentResolver.getType(uri)), imageFile)

            val multiPartBody = MultipartBody.Part.createFormData("image", imageFile.name, requestBody)
            lifecycleScope.launch {
                pengaduanServices.createPengaduan(multiPartBody, tiet_create_pengaduan_isi_pengaduan.text.toString())
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@CreatePengaduanActivity, "Pengaduan Inserted Successfuly", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }

        btn_choose_image_pengaduan.setOnClickListener {
            takeImageTask()
        }
    }

    private fun takeImageTask() {
        if(hasReadStoragePermission()) {
            startActivityForResult(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), REQUEST_CODE_IMAGE)
        } else {
            EasyPermissions.requestPermissions(
                this@CreatePengaduanActivity,
                "This app needs access to your storage so you can take pictures.",
                REQUEST_CODE_IMAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }

    private fun hasReadStoragePermission(): Boolean {
        return EasyPermissions.hasPermissions(this@CreatePengaduanActivity, Manifest.permission.READ_EXTERNAL_STORAGE)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
//        Toast permission granted
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms))
        {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onRationaleAccepted(requestCode: Int) {
    }

    override fun onRationaleDenied(requestCode: Int) {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && data != null && requestCode == REQUEST_CODE_IMAGE) {
            this.uri = data.data!!
            this.imageFile = File(getPath(this@CreatePengaduanActivity, uri))
            Glide.with(this).load(imageFile).into(img_pengaduan)
        }
    }

    private fun getPath(ctx : Context, uri : Uri): String {
        var result = ""
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor : Cursor? =  ctx.contentResolver.query(uri, projection, null, null, null)
        if(cursor != null) {
            if(cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndexOrThrow(projection[0])
                result = cursor.getString(columnIndex)
            }
            cursor.close()
        }

        return result
    }
}
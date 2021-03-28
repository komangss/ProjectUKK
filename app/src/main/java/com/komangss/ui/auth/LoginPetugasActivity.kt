package com.komangss.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.komangss.R
import com.komangss.datasource.network.instance.RetrofitBuilder
import com.komangss.repository.AuthRepository
import com.komangss.ui.image.ImageUploaderActivity
import com.komangss.util.Resource
import kotlinx.android.synthetic.main.activity_login_petugas.*
import kotlinx.coroutines.launch

class LoginPetugasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_petugas)
        val repo = AuthRepository.getInstance(RetrofitBuilder.authServices)

        btn_activity_login_sign_in.setOnClickListener {
            val username = tiet_activity_login_username.text.toString().trim()
            val password = tiet_activity_login_password.text.toString().trim()
            when {
                username == "" -> til_activity_login_username.error = "This Fields cannot be empty"
                password == "" -> til_activity_login_password.error = "This Fields cannot be empty"
                else -> lifecycleScope.launch {
                    when (val response = repo.sendLoginRequest(username, password, "petugas")) {
                        is Resource.Error -> {
                            Toast.makeText(this@LoginPetugasActivity, response.exception.message, Toast.LENGTH_SHORT).show()
                        }
                        is Resource.Success -> {
                            Toast.makeText(this@LoginPetugasActivity, response.data, Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@LoginPetugasActivity, ImageUploaderActivity::class.java))
                        }
                        else -> Toast.makeText(this@LoginPetugasActivity, "Unknown Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
package com.komangss.ui.auth

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.komangss.R
import com.komangss.datasource.network.instance.RetrofitBuilder
import com.komangss.repository.AuthRepository
import com.komangss.util.Resource
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btn_activity_register_sign_up.setOnClickListener {

            val username = tiet_activity_register_username.text.toString().trim()
            val password = tiet_activity_register_password.text.toString().trim()
            val nik = tiet_activity_register_nik.text.toString().trim()
            val name = tiet_activity_register_name.text.toString().trim()
            val telp = tiet_activity_register_telp.text.toString().trim()

            val repo = AuthRepository.getInstance(RetrofitBuilder.authServices)
            when {
                username == "" -> til_activity_register_username.error = "This Fields cannot be empty"
                password == "" -> til_activity_register_password.error = "This Fields cannot be empty"
                nik == "" -> til_activity_register_nik.error = "This Fields cannot be empty"
                name == "" -> til_activity_register_name.error = "This Fields cannot be empty"
                telp == "" -> til_activity_register_telp.error = "This Fields cannot be empty"
                else -> lifecycleScope.launch {
                    when (val response = repo.sendRegisterMasyarakatRequest(username, password, nik, name, telp)) {
                        is Resource.Error -> {
                            Toast.makeText(this@RegisterActivity, response.exception.message, Toast.LENGTH_SHORT).show()
                        }
                        is Resource.Success -> {
                            Toast.makeText(this@RegisterActivity, response.data, Toast.LENGTH_SHORT).show()
                        }
                        else -> Toast.makeText(this@RegisterActivity, "Unknown Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
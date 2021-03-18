package com.komangss.ui.login

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.komangss.R
import com.komangss.datasource.network.instance.RetrofitBuilder
import com.komangss.repository.AuthRepository
import com.komangss.util.Resource
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val repo = AuthRepository.getInstance(RetrofitBuilder.authServices)
        btn_activity_login_sign_in.setOnClickListener {
            val username = tiet_activity_login_username.text.toString().trim()
            val password = tiet_activity_login_password.text.toString().trim()
            when {
                username == "" -> til_activity_login_username.error = "This Fields cannot be empty"
                password == "" -> til_activity_login_password.error = "This Fields cannot be empty"
                else -> lifecycleScope.launch {
                    when (val response = repo.sendLoginRequest(username, password, "masyarakat")) {
                        is Resource.Error -> {
                            Toast.makeText(this@LoginActivity, response.exception.message, Toast.LENGTH_SHORT).show()
                        }
                        is Resource.Success -> {
                            Toast.makeText(this@LoginActivity, response.data, Toast.LENGTH_SHORT).show()
                        }
                        else -> Toast.makeText(this@LoginActivity, "Unknown Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
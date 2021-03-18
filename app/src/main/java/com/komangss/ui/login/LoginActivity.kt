package com.komangss.ui.login

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.komangss.R
import com.komangss.datasource.network.instance.RetrofitBuilder
import com.komangss.repository.AuthRepository
import com.komangss.util.Resource
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val repo = AuthRepository.getInstance(RetrofitBuilder.authServices)
        btn_activity_main_sign_in.setOnClickListener {
            val username = text_view_activity_main_username.text.toString()
            val password = text_view_activity_main_password.text.toString()
            lifecycleScope.launch {
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
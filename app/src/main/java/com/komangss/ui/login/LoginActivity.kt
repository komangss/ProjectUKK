package com.komangss.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.komangss.R
import com.komangss.datasource.network.instance.RetrofitBuilder
import com.komangss.factory.AuthViewModelFactory
import com.komangss.repository.AuthRepository
import com.komangss.ui.login.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val repo = AuthRepository.getInstance(RetrofitBuilder.authServices)
        val vmFactory = AuthViewModelFactory.getInstance(repo)
        val authViewModel = ViewModelProvider(this, vmFactory)[LoginViewModel::class.java]
        
        btn_activity_main_sign_in.setOnClickListener {

        }
    }
}
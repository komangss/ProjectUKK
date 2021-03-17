package com.komangss.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.komangss.R
import com.komangss.util.Resource

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Resource.Success("haha")
    }
}
package com.komangss.ui.dashboard.masyarakat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.komangss.R
import com.komangss.ui.pengaduan.CreatePengaduanActivity
import kotlinx.android.synthetic.main.activity_masyarakat_dashboard.*

class MasyarakatDashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_masyarakat_dashboard)

        createPengaduanBTN.setOnClickListener {
            startActivity(Intent(this@MasyarakatDashboardActivity, CreatePengaduanActivity::class.java))
        }
    }
}
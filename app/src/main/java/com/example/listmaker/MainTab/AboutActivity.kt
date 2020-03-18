package com.example.listmaker.MainTab


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.listmaker.R
import com.example.listmaker.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {
    lateinit var binding: ActivityAboutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_about
        )
        binding.aboutTool.setNavigationOnClickListener {
            onBackPressed()
            finish()
        }

    }
}



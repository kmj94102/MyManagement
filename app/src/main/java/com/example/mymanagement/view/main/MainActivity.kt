package com.example.mymanagement.view.main

import android.os.Bundle
import com.example.mymanagement.R
import com.example.mymanagement.databinding.ActivityMainBinding
import com.example.mymanagement.view.base.BindingActivity
import com.example.mymanagement.view.compose.ComposeMainActivity
import com.example.mymanagement.util.startActivity
import com.example.mymanagement.view.xml.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnCompose.setOnClickListener {
            startActivity(ComposeMainActivity::class.java)
            finish()
        }

        binding.btnXml.setOnClickListener {
            startActivity(HomeActivity::class.java)
            finish()
        }

        binding.btnXml.performClick()

    }
}
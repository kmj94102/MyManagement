package com.example.mymanagement.ui.main

import android.os.Bundle
import com.example.mymanagement.R
import com.example.mymanagement.databinding.ActivityMainBinding
import com.example.mymanagement.ui.base.BindingActivity
import com.example.mymanagement.ui.compose.ComposeMainActivity
import com.example.mymanagement.util.startActivity
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

        }

        binding.btnCompose.performClick()

    }
}
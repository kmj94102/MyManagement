package com.example.mymanagement.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.mymanagement.R
import androidx.activity.viewModels
import androidx.core.view.isVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    lateinit var btnLogin: Button
    lateinit var btnLogout: Button
    lateinit var txtUserInfo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLogin = findViewById(R.id.btnLogin)
        btnLogout = findViewById(R.id.btnLogout)
        txtUserInfo = findViewById(R.id.txtLoginInfo)

        checkLoggedIn()

        btnLogin.setOnClickListener {
            viewModel.loginWithKakaoAccount(
                context = this,
                successListener = {
                    viewModel.getUserInfo()
                    checkLoggedIn()
                },
                failureListener = {
                    txtUserInfo.text = "로그인 실패"
                }
            )
        }

        btnLogout.setOnClickListener {
            viewModel.logout()
            checkLoggedIn()
        }
    }

    private fun checkLoggedIn() {
        btnLogin.isVisible = viewModel.userInfo == null
        btnLogout.isVisible = viewModel.userInfo != null
        viewModel.userInfo?.let {
            txtUserInfo.text = it.kakaoAccount?.profile?.nickname?.plus('\n')
                ?.plus(it.kakaoAccount?.profile?.thumbnailImageUrl)
        }
    }
}
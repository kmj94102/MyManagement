package com.example.mymanagement.ui.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.kakao_api.service.KakaoService
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val client: KakaoService
) : ViewModel() {

    var userInfo: User? = null
        private set

    init {
        getUserInfo()
    }

    fun loginWithKakaoAccount(
        context: Context,
        successListener: () -> Unit,
        failureListener: () -> Unit
    ) {
        client.loginWithKakaoAccount(
            context = context,
            callback = { token: OAuthToken?, error ->
                error?.printStackTrace()
                token?.let {
                    successListener()
                } ?: failureListener()
            }
        )
    }

    fun getUserInfo() {
        client.getUserInfo { user, throwable ->
            userInfo = user
            throwable?.printStackTrace()
        }
    }

    fun logout() {
        client.logout {
            it?.printStackTrace()
            if (it == null) userInfo = null
        }
    }

}
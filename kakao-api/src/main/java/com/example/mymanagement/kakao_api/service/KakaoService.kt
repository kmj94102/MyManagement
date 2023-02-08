package com.example.kakao_api.service

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.model.User

interface KakaoService {

    fun getUserInfo(
        callback: (User?, Throwable?) -> Unit
    )

    fun loginWithKakaoAccount(
        context: Context,
        callback: (OAuthToken?, Throwable?) -> Unit
    )

    fun loginWithKakaoTalk(
        context: Context,
        callback: (OAuthToken?, Throwable?) -> Unit
    )

    fun logout(callback: (Throwable?) -> Unit)

}
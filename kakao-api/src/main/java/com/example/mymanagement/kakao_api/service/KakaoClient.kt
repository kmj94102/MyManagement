package com.example.mymanagement.kakao_api.service

import android.content.Context
import com.example.kakao_api.service.KakaoService
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.model.User
import javax.inject.Inject

class KakaoClient @Inject constructor(
    private val service: KakaoService,
) {

    fun loginWithKakaoTalk(
        context: Context,
        callback: (OAuthToken?, Throwable?) -> Unit
    ) = service.loginWithKakaoTalk(
        context = context,
        callback = callback
    )

    fun loginWithKakaoAccount(
        context: Context,
        callback: (OAuthToken?, Throwable?) -> Unit
    ) = service.loginWithKakaoAccount(
        context = context,
        callback = callback
    )

    fun logout(
        callback: (Throwable?) -> Unit
    ) = service.logout(callback = callback)

    fun getUserInfo(
        callback: (User?, Throwable?) -> Unit
    ) = service.getUserInfo(callback = callback)

}
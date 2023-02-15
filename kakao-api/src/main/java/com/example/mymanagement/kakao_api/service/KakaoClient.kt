package com.example.mymanagement.kakao_api.service

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.model.User
import javax.inject.Inject

class KakaoClient @Inject constructor(
    private val loginService: KakaoLoginService,
) {

    fun loginWithKakaoTalk(
        context: Context,
        callback: (OAuthToken?, Throwable?) -> Unit
    ) = loginService.loginWithKakaoTalk(
        context = context,
        callback = callback
    )

    fun loginWithKakaoAccount(
        context: Context,
        callback: (OAuthToken?, Throwable?) -> Unit
    ) = loginService.loginWithKakaoAccount(
        context = context,
        callback = callback
    )

    fun logout(
        callback: (Throwable?) -> Unit
    ) = loginService.logout(callback = callback)

    fun getUserInfo(
        context: Context,
        callback: (User?, Throwable?) -> Unit
    ) = loginService.getUserInfo(context = context, callback = callback)

}
package com.example.kakao_api.service

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.User
import javax.inject.Inject

class KakaoServiceImpl @Inject constructor(
    private val userApiClient: UserApiClient
) : KakaoService {

    override fun getUserInfo(
        callback: (User?, Throwable?) -> Unit,
    ) {
        userApiClient.me { user, error ->
            callback(user, error)
        }
    }

    override fun loginWithKakaoAccount(
        context: Context,
        callback: (OAuthToken?, Throwable?) -> Unit
    ) {
        userApiClient.loginWithKakaoAccount(
            context = context,
            callback = callback
        )
    }

    override fun loginWithKakaoTalk(
        context: Context,
        callback: (OAuthToken?, Throwable?) -> Unit
    ) {
        userApiClient.loginWithKakaoTalk(
            context = context,
            callback = callback
        )
    }

    override fun logout(
        callback: (Throwable?) -> Unit
    ) {
        userApiClient.logout {
            callback(it)
        }
    }
}
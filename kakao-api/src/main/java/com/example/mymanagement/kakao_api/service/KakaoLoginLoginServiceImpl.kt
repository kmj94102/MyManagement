package com.example.mymanagement.kakao_api.service

import android.content.Context
import android.util.Log
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.User
import javax.inject.Inject

class KakaoLoginLoginServiceImpl @Inject constructor(
    private val userApiClient: UserApiClient,
) : KakaoLoginService {

    override fun getUserInfo(
        context: Context,
        callback: (User?, Throwable?) -> Unit,
    ) {
        userApiClient.me { user, error ->
            val scope = mutableListOf("talk_calendar")
            UserApiClient.instance.loginWithNewScopes(context, scope) { token, error ->
                if (error != null) {
                    Log.e("+++++", "error : ${error.cause}")
                }
                Log.e("+++++", "sucess : ${token?.scopes}")
            }
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
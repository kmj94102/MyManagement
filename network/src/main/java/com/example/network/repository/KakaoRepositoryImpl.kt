package com.example.network.repository

import android.content.Context
import com.example.network.model.storeOnLoginState
import com.example.network.service.KakaoClient
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class KakaoRepositoryImpl @Inject constructor(
    private val client: KakaoClient,
    @ApplicationContext private val context: Context
) : KakaoRepository {
    override fun fetchToken(): Flow<String> = context.storeOnLoginState.data.map { it.kakaoToken }

    private suspend fun updateToken(token: String) {
        context.storeOnLoginState.updateData {
            it.toBuilder().setKakaoToken(token).build()
        }
    }

    override suspend fun kakaoLogin(context: Context) {
        val oauth = client.loginWithKakaoAccount(context)
        updateToken(oauth.accessToken)
    }

    override suspend fun kakaoLogout() {
        client.logout()
        updateToken("")
    }
}
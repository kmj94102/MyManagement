package com.example.network.repository

import android.content.Context
import com.example.mymanagement.OnLoginState
import kotlinx.coroutines.flow.Flow

interface KakaoRepository {
    fun fetchToken(): Flow<String>

    suspend fun kakaoLogin(context: Context)

    suspend fun kakaoLogout()
}
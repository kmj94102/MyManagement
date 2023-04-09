package com.example.mymanagement.kakao_api.repository

import android.content.Context
import com.example.mymanagement.OnLoginState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

//class KakaoRepositoryImpl @Inject constructor(
//    private val service: KakaoLoginLoginService,
//    @ApplicationContext private val context: Context
//) : KakaoRepository {
//    fun loginState(): Flow<OnLoginState> = context.storeOnLoginState.data
//
//    private suspend fun updateToken(token: String) {
//        context.storeOnLoginState.updateData {
//            it.toBuilder().setKakaoToken(token).build()
//        }
//    }
//
//}
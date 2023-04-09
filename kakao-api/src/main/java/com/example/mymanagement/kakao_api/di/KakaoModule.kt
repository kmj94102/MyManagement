package com.example.mymanagement.kakao_api.di

import com.kakao.sdk.user.UserApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
//object KakaoModule {
//
//    @Provides
//    @Singleton
//    fun provideUserApiClient(): UserApiClient = UserApiClient.instance
//
//    @Provides
//    @Singleton
//    fun provideKakaoLoginService(
//        userApiClient: UserApiClient,
//    ): KakaoLoginLoginService = KakaoLoginLoginService(userApiClient)
//
//}
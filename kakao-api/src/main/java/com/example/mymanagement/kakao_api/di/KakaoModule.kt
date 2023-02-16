package com.example.mymanagement.kakao_api.di

import com.example.mymanagement.kakao_api.service.KakaoLoginClient
import com.example.mymanagement.kakao_api.service.KakaoLoginService
import com.kakao.sdk.user.UserApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KakaoModule {

    @Provides
    @Singleton
    fun provideUserApiClient(): UserApiClient = UserApiClient.instance


    @Provides
    @Singleton
    fun provideUserClient(kakaoLoginService: KakaoLoginService): KakaoLoginClient =
        KakaoLoginClient(kakaoLoginService)


}
package com.example.kakao_api.di

import com.example.kakao_api.service.KakaoService
import com.example.kakao_api.service.KakaoServiceImpl
import com.kakao.sdk.user.UserApiClient
import dagger.Binds
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

}
package com.example.kakao_api.di

import com.example.kakao_api.service.KakaoService
import com.example.kakao_api.service.KakaoServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface KakaoServiceModule {

    @Binds
    fun provideKakaoService(
        kakaoServiceImpl: KakaoServiceImpl
    ): KakaoService

}
package com.example.mymanagement.kakao_api.di

import com.example.mymanagement.kakao_api.service.KakaoLoginService
import com.example.mymanagement.kakao_api.service.KakaoLoginLoginServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface KakaoServiceModule {

    @Binds
    fun provideKakaoLoginService(
        kakaoLoginServiceImpl: KakaoLoginLoginServiceImpl
    ): KakaoLoginService

}
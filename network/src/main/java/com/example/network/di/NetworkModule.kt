package com.example.network.di

import com.example.network.BuildConfig
import com.example.network.service.*
import com.kakao.sdk.user.UserApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideGsonConvertFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    @Named("Kakao")
    fun provideRetrofitKakao(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit =
        Retrofit
            .Builder()
            .baseUrl("https://kapi.kakao.com")
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()

    @Provides
    @Singleton
    @Named("Bus")
    fun provideRetrofitBus(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit =
        Retrofit
            .Builder()
            .baseUrl("http://apis.data.go.kr/1613000/")
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()

    @Provides
    @Singleton
    @Named("Subway")
    fun provideRetrofitSubway(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit =
        Retrofit
            .Builder()
            .baseUrl("http://swopenapi.seoul.go.kr/api/")
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()

    @Provides
    @Singleton
    fun provideKakaoService(
        @Named("Kakao") retrofit: Retrofit
    ): KakaoService = retrofit.create(KakaoService::class.java)

    @Provides
    @Singleton
    fun provideBusService(
        @Named("Bus") retrofit: Retrofit
    ): BusService = retrofit.create(BusService::class.java)

    @Provides
    @Singleton
    fun provideSubwayService(
        @Named("Subway") retrofit: Retrofit
    ): SubwayService = retrofit.create(SubwayService::class.java)

    @Provides
    @Singleton
    fun provideKakaoClient(
        kakaoService: KakaoService,
        userApiClient: UserApiClient
    ): KakaoClient =
        KakaoClient(kakaoService, userApiClient)

    @Provides
    @Singleton
    fun provideBusClient(busService: BusService): BusClient =
        BusClient(busService)

    @Provides
    @Singleton
    fun provideSubwayClient(subwayService: SubwayService): SubwayClient =
        SubwayClient(subwayService)

    @Provides
    @Singleton
    fun provideUserApiClient(): UserApiClient = UserApiClient.instance

}
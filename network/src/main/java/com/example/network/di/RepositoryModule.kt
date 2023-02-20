package com.example.network.di

import com.example.network.repository.MapRepository
import com.example.network.repository.MapRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindMapRepository(
        mapRepositoryImpl: MapRepositoryImpl
    ): MapRepository

}
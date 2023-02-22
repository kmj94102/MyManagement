package com.example.network.di

import com.example.network.repository.BusRepository
import com.example.network.repository.BusRepositoryImpl
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

    @Binds
    fun bindBusRepository(
        busRepositoryImpl: BusRepositoryImpl
    ): BusRepository

}
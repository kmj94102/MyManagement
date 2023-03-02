package com.example.network.di

import com.example.network.repository.*
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

    @Binds
    fun bindSubwayRepository(
        subwayRepositoryImpl: SubwayRepositoryImpl
    ): SubwayRepository

    @Binds
    fun bindTransportationRepository(
        transportationRepositoryImpl: TransportationRepositoryImpl
    ): TransportationRepository

}
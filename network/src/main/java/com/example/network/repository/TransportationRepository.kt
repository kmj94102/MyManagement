package com.example.network.repository

import com.example.mymanagement.database.entity.Favorite
import kotlinx.coroutines.flow.Flow

interface TransportationRepository {

    fun fetchFavoriteList(): Flow<List<Favorite>>

    fun fetchFavoriteById(id: String): Flow<Boolean>

    suspend fun toggleSubwayStationStatus(
        id: String,
        name: String
    )

    suspend fun toggleSubwayDestinationStatus(
        id: String,
        name: String
    )

}
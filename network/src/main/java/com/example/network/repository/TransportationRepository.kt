package com.example.network.repository

import com.example.mymanagement.database.entity.Favorite
import kotlinx.coroutines.flow.Flow

interface TransportationRepository {

    fun fetchFavoriteList(): Flow<List<Favorite>>

}
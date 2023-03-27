package com.example.network.repository

import android.util.Log
import com.example.mymanagement.database.FavoriteDao
import com.example.mymanagement.database.entity.Favorite
import com.example.mymanagement.database.entity.FavoriteEntity
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class TransportationRepositoryImpl @Inject constructor(
    private val dao: FavoriteDao
) : TransportationRepository {

    override fun fetchFavoriteList(): Flow<List<Favorite>> =
        dao.fetchFavoriteList().map { it.map { entity -> entity.toFavorite() } }

    override fun fetchFavoriteById(id: String): Flow<Boolean> =
        dao.fetchFavoriteById(id)

    override suspend fun toggleSubwayStationStatus(
        id: String,
        name: String
    ) {
        dao.favoriteInsertOrDelete(
            type = FavoriteEntity.TypeSubway,
            id = id,
            name = name
        )
    }

    override suspend fun toggleSubwayDestinationStatus(
        id: String,
        name: String
    ) {
        dao.favoriteInsertOrDelete(
            type = FavoriteEntity.TypeSubwayDestination,
            id = id,
            name = name
        )
    }

}
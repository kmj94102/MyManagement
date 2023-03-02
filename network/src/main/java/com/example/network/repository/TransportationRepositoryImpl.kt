package com.example.network.repository

import com.example.mymanagement.database.FavoriteDao
import com.example.mymanagement.database.entity.Favorite
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TransportationRepositoryImpl @Inject constructor(
    private val dao: FavoriteDao
): TransportationRepository {

    override fun fetchFavoriteList(): Flow<List<Favorite>> =
        dao.fetchFavoriteList().map { it.map { entity -> entity.mapper() } }



}
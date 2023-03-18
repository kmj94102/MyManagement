package com.example.network.repository

import com.example.mymanagement.database.entity.Favorite
import kotlinx.coroutines.flow.Flow

interface TransportationRepository {

    /** 즐겨찾기 리스트 **/
    fun fetchFavoriteList(): Flow<List<Favorite>>

    /** id로 즐겨찾기 조회 **/
    fun fetchFavoriteById(id: String): Flow<Boolean>

    /** 지하철 즐겨찾기 상태 업데이트 **/
    suspend fun toggleSubwayStationStatus(
        id: String,
        name: String
    )

    /** 지하철 목적지 설정 즐겨찾기 상태 업데이트 **/
    suspend fun toggleSubwayDestinationStatus(
        id: String,
        name: String
    )

}
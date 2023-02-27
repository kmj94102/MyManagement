package com.example.network.repository

import com.example.mymanagement.database.entity.StationItem
import com.example.network.model.SubwayArrivalInfo
import kotlinx.coroutines.flow.Flow

interface SubwayRepository {

    /** 지하철 역 정보 조회 **/
    suspend fun fetchStationItems(
        stationName: String,
        allOrFavorite: List<Int>,
    ): Flow<List<StationItem>>

    /** 지하철 노선 종류 조회 **/
    suspend fun fetchStationLineNumbers(): Map<String, Boolean>

    /** 즐겨찾기 업데이트 **/
    suspend fun updateFavorite(item: StationItem): Any

    /** 지하철역 실시간 도착 정보 **/
    suspend fun fetchRealtimeStationArrivals(keyword: String): Flow<Map<String, List<SubwayArrivalInfo>>>

}
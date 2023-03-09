package com.example.network.repository

import com.example.mymanagement.database.entity.StationItem
import com.example.network.model.SubwayArrival
import com.example.network.model.SubwayRouteInfo
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
    fun fetchRealtimeStationArrivals(keyword: String): Flow<List<SubwayArrival>>

    /** 지하철 경로 조회 **/
    fun fetchSubwayRoute(
        searchTime: String,
        startStationCode: String,
        endStationCode: String,
        week: String,
    ): Flow<SubwayRouteInfo>

}
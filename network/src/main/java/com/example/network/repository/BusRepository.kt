package com.example.network.repository

import com.example.network.model.BusEstimatedArrivalInfo
import com.example.network.model.BusStopRouteItem
import kotlinx.coroutines.flow.Flow

interface BusRepository {

    /**
     * 정류소의 버스 별 도착 예정 정보 목록 조회
     * @param cityCode 도시 코드
     * @param nodeId 정류소 ID
     * **/
    fun fetchEstimatedArrivalInfoList(
        cityCode: Int,
        nodeId: String,
    ): Flow<List<BusEstimatedArrivalInfo>>

    /** 정류소 즐겨찾기 여부 조회 **/
    fun isFavoriteStation(nodeId: String): Flow<Boolean>

    /** 정류소 즐겨찾기 등록 **/
    suspend fun toggleBusStopFavoriteStatus(name: String, nodeId: String)

    /** 버스 즐겨찾기 등록 **/
    suspend fun toggleBusFavoriteStatus(info: BusEstimatedArrivalInfo)

    /** 노선별 경유 정류소 목록 조회 **/
    fun fetchBusStopRouteList(cityCode: Int, routeId: String): Flow<List<BusStopRouteItem>>

}
package com.example.network.service

import javax.inject.Inject

class BusClient @Inject constructor(
    private val service: BusService
) {

    /** 버스 정류소 조회 **/
    suspend fun fetchBusStopList(
        latitude: Double,
        longitude: Double
    ) = service.fetchBusStopList(
        latitude = latitude,
        longitude = longitude
    ).response.body.items.item

    /**
     * 정류소 별 경유 노선 목록 조회
     * @param cityCode 도시 코드
     * @param nodeId 정류소 ID
     * **/
    suspend fun fetchTransitRouteList(
        cityCode: Int,
        nodeId: String
    ) = service.fetchTransitRouteList(
        cityCode = cityCode,
        nodeId = nodeId
    )

    /**
     * 정류소 별 도착 예정 정보 목록 조회
     * @param cityCode 도시 코드
     * @param nodeId 정류소 ID
     * **/
    suspend fun fetchEstimatedArrivalInfoList(
        cityCode: Int,
        nodeId: String
    ) = service.fetchEstimatedArrivalInfoList(
        cityCode = cityCode,
        nodeId = nodeId
    ).response.body.items.item

    /**
     * 정류소 별 특정 노선 버스 도착 예정 정보 목록 조회
     * @param cityCode 도시 코드
     * @param nodeId 정류소 ID
     * @param routeId
     * **/
    suspend fun fetchRouteEstimatedArrivalInfoList(
        cityCode: Int,
        nodeId: String,
        routeId: String
    ) = service.fetchRouteEstimatedArrivalInfoList(
        cityCode = cityCode,
        nodeId = nodeId,
        routeId = routeId
    )

}
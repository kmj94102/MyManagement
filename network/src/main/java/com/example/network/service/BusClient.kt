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

    suspend fun getTransitRouteList() =
        service.getTransitRouteList(
            cityCode = 0,
            nodeId = ""
        )

    suspend fun getEstimatedArrivalInfoList() =
        service.getEstimatedArrivalInfoList(
            cityCode = 0,
            nodeId = ""
        )

    suspend fun getRouteEstimatedArrivalInfoList() =
        service.getRouteEstimatedArrivalInfoList(
            cityCode = 0,
            nodeId = "",
            routeId = ""
        )

}
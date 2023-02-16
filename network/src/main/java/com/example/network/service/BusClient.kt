package com.example.network.service

import javax.inject.Inject

class BusClient @Inject constructor(
    private val service: BusService
) {

    suspend fun getBusStopList() = try {
        service.getBusStopList(
            latitude = 0.0,
            longitude = 0.0
        )
    } catch (e: Exception) {
        e.printStackTrace()
    }

    suspend fun getTransitRouteList() = try {
        service.getTransitRouteList(
            cityCode = 0,
            nodeId = ""
        )
    } catch (e: Exception) {
        e.printStackTrace()
    }

    suspend fun getEstimatedArrivalInfoList() = try {
        service.getEstimatedArrivalInfoList(
            cityCode = 0,
            nodeId = ""
        )
    } catch (e: Exception) {
        e.printStackTrace()
    }

    suspend fun getRouteEstimatedArrivalInfoList() = try {
        service.getRouteEstimatedArrivalInfoList(
            cityCode = 0,
            nodeId = "",
            routeId = ""
        )
    } catch (e: Exception) {
        e.printStackTrace()
    }

}
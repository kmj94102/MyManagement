package com.example.network.service

import javax.inject.Inject

class SubwayClient @Inject constructor(
    private val service: SubwayService
) {

    suspend fun getRealtimeStationArrivals() = try {
        service.getRealtimeStationArrivals(
            keyword = "서울"
        )
    } catch (e: Exception) {
        e.printStackTrace()
    }

}
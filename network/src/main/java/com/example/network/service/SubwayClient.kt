package com.example.network.service

import javax.inject.Inject

class SubwayClient @Inject constructor(
    private val service: SubwayService
) {

    suspend fun fetchRealtimeStationArrivals(
        keyword: String
    ) = service.fetchRealtimeStationArrivals(
        keyword = keyword
    ).realtimeArrivalList

    suspend fun fetchSubwayStationList(
        start: Int,
        end: Int
    ) = service.fetchSubwayStationList(
        start = start,
        end = end
    ).info

}
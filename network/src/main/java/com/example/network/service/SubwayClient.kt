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

    suspend fun fetchSubwayRoute(
        pageNo: Int,
        numOfRows: Int,
        searchTime: String,
        startStationCode: String,
        endStationCode: String,
        week: String,
    ) = service.fetchSubwayRoute(
        pageNo = pageNo,
        numOfRows = numOfRows,
        searchTime = searchTime,
        startStationCode = startStationCode,
        endStationCode = endStationCode,
        week = week
    )

}
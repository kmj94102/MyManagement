package com.example.network.service

import com.example.network.BuildConfig
import retrofit2.http.Path
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
        startStationCode: String,
        endStationCode: String,
        week: String,
    ) = service.fetchSubwayRoute(
        pageNo = pageNo,
        numOfRows = numOfRows,
        startStationCode = startStationCode,
        endStationCode = endStationCode,
        week = week
    )

}
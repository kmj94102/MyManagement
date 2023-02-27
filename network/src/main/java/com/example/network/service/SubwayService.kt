package com.example.network.service

import com.example.network.BuildConfig
import com.example.network.model.StationsResults
import com.example.network.model.SubwayArrivalInfoResult
import retrofit2.http.GET
import retrofit2.http.Path

interface SubwayService {

    /** 지하철역 실시간 도착 정보 조회 **/
    @GET("subway/${BuildConfig.SUBWAY_API_KEY}/json/realtimeStationArrival/0/15/{keyword}")
    suspend fun fetchRealtimeStationArrivals(
        @Path("keyword", encoded = true) keyword: String
    ): SubwayArrivalInfoResult

    /** 지하철 역 정보 조회 **/
    @GET("http://openapi.seoul.go.kr:8088/${BuildConfig.SUBWAY_API_KEY}/json/SearchSTNBySubwayLineInfo/{start}/{end}/")
    suspend fun fetchSubwayStationList(
        @Path("start", encoded = true) start: Int,
        @Path("end", encoded = true) end: Int
    ): StationsResults

}
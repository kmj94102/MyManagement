package com.example.network.service

import com.example.network.BuildConfig
import com.example.network.model.StationsResults
import com.example.network.model.SubwayArrivalInfoResult
import com.example.network.model.SubwayRouteResult
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

    /** 지하철 이동 경로 조회 **/
    @GET("http://apis.data.go.kr/B553766/smt-path")
    suspend fun fetchSubwayRoute(
        @Path("serviceKey") serviceKey: String = BuildConfig.BUS_AUTH_KEY,
        @Path("pageNo") pageNo: Int,
        @Path("numOfRows") numOfRows: Int,
        @Path("dept_station_code") startStationCode: String,
        @Path("dest_station_code") endStationCode: String,
        @Path("week") week: String,
        @Path("search_type") searchType: String = "FASTEST"
    ): SubwayRouteResult

}
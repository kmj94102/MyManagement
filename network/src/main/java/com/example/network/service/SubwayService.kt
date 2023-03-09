package com.example.network.service

import com.example.network.BuildConfig
import com.example.network.model.SubwayArrivalInfoResult
import com.example.network.model.SubwayRouteResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SubwayService {
    // 지하철 역 정보
    // http://data.seoul.go.kr/dataList/OA-15442/S/1/datasetView.do
    // 지하철 역 시간표
    // http://data.seoul.go.kr/dataList/OA-101/A/1/datasetView.do
    // 4d5a6277486b6d6a333466666a7562
    // CLJ4nNi2WL3AW6VyVctsfa2BeUdF4P/lm75nQlThMikADXX85VAL48ivLnyx6kVGBooaY2L+NVRBuqnFZNA3Ow==

    /** 지하철역 실시간 도착 정보 조회 **/
    @GET("subway/${BuildConfig.SUBWAY_API_KEY}/json/realtimeStationArrival/0/15/{keyword}")
    suspend fun fetchRealtimeStationArrivals(
        @Path("keyword", encoded = true) keyword: String
    ): SubwayArrivalInfoResult


    /** 지하철 이동 경로 조회
     * @param week 주중(DAY)/토요일(SAT)/공휴일(HOL)
     * **/
    @GET("https://apis.data.go.kr/B553766/smt-path/path")
    suspend fun fetchSubwayRoute(
        @Query("serviceKey") serviceKey: String = BuildConfig.BUS_AUTH_KEY,
        @Query("pageNo") pageNo: Int,
        @Query("numOfRows") numOfRows: Int,
        @Query("dept_time") searchTime: String,
        @Query("dept_station_code") startStationCode: String,
        @Query("dest_station_code") endStationCode: String,
        @Query("week") week: String,
        @Query("search_type") searchType: String = "FASTEST"
    ): SubwayRouteResult

}
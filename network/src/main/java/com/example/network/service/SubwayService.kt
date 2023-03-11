package com.example.network.service

import com.example.network.BuildConfig
import com.example.network.model.SubwayArrivalInfoResult
import com.example.network.model.SubwayRouteResult
import com.example.network.model.SubwayScheduleResults
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SubwayService {
    /**
     * 지하철역 실시간 도착 정보 조회
     * @param keyword 지하철 역 이름
     * **/
    @GET("subway/${BuildConfig.SUBWAY_API_KEY}/json/realtimeStationArrival/0/15/{keyword}")
    suspend fun fetchRealtimeStationArrivals(
        @Path("keyword", encoded = true) keyword: String
    ): SubwayArrivalInfoResult


    /**
     * 지하철 이동 경로 조회
     * @param serviceKey 서비스 키
     * @param pageNo 페이지 번호
     * @param numOfRows 1회 호출 시 조회 건수
     * @param searchTime 검색 시간
     * @param startStationCode 출발 역 코드
     * @param endStationCode 종착 역 코드
     * @param week 주중(DAY)/토요일(SAT)/공휴일(HOL)
     * @param searchType 검색 방식
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

    /**
     * 지하철 시간표 조회
     * @param start 조회 시작 위치
     * @param end 조회 종료 위치
     * @param stationCode 지하철 역 코드
     * @param week 요일 : 주중 1 / 토요일 2 / 공휴일, 일요일 3
     * @param direction 방향 : 상행, 내선 1 / 하행, 외선 2
     * **/
    @GET("http://openapi.seoul.go.kr:8088/${BuildConfig.SUBWAY_API_KEY}/json/SearchSTNTimeTableByIDService/{start}/{end}/{stationCode}/{week}/{direction}/")
    suspend fun fetchSubwaySchedule(
        @Path("start") start: Int,
        @Path("end") end: Int,
        @Path("stationCode") stationCode: String,
        @Path("week") week: Int,
        @Path("direction") direction: Int
    ): SubwayScheduleResults

}
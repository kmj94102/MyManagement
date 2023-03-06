package com.example.network.service

import com.example.network.BuildConfig
import com.example.network.model.BusApiResult
import com.example.network.model.BusStop
import com.example.network.model.EstimatedArrivalInfo
import com.example.network.model.BusStopRoute
import retrofit2.http.GET
import retrofit2.http.Query

interface BusService {
    /***
     * 좌표 기반 근접 정류소 목록 조회
     * @param serviceKey 필수) 인증키
     * @param latitude 필수) WGS84 위도 좌표
     * @param longitude 필수) WGS84 경도 좌표
     * @param numOfRows 한 페이지 결과 수
     * @param pageNo 페이지 번호
     * @param type 데이터 타입(xml, json)
     * ***/
    @GET("BusSttnInfoInqireService/getCrdntPrxmtSttnList")
    suspend fun fetchBusStopList(
        @Query("serviceKey") serviceKey: String = BuildConfig.BUS_AUTH_KEY,
        @Query("gpsLati") latitude: Double,
        @Query("gpsLong") longitude: Double,
        @Query("numOfRows") numOfRows: Int = 10,
        @Query("pageNo") pageNo: Int = 1,
        @Query("_type") type: String = "json",
    ): BusApiResult<List<BusStop>>

    /**
     * 정류소 별 도착 예정 정보 목록 조회
     * @param serviceKey 필수) 인증키
     * @param cityCode 필수) 도시 코드
     * @param nodeId 필수) 정류소 ID
     * @param pageNo 페이지 번호
     * @param numOfRows 한 페이지 결과 수
     * @param type 데이터 타입(xml, json)
     * **/
    @GET("ArvlInfoInqireService/getSttnAcctoArvlPrearngeInfoList")
    suspend fun fetchEstimatedArrivalInfoList(
        @Query("serviceKey") serviceKey: String = BuildConfig.BUS_AUTH_KEY,
        @Query("cityCode") cityCode: Int,
        @Query("nodeId") nodeId: String,
        @Query("pageNo") pageNo: Int = 1,
        @Query("numOfRows") numOfRows: Int = 10,
        @Query("_type") type: String = "json"
    ): BusApiResult<List<EstimatedArrivalInfo>>

    /**
     * 정류소의 특정 노선 버스 도착 예정 정보 목록 조회
     * @param serviceKey 필수) 인증키
     * @param cityCode 필수) 도시 코드
     * @param nodeId 필수) 정류소 ID
     * @param routeId 필수) 노선 ID
     * @param pageNo 페이지 번호
     * @param numOfRows 한 페이지 결과 수
     * @param type 데이터 타입(xml, json)
     * * **/
    @GET("ArvlInfoInqireService/getSttnAcctoSpcifyRouteBusArvlPrearngeInfoList")
    suspend fun fetchRouteEstimatedArrivalInfoList(
        @Query("serviceKey") serviceKey: String = BuildConfig.BUS_AUTH_KEY,
        @Query("cityCode") cityCode: Int,
        @Query("nodeId") nodeId: String,
        @Query("routeId") routeId: String,
        @Query("pageNo") pageNo: Int = 1,
        @Query("numOfRows") numOfRows: Int = 10,
        @Query("_type") type: String = "json"
    ): BusApiResult<List<EstimatedArrivalInfo>>

    /** 노선별 경유 정류소 목록 조회
     * @param serviceKey 필수) 인증키
     * @param cityCode 필수) 도시 코드
     * @param routeId 필수) 노선 ID
     * @param pageNo 페이지 번호
     * @param numOfRows 한 페이지 결과 수
     * @param type 데이터 타입(xml, json)
     * **/
    @GET("BusRouteInfoInqireService/getRouteAcctoThrghSttnList")
    suspend fun fetchBusStopRouteList(
        @Query("serviceKey") serviceKey: String = BuildConfig.BUS_AUTH_KEY,
        @Query("pageNo") pageNo: Int = 1,
        @Query("numOfRows") numOfRows: Int = 20,
        @Query("_type") type: String = "json",
        @Query("cityCode") cityCode: Int,
        @Query("routeId") routeId: String
    ): BusApiResult<List<BusStopRoute>>

}
package com.example.network.repository

import com.example.mymanagement.database.entity.StationItem
import com.example.network.model.SubwayArrival
import com.example.network.model.SubwayRouteInfo
import com.example.network.model.SubwayScheduleInfo
import kotlinx.coroutines.flow.Flow

interface SubwayRepository {

    /**
     * 지하철 역 정보 조회
     * @param stationName 지하철역 이름
     * @param isFavoriteOnly 즐겨찾기만 표시 여부
     * **/
    suspend fun fetchStationItems(
        stationName: String,
        isFavoriteOnly: Boolean,
    ): Flow<List<StationItem>>

    /** 지하철 노선 종류 조회 **/
    suspend fun fetchStationLineNumbers(): Map<String, Boolean>

    /** 즐겨찾기 업데이트 **/
    suspend fun updateFavorite(item: StationItem): Any

    /** 지하철역 실시간 도착 정보 **/
    fun fetchRealtimeStationArrivals(keyword: String): Flow<List<SubwayArrival>>

    /**
     * 지하철 경로 조회
     * @param searchTime 조회하려는 시간
     * @param startStationCode 출발지 역 코드
     * @param endStationCode 목적지 역 코드
     * @param week 평일(DAY) / 토요일(SAT) / 공휴일(HOL)
     * **/
    fun fetchSubwayRoute(
        searchTime: String,
        startStationCode: String,
        endStationCode: String,
        week: String,
    ): Flow<SubwayRouteInfo>

    /** 지하철 시간표 조회 **/
    fun fetchSubwaySchedule(
        stationCode: String,
        week: Int,
        direction: Int
    ): Flow<List<SubwayScheduleInfo>>

}
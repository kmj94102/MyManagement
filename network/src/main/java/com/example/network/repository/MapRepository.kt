package com.example.network.repository

import com.example.network.model.BusStop
import kotlinx.coroutines.flow.Flow

interface MapRepository {

    /**
     * 버스 정류소 조회
     * @param keyword 키워드
     * @param latitude 위도
     * @param longitude 경도
     * @param cameraLocation 검색 후 지도 위치
     * **/
    fun searchBusStopList(
        keyword: String,
        latitude: Double = 0.0,
        longitude: Double = 0.0,
        cameraLocation: (Double, Double) -> Unit,
    ) : Flow<List<BusStop>>

}
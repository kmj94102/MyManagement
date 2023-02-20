package com.example.network.repository

import com.example.network.model.BusStop
import kotlinx.coroutines.flow.Flow

interface MapRepository {

    /**
     * 버스 정류소 조회
     * @param keyword 키워드
     * @param cameraLocation 검색 후 지도 위치
     * @param onComplete 완료 리스너
     * @param onError 오류 리스너
     * **/
    fun searchBusStopList(
        keyword: String,
        latitude: Double = 0.0,
        longitude: Double = 0.0,
        isKeyword: Boolean,
        cameraLocation: (Double, Double) -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ) : Flow<List<BusStop>>

}
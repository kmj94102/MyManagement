package com.example.network.repository

import com.example.network.model.BusEstimatedArrivalInfo
import kotlinx.coroutines.flow.Flow

interface BusRepository {

    /**
     * 정류소 별 도착 예정 정보 목록 조회
     * @param cityCode 도시 코드
     * @param nodeId 정류소 ID
     * **/
    fun fetchEstimatedArrivalInfoList(
        cityCode: Int,
        nodeId: String,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<BusEstimatedArrivalInfo>>

}
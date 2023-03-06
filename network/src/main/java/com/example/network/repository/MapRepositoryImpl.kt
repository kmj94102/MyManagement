package com.example.network.repository

import com.example.network.service.BusClient
import com.example.network.service.KakaoClient
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MapRepositoryImpl @Inject constructor(
    private val kakaoClient: KakaoClient,
    private val busClient: BusClient
) : MapRepository {

    override fun searchBusStopList(
        keyword: String,
        latitude: Double,
        longitude: Double,
        cameraLocation: (Double, Double) -> Unit,
    ) = flow {
        // 버스 정류소 리스트
        try {
            if (keyword.isNotEmpty()) {
                // 키워드로 검색된 장소 리스트
                val placeList = kakaoClient.fetchPlaceListByKeyword(keyword = keyword).documents

                // 리스트 중 SW8 (지하철 역)이 있을 경우 지하철 역 장소 저장
                // 해당 사항 없을 경우 첫 번째 장소 저장
                val place = placeList.find {
                    it.categoryGroupCode == "SW8"
                } ?: placeList.firstOrNull()

                place?.let {
                    // 지도 카메라 위치 지정
                    cameraLocation(it.y, it.x)
                    emit(busClient.fetchBusStopList(latitude = it.y, longitude = it.x))
                } ?: emit(emptyList())
            } else {
                cameraLocation(latitude, longitude)
                emit(busClient.fetchBusStopList(latitude = latitude, longitude = longitude))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(emptyList())
        }
    }

}
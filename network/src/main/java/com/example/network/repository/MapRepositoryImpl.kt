package com.example.network.repository

import android.util.Log
import com.example.network.model.BusStop
import com.example.network.service.BusClient
import com.example.network.service.KakaoClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import javax.inject.Inject

class MapRepositoryImpl @Inject constructor(
    private val kakaoClient: KakaoClient,
    private val busClient: BusClient
): MapRepository {

    override fun searchBusStopList(
        keyword: String,
        latitude: Double,
        longitude: Double,
        isKeyword: Boolean,
        cameraLocation: (Double, Double) -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ) = flow {
        // 버스 정류소 리스트
        val busStopList = mutableListOf<BusStop>()

        if (isKeyword) {
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
                busStopList.addAll(
                    busClient.fetchBusStopList(
                        latitude = it.y,
                        longitude = it.x
                    )
                )
            }
        } else {
            cameraLocation(latitude, longitude)
            busStopList.addAll(
                busClient.fetchBusStopList(
                    latitude = latitude,
                    longitude = longitude
                )
            )
        }
        emit(busStopList)
    }.onCompletion { onComplete() }.catch {
        it.printStackTrace()
        onError("오류 발생")
    }

}
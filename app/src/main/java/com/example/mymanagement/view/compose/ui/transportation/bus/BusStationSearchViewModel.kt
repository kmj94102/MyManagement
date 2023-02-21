package com.example.mymanagement.view.compose.ui.transportation.bus

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mymanagement.util.getSeoulLatLng
import com.example.network.repository.MapRepository
import com.naver.maps.geometry.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class BusStationSearchViewModel @Inject constructor(
    private val kakaoRepository: MapRepository
): ViewModel() {

    private val _cameraLatLng = mutableStateOf(getSeoulLatLng())
    val cameraLatLng: State<LatLng> = _cameraLatLng

    private var isKeyword = true

    private val _place: MutableSharedFlow<String> = MutableSharedFlow(replay = 1)
    var place = _place.flatMapLatest {
        kakaoRepository.searchBusStopList(
            keyword = it,
            latitude = _cameraLatLng.value.latitude,
            longitude = _cameraLatLng.value.longitude,
            isKeyword = isKeyword,
            cameraLocation = { lat, lng ->
                _cameraLatLng.value = LatLng(lat, lng)
            },
            onComplete = {

            },
            onError = {

            }
        )
    }

    // 키워드로 버스 정류장 검색
    fun searchBusStopByKeyword(keyword: String) {
        isKeyword = true
        _place.tryEmit(keyword)
    }

    // 위치로 버스 정류장 검색
    fun searchBusStopByLocation(latLng: LatLng) {
        _cameraLatLng.value = latLng
        isKeyword = false
        _place.tryEmit("")
    }
}
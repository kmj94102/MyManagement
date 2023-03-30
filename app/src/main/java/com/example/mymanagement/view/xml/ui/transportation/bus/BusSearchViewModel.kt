package com.example.mymanagement.view.xml.ui.transportation.bus

import androidx.lifecycle.ViewModel
import com.example.network.repository.MapRepository
import com.naver.maps.geometry.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class BusSearchViewModel @Inject constructor(
    private val repository: MapRepository
): ViewModel() {

    private val _cameraLatLng = MutableStateFlow(LatLng(37.5547125,26.9707878))
    private val _latLng = MutableStateFlow(LatLng(37.5547125,26.9707878))

    private val sharedFlow: MutableSharedFlow<String> = MutableSharedFlow(replay = 1)
    val busStopList = sharedFlow.flatMapLatest {
        repository.searchBusStopList(
            keyword = it,
            latitude = _cameraLatLng.value.latitude,
            longitude = _cameraLatLng.value.longitude,
            cameraLocation = { lat, lng ->
                _cameraLatLng.value = LatLng(lat, lng)
            },
        )
    }

    fun cameraPositionChange(latLng: LatLng) {
        _cameraLatLng.value = latLng
    }

    fun searchBusStopListByKeyword(keyword: String) {
        sharedFlow.tryEmit(keyword)
    }

    fun searchBusStopListByLatLng(
        latLng: LatLng?= null
    ) {
        latLng?.let {
            _cameraLatLng.value = latLng
        }
        sharedFlow.tryEmit("")
    }
}
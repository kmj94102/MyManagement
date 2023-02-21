package com.example.mymanagement.view.compose.ui.custom

import android.view.Gravity
import androidx.compose.runtime.*
import com.example.network.model.BusStop
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.compose.*
import kotlinx.coroutines.launch

/**
 * 네이버 지도
 * @param cameraLatLng 카메라 위치
 * @param list 버스 정류장 리스트
 * **/
@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun CommonNaverMap(
    cameraLatLng: LatLng,
    list: List<BusStop>,
    cameraPositionState: CameraPositionState
){
    val mapProperties by remember {
        mutableStateOf(
            MapProperties(
                maxZoom = 20.0,
                minZoom = 5.0,
                isIndoorEnabled = true
            )
        )
    }
    val mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                isZoomControlEnabled = true,
                logoGravity = Gravity.TOP or Gravity.END,
            )
        )
    }

    val scope = rememberCoroutineScope()

    NaverMap(
        locationSource = rememberFusedLocationSource(),
        properties = mapProperties,
        uiSettings = mapUiSettings,
        cameraPositionState = cameraPositionState,
    ) {
        list.forEach {
            Marker(
                state = MarkerState(
                    position = LatLng(it.latitude, it.longitude)
                ),
                captionText = it.nodeNm,
                onClick = {

                    true
                }
            )
        }

        LaunchedEffect(cameraLatLng) {
            scope.launch {
                cameraPositionState.animate(
                    update = CameraUpdate.scrollTo(cameraLatLng),
                    animation = CameraAnimation.Fly,
                )
            }
        }
    }
}
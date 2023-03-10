package com.example.mymanagement.view.compose.ui.transportation.bus

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mymanagement.R
import com.example.mymanagement.util.getSeoulLatLng
import com.example.mymanagement.util.shadow
import com.example.mymanagement.view.compose.ui.custom.*
import com.example.mymanagement.view.compose.ui.navigation.NavScreen
import com.example.mymanagement.view.compose.ui.theme.White
import com.example.network.model.BusStop
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.rememberCameraPositionState

/** 버스 정류장 검색 화면 **/
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun BusStationSearchScreen(
    goToArrivalInfo: (Int, String, String) -> Unit,
    onBackClick: () -> Unit = {},
    viewModel: BusStationSearchViewModel = hiltViewModel()
) {
    val permission = rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)
    val context = LocalContext.current
    // 버스 정류소 리스트
    val busStopList: List<BusStop> by viewModel.place.collectAsState(initial = emptyList())
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition(
            getSeoulLatLng(),
            14.0
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // 네이버 지도
        CommonNaverMap(
            list = busStopList,
            cameraLatLng = viewModel.cameraLatLng.value,
            cameraPositionState = cameraPositionState
        ) { cityCode, nodeId, name ->
            goToArrivalInfo(cityCode, nodeId, name)
        }

        // 해더
        CommonHeader(
            title = NavScreen.BusStationSearch.item.title,
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            onBackClick()
        }

        // 하단 검색 창
        Card(
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
            backgroundColor = White,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .shadow(
                    borderRadius = 20.dp,
                    blurRadius = 3.dp
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                SearchTextField(
                    hint = "인근 지역 검색",
                    onSearch = {
                        viewModel.searchBusStopByKeyword(it)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 27.dp, start = 20.dp, end = 20.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 23.dp, horizontal = 20.dp)
                ) {
                    CommonOutLineButton(
                        text = "내 위치에서 검색",
                        icon = {
                            Icon(
                                painter = painterResource(
                                    id = R.drawable.ic_my_location
                                ),
                                contentDescription = "내 위치"
                            )
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        getDeviceLocation(context = context, permissionState = permission) {
                            viewModel.searchBusStopByLocation(it)
                        }
                    } // CommonButton

                    Spacer(modifier = Modifier.width(16.dp))

                    CommonButton(
                        text = "현 지도에서 검색",
                        icon = {
                            Icon(
                                painter = painterResource(
                                    id = R.drawable.ic_refresh
                                ),
                                contentDescription = "다시 시도",
                                tint = White
                            )
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        viewModel.searchBusStopByLocation(
                            latLng = cameraPositionState.position.target
                        )
                    } // CommonButton
                } // Row
            } // Column
        }
    } // Box

}

/** 내 위치 조회 **/
@OptIn(ExperimentalPermissionsApi::class)
private fun getDeviceLocation(
    context: Context,
    permissionState: PermissionState,
    resultListener: (LatLng) -> Unit
) {
    val fusedLocation = LocationServices.getFusedLocationProviderClient(context)
    try {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            val locationResult = fusedLocation.lastLocation
            locationResult.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val lastKnowLocation = task.result
                    resultListener(
                        LatLng(lastKnowLocation.latitude, lastKnowLocation.longitude)
                    )
                }
            }
        } else {
            // 권한 요청
            permissionState.launchPermissionRequest()
        }

    } catch (e: Exception) {
        e.printStackTrace()
    }
}

@Composable
@Preview
fun PreviewBusStationSearchScreen() {
    BusStationSearchScreen({ _, _, _ -> })
}
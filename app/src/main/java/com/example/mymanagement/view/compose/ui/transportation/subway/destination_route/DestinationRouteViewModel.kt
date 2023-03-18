package com.example.mymanagement.view.compose.ui.transportation.subway.destination_route

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymanagement.database.entity.FavoriteEntity
import com.example.mymanagement.util.getToday
import com.example.mymanagement.view.compose.ui.navigation.NavScreen
import com.example.network.model.formatTime
import com.example.network.repository.SubwayRepository
import com.example.network.repository.TransportationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class DestinationRouteViewModel @Inject constructor(
    private val repository: SubwayRepository,
    private val transportationRepository: TransportationRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    // 출발역 코드
    private var startCode: String = ""
    // 도착역 코드
    private var endCode: String = ""
    // 선택한 조회 시간
    private val _time = mutableStateOf(getToday())
    val time: String = formatTime(_time.value)
    // 선택한 주간 정보 : 평일 / 툐요일 / 공휴일
    private val _week = mutableStateOf(WeekDay)
    val week: State<String> = _week
    // 즐겨찾기 등록 여부
    private val _isFavorite = mutableStateOf(false)
    val isFavorite: State<Boolean> = _isFavorite
    // 이동 경로
    private val _routeInfoSharedFlow = MutableSharedFlow<String>(replay = 1)
    val routeInfo = _routeInfoSharedFlow.flatMapLatest {
        repository.fetchSubwayRoute(
            searchTime = _time.value,
            startStationCode = startCode,
            endStationCode = endCode,
            week = _week.value
        )
    }

    init {
        savedStateHandle.get<String>(NavScreen.SubwayDestinationRoute.StartStationCode)?.let {
            startCode = it
        }
        savedStateHandle.get<String>(NavScreen.SubwayDestinationRoute.EndStationCode)?.let {
            endCode = it
        }
        fetchRouteInfo()
        fetchFavoriteStatus()
    }

    /** 목적지 경로 조회 **/
    private fun fetchRouteInfo() {
        _routeInfoSharedFlow.tryEmit("Unit")
    }

    /** 즐겨찾기 정보 조회 **/
    private fun fetchFavoriteStatus() = viewModelScope.launch {
        transportationRepository
            .fetchFavoriteById("${startCode}${FavoriteEntity.Separator}${endCode}")
            .collect {
                _isFavorite.value = it
            }
    }

    /** 즐겨찾기 상태 업데이트 **/
    fun toggleFavoriteStatus(
        startName:String,
        endName: String
    ) = viewModelScope.launch {
        transportationRepository.toggleSubwayDestinationStatus(
            id = "${startCode}${FavoriteEntity.Separator}${endCode}",
            name = "${startName}${FavoriteEntity.Separator}${endName}"
        )
    }

    /** 주간 정보 상태 업데이트 **/
    fun updateWeek(week: String) {
        if (week == _week.value) return
        _week.value = week
        fetchRouteInfo()
    }

    companion object {
        const val WeekDay = "DAY"
        const val WeekSaturday = "SAT"
        const val WeekHolidays = "HOL"
    }

}
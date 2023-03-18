package com.example.mymanagement.view.compose.ui.transportation.subway.schedule

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymanagement.view.compose.ui.navigation.NavScreen
import com.example.network.model.SubwayScheduleInfo
import com.example.network.repository.SubwayRepository
import com.example.network.repository.TransportationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubwayScheduleViewModel @Inject constructor(
    private val repository: SubwayRepository,
    private val transportationRepository: TransportationRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    // 역 코드
    private val stationCode: String = savedStateHandle[NavScreen.SubwaySchedule.StationCode] ?: ""
    // 역 이름
    private val stationName: String = savedStateHandle[NavScreen.SubwaySchedule.CurrentStationName] ?: ""
    // 선택한 주간 정보
    private val _week = mutableStateOf(WeekDay)
    val week: State<Int> = _week
    // 즐겨찾기 여부
    val isFavorite = transportationRepository.fetchFavoriteById(stationCode)
    // 원본 리스트
    private val originList = mutableStateListOf<SubwayScheduleInfo>()
    // 지하철 시간표 리스트
    val scheduleList: List<Pair<String, List<SubwayScheduleInfo>>>
        get() {
            return originList
                .sortedWith(compareBy({ info -> info.hour }, { info -> info.minute }))
                .groupBy { it.hour }
                .toList()
        }

    init {
        fetchSubwaySchedules()
    }

    /** 시간표 조회 **/
    private fun fetchSubwaySchedules() = viewModelScope.launch {
        originList.clear()
        fetchSubwaySchedule(1)
        fetchSubwaySchedule(2)
    }

    private suspend fun fetchSubwaySchedule(direction: Int) = repository.fetchSubwaySchedule(
        stationCode = stationCode,
        week = _week.value,
        direction = direction
    ).collect {
        originList.addAll(it)
    }

    fun toggleSubwayStationStatus() = viewModelScope.launch {
        transportationRepository.toggleSubwayStationStatus(
            id = stationCode,
            name = stationName
        )
    }

    fun changeWeek(week: Int) {
        _week.value = week
        fetchSubwaySchedules()
    }

    companion object {
        const val WeekDay = 1
        const val WeekSaturday = 2
        const val WeekHolidays = 3
    }

}
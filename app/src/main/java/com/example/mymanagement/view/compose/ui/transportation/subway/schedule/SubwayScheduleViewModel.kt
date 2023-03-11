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

    private val stationCode: String = savedStateHandle[NavScreen.SubwaySchedule.StationCode] ?: ""
    private val stationName: String = savedStateHandle[NavScreen.SubwaySchedule.CurrentStationName] ?: ""

    private val _week = mutableStateOf(WeekDay)
    val week: State<Int> = _week

    val isFavorite = transportationRepository.fetchFavoriteById(stationCode)

    private val originList = mutableStateListOf<SubwayScheduleInfo>()
    val list: List<Pair<String, List<SubwayScheduleInfo>>>
        get() {
            return originList
                .sortedWith(compareBy({ info -> info.hour }, { info -> info.minute }))
                .groupBy { it.hour }
                .toList()
                .sortedBy { it.first }
        }

    init {
        fetchSchedule()
    }

    private fun fetchSchedule() = viewModelScope.launch {
        originList.clear()
        repository.fetchSubwaySchedule(
            stationCode = stationCode,
            week = _week.value,
            direction = 1
        ).collect {
            originList.addAll(it)
            originList.sortedWith(compareBy({ info -> info.hour }, { info -> info.minute }))
        }

        repository.fetchSubwaySchedule(
            stationCode = stationCode,
            week = _week.value,
            direction = 2
        ).collect {
            originList.addAll(it)
        }
    }

    fun toggleSubwayStationStatus() = viewModelScope.launch {
        transportationRepository.toggleSubwayStationStatus(
            id = stationCode,
            name = stationName
        )
    }

    fun changeWeek(week: Int) {
        _week.value = week
        fetchSchedule()
    }

    companion object {
        const val WeekDay = 1
        const val WeekSaturday = 2
        const val WeekHolidays = 3
    }

}
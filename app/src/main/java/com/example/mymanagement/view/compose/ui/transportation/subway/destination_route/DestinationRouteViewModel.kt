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

    private var startCode: String = ""
    private var endCode: String = ""

    private val _time = mutableStateOf(getToday())
    val time: String = formatTime(_time.value)

    private val _week = mutableStateOf(WeekDay)
    val week: State<String> = _week

    private val _isFavorite = mutableStateOf(false)
    val isFavorite: State<Boolean> = _isFavorite

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

    private fun fetchRouteInfo() {
        _routeInfoSharedFlow.tryEmit("Unit")
    }

    private fun fetchFavoriteStatus() = viewModelScope.launch {
        transportationRepository
            .fetchFavoriteById("${startCode}${FavoriteEntity.Separator}${endCode}")
            .collect {
                _isFavorite.value = it
            }
    }

    fun toggleFavoriteStatus(
        startName:String,
        endName: String
    ) = viewModelScope.launch {
        transportationRepository.toggleSubwayDestinationStatus(
            id = "${startCode}${FavoriteEntity.Separator}${endCode}",
            name = "${startName}${FavoriteEntity.Separator}${endName}"
        )
    }

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
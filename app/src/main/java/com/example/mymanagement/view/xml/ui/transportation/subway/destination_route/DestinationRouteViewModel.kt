package com.example.mymanagement.view.xml.ui.transportation.subway.destination_route

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymanagement.util.getToday
import com.example.mymanagement.view.xml.model.SubwayRouteRecyclerItem
import com.example.network.model.formatTime
import com.example.network.repository.SubwayRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class DestinationRouteViewModel @Inject constructor(
    private val repository: SubwayRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val startStationCode: String =
        savedStateHandle[DestinationRouteFragment.StartStationCode] ?: ""
    private val endStationCode: String =
        savedStateHandle[DestinationRouteFragment.EndStationCode] ?: ""

    private val _week = MutableStateFlow(Day)
    val week: StateFlow<String> = _week

    private val _searchTime = MutableStateFlow(getToday())
    val searchTime: StateFlow<String> = MutableStateFlow(formatTime(_searchTime.value))

    private val _info = MutableStateFlow(SubwayRouteRecyclerItem.ArrivalInfo())
    val info: StateFlow<SubwayRouteRecyclerItem.ArrivalInfo> = _info

    private val _list = MutableStateFlow<List<SubwayRouteRecyclerItem>>(emptyList())
    val list: StateFlow<List<SubwayRouteRecyclerItem>> = _list

    init {
        fetchSubwayRoute()
    }

    private fun fetchSubwayRoute() {
        repository
            .fetchSubwayRoute(
                searchTime = _searchTime.value,
                startStationCode = startStationCode,
                endStationCode = endStationCode,
                week = _week.value
            )
            .onEach {
                _info.value = SubwayRouteRecyclerItem.convertArrivalInfo(it)
                _list.value = SubwayRouteRecyclerItem.convertRecyclerItemList(it.list)
            }
            .launchIn(viewModelScope)
    }

    fun updateSearchTime(time: String) {
        _searchTime.value = time
    }

    fun updateWeek(week: String) {
        if (_week.value == week) return
        _week.value = week
        fetchSubwayRoute()
    }

    companion object {
        const val Day = "DAY"
        const val Saturday = "SAT"
        const val Holiday = "HOL"
    }

}
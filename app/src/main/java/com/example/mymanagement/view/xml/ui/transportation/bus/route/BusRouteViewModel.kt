package com.example.mymanagement.view.xml.ui.transportation.bus.route

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.model.BusEstimatedArrivalInfo
import com.example.network.model.BusStopRouteItem
import com.example.network.repository.BusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusRouteViewModel @Inject constructor(
    private val repository: BusRepository
) : ViewModel() {

    private var cityCode = 0
    private var routeId = ""
    private var busNumber = ""

    private val sharedFlow = MutableSharedFlow<Unit>(replay = 1)

    val busRouteList = sharedFlow.flatMapLatest {
        repository.fetchBusStopRouteList(
            cityCode = cityCode,
            routeId = routeId
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500L),
        initialValue = emptyList()
    )

    private fun fetchBusRouteList() {
        sharedFlow.tryEmit(Unit)
    }

    fun setInfo(cityCode: Int, routeId: String, busNumber: String) {
        this.cityCode = cityCode
        this.routeId = routeId
        this.busNumber = busNumber
        fetchBusRouteList()
    }

    fun toggleBusStopFavoriteStatus(name: String, nodeId: String) = viewModelScope.launch {
        repository.toggleBusFavoriteStatus(
            BusEstimatedArrivalInfo(
                busNumber = busNumber,
                routeId = routeId,
                nodeId = nodeId,
                nodeName = name,
                arrInfo = emptyList(),
                isFavorite = false
            )
        )
        fetchBusRouteList()
    }

}
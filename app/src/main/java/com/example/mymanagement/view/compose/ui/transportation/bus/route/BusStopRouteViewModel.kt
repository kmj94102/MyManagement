package com.example.mymanagement.view.compose.ui.transportation.bus.route

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymanagement.view.compose.ui.navigation.NavScreen
import com.example.network.model.BusEstimatedArrivalInfo
import com.example.network.model.BusStopRoute
import com.example.network.model.BusStopRouteItem
import com.example.network.repository.BusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusStopRouteViewModel @Inject constructor(
    private val repository: BusRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var cityCode = 0
    private var routeId = ""

    private val _isProgress = mutableStateOf(false)
    val isProgress: State<Boolean> = _isProgress

    private val _list = mutableStateListOf<BusStopRouteItem>()
    val list: List<BusStopRouteItem> = _list

    init {
        savedStateHandle.get<Int>(NavScreen.BusStopRoute.CityCode)?.let {
            cityCode = it
        }
        savedStateHandle.get<String>(NavScreen.BusStopRoute.RouteId)?.let {
            routeId = it
        }

        fetchBusStopRouteList()
    }

    private fun fetchBusStopRouteList() {
        repository.fetchBusStopRouteList(
            cityCode = cityCode,
            routeId = routeId
        ).onStart { _isProgress.value = true }
            .onEach {
                _list.clear()
                _list.addAll(it)
            }
            .onCompletion { _isProgress.value = false }
            .catch { it.printStackTrace() }
            .launchIn(viewModelScope)
    }

    fun toggleBusFavoriteStatus(
        busNumber: String,
        item: BusStopRouteItem
    ) = viewModelScope.launch {
        repository.toggleBusFavoriteStatus(
            info = BusEstimatedArrivalInfo(
                busNumber = busNumber,
                routeId = routeId,
                nodeId = item.nodeId,
                nodeName = item.nodeName,
                arrInfo = emptyList(),
                isFavorite = false
            )
        )
        fetchBusStopRouteList()
    }

}
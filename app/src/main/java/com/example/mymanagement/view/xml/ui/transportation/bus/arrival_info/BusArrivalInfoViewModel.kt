package com.example.mymanagement.view.xml.ui.transportation.bus.arrival_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.model.BusEstimatedArrivalInfo
import com.example.network.repository.BusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusArrivalInfoViewModel @Inject constructor(
    private val repository: BusRepository
) : ViewModel() {

    private var cityCode = 0
    private var nodeId = ""

    private val _isFavoriteBusStop = MutableStateFlow(false)
    val isFavoriteBusStop: StateFlow<Boolean> = _isFavoriteBusStop

    private val sharedFlow = MutableSharedFlow<Unit>(replay = 1)
    val arrivalInfoList = sharedFlow.flatMapLatest {
        repository.fetchEstimatedArrivalInfoList(
            cityCode = cityCode,
            nodeId = nodeId
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500L),
        initialValue = emptyList()
    )

    init {
        fetchEstimatedArrivalInfoList()
        isFavoriteBusStop()
    }

    private fun isFavoriteBusStop() {
        repository
            .isFavoriteBusStop(nodeId)
            .onEach {
                _isFavoriteBusStop.value = it
            }
            .launchIn(viewModelScope)
    }

    fun setCityCodeAndNodeId(
        cityCode: Int,
        nodeId: String
    ) {
        this.cityCode = cityCode
        this.nodeId = nodeId
    }

    fun fetchEstimatedArrivalInfoList() {
        sharedFlow.tryEmit(Unit)
    }

    fun toggleBusFavoriteStatus(info: BusEstimatedArrivalInfo) = viewModelScope.launch {
        repository.toggleBusFavoriteStatus(info)
        fetchEstimatedArrivalInfoList()
    }

    fun toggleBusStopFavoriteStatus(
        name: String,
    ) = viewModelScope.launch {
        repository.toggleBusStopFavoriteStatus(
            name = name,
            nodeId = nodeId
        )
    }

}
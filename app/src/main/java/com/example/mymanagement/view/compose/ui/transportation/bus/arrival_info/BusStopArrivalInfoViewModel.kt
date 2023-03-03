package com.example.mymanagement.view.compose.ui.transportation.bus.arrival_info

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymanagement.view.compose.ui.navigation.NavScreen
import com.example.network.model.BusEstimatedArrivalInfo
import com.example.network.repository.BusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class BusStopArrivalInfoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: BusRepository
) : ViewModel() {

    private var _cityCode = mutableStateOf(0)
    val cityCode: State<Int> = _cityCode
    private lateinit var nodeId: String

    private val _isProgress = mutableStateOf(false)
    val isProgress: State<Boolean> = _isProgress

    private val _isFavoriteStation = mutableStateOf(false)
    val isFavoriteStation: State<Boolean> = _isFavoriteStation

    private val sharedFlow = MutableSharedFlow<Unit>(replay = 1)
    val arrivalInfoList = sharedFlow.flatMapLatest {
        repository.fetchEstimatedArrivalInfoList(
            cityCode = _cityCode.value,
            nodeId = nodeId,
            onComplete = { _isProgress.value = false },
            onError = {}
        )
    }.onStart { _isProgress.value = true }

    init {
        savedStateHandle.get<Int>(NavScreen.BusStopArrivalInfo.CityCode)?.let {
            _cityCode.value = it
        }
        savedStateHandle.get<String>(NavScreen.BusStopArrivalInfo.NodeId)?.let {
            nodeId = it
        }
        fetchEstimatedArrivalInfoList()
        fetchStationFavoriteStatus()
    }

    fun fetchEstimatedArrivalInfoList() = sharedFlow.tryEmit(Unit)

    private fun fetchStationFavoriteStatus() {
        repository.isFavoriteStation(nodeId)
            .onEach { _isFavoriteStation.value = it }
            .catch { it.printStackTrace() }
            .launchIn(viewModelScope)
    }

    fun toggleBusStopFavoriteStatus(
        name: String
    ) = viewModelScope.launch {
        repository.toggleBusStopFavoriteStatus(
            name = name,
            nodeId = nodeId
        )
    }

    fun toggleBusFavoriteStatus(
        info: BusEstimatedArrivalInfo
    ) = viewModelScope.launch {
        repository.toggleBusFavoriteStatus(
            info = info
        )
        fetchEstimatedArrivalInfoList()
    }

}
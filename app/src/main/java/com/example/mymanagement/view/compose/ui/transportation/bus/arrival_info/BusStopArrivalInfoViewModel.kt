package com.example.mymanagement.view.compose.ui.transportation.bus.arrival_info

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.mymanagement.view.compose.ui.navigation.NavScreen
import com.example.network.repository.BusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class BusStopArrivalInfoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: BusRepository
) : ViewModel() {

    private var cityCode: Int = 0
    private lateinit var nodeId: String

    private val sharedFlow = MutableSharedFlow<Unit>(replay = 1)
    val arrivalInfoList = sharedFlow.flatMapLatest {
        repository.fetchEstimatedArrivalInfoList(
            cityCode = cityCode,
            nodeId = nodeId,
            onComplete = {

            },
            onError = {

            }
        )
    }

    init {
        savedStateHandle.get<Int>(NavScreen.BusStopArrivalInfo.CityCode)?.let {
            cityCode = it
        }
        savedStateHandle.get<String>(NavScreen.BusStopArrivalInfo.NodeId)?.let {
            nodeId = it
        }
        fetchEstimatedArrivalInfoList()
    }

    fun fetchEstimatedArrivalInfoList() = sharedFlow.tryEmit(Unit)

}
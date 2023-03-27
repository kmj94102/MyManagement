package com.example.mymanagement.view.xml.ui.transportation.subway

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymanagement.database.entity.StationItem
import com.example.mymanagement.view.xml.model.Station
import com.example.network.repository.SubwayRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SubwaySearchViewModel2 @Inject constructor(
    private val subwayRepository: SubwayRepository
) : ViewModel() {
    private val sharedFlow = MutableSharedFlow<String>(replay = 1)

    val stationList: StateFlow<List<Station>> = sharedFlow.flatMapLatest {
        subwayRepository.fetchStationItems(
            stationName = it,
            isFavoriteOnly = false
        ).map { list ->
            Station.fromStationItemList(list)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500L),
        initialValue = emptyList()
    )

    private val _startStation = MutableStateFlow<Station?>(null)
    val stationStation: StateFlow<Station?> = _startStation

    private val _endStation = MutableStateFlow<Station?>(null)
    val endStation: StateFlow<Station?> = _endStation

    init {
        fetchStationList("")
    }

    fun fetchStationList(search: String) {
        sharedFlow.tryEmit(search)
    }

    fun startChangeListener(station: Station?) {
        _startStation.value = station
    }

    fun endChangeListener(station: Station?) {
        _endStation.value = station
    }

}
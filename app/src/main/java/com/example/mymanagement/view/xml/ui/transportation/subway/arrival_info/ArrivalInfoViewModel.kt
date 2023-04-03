package com.example.mymanagement.view.xml.ui.transportation.subway.arrival_info

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymanagement.util.SubwayLine
import com.example.mymanagement.util.toHexString
import com.example.network.model.SubwayArrival
import com.example.network.repository.SubwayRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ArrivalInfoViewModel @Inject constructor(
    private val repository: SubwayRepository
) : ViewModel() {

    private val _arrivalInfoList = MutableStateFlow<List<SubwayArrival>>(emptyList())

    private val _arrivalInfo = MutableStateFlow<SubwayArrival?>(null)
    val arrivalInfo: StateFlow<SubwayArrival?> = _arrivalInfo

    private val _lineList = MutableStateFlow<List<SubwayLine>>(emptyList())
    val lineList: StateFlow<List<SubwayLine>> = _lineList

    private val _upLineInfo = MutableStateFlow<List<String>>(emptyList())
    val upLineInfo: StateFlow<List<String>> = _upLineInfo
    private val _downLineInfo = MutableStateFlow<List<String>>(emptyList())
    val downLineInfo: StateFlow<List<String>> = _downLineInfo

    private val _color = MutableStateFlow(0xFF0D3692.toHexString())
    val color: StateFlow<String> = _color

    fun fetchArrivals(stationName: String) {
        repository
            .fetchRealtimeStationArrivals(stationName)
            .onStart { }
            .onEach {
                _arrivalInfoList.value = it
                _lineList.value = _arrivalInfoList.value.map { info -> SubwayLine.getSubwayLineByCode(info.subwayLineId) }
                setArrivalInfo(0)
            }
            .onEach { }
            .catch { it.printStackTrace() }
            .launchIn(viewModelScope)
    }

    private fun setArrivalInfo(index: Int) {
        _arrivalInfoList.value.getOrNull(index)?.let { arrival ->
            _arrivalInfo.value = arrival
            _upLineInfo.value =
                arrival.arrItemList.filter { it.isUpLine }.map { it.arrInfo }.take(3)
            _downLineInfo.value =
                arrival.arrItemList.filter { it.isUpLine.not() }.map { it.arrInfo }.take(3)
            _color.value = SubwayLine.getSubwayLineByCode(arrival.subwayLineId).color.toHexString()
        }
    }

    fun onChipSelect(index: Int) {
        setArrivalInfo(index)
    }



}
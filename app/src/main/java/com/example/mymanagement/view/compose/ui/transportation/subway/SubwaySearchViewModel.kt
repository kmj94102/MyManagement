package com.example.mymanagement.view.compose.ui.transportation.subway

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymanagement.database.entity.StationItem
import com.example.network.model.SubwayArrival
import com.example.network.repository.SubwayRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.internal.filterList
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SubwaySearchViewModel @Inject constructor(
    private val repository: SubwayRepository,
) : ViewModel() {

    private val _isNeedUpdate = mutableStateOf(false)
    val isNeedUpdate: State<Boolean> = _isNeedUpdate

    private val _isProgress = mutableStateOf(false)
    val isProgress: State<Boolean> = _isProgress

    private var allOrFavorite = listOf(0, 1)
    private var stationName = ""

    private val _lineNumbersMap = mutableStateMapOf<String, Boolean>()
    val lineNumbersMap: Map<String, Boolean> = _lineNumbersMap

    private val _departure = mutableStateOf<StationItem?>(null)
    val departure: State<StationItem?> = _departure

    private val _arrival = mutableStateOf<StationItem?>(null)
    val arrival: State<StationItem?> = _arrival

    private val stationItemsSharedFlow = MutableSharedFlow<String>(replay = 1)
    val stationItems = stationItemsSharedFlow.flatMapLatest {
        repository.fetchStationItems(
            stationName = it,
            allOrFavorite = allOrFavorite,
//            selectLines = _lineNumbersMap.filterValues { value -> value }.keys.toList()
        )
    }.onStart { _isProgress.value = true }
        .onCompletion { _isProgress.value = false }
        .retryWhen { cause, attempt ->
            // 재시도 여부를 결정하는 함수
            if (cause is NoSuchElementException && attempt < 2) {
                true
            } else {
                throw cause // 그 외의 경우에는 예외를 다시 던짐
            }
        }
//        .map {
//            it.filterList {
//                _lineNumbersMap
//                    .filterValues { value -> value }
//                    .keys
//                    .toList()
//                    .any { selectLine ->
//                        lineNames.split(",").contains(selectLine)
//                    }
//            }.ifEmpty {
//                _isProgress.value = false
//                emptyList()
//            }
//        }
        .catch { it.printStackTrace() }

    private val _arrivalInfoMap = MutableStateFlow<List<SubwayArrival>>(listOf())
    val arrivalInfoMap: Flow<List<SubwayArrival>> = _arrivalInfoMap

    init {
        searchStations()
    }

    /** 지하철 호선 정보 조회 **/
    private fun fetchStationLineNumbers() = viewModelScope.launch {
        _lineNumbersMap.putAll(repository.fetchStationLineNumbers())
    }

    /** 지하철 역 검색 **/
    private fun searchStations() {
        stationItemsSharedFlow.tryEmit(stationName)
    }

    /** 지하철 역 이름으로 지하철 역 검색 **/
    fun searchStationsByStationName(stationName: String) {
        this.stationName = stationName
        searchStations()
    }

    /** 즐겨 찾기만 조회 **/
    fun searchStationsAllOrFavorite(isFavorite: Boolean) {
        allOrFavorite = if (isFavorite) listOf(1) else listOf(0, 1)
        searchStations()
    }

    /** 즐겨찾기 업데이트 **/
    fun updateFavorite(
        item: StationItem
    ) = viewModelScope.launch {
        repository.updateFavorite(
            item = item
        )
    }

    /** 지하철 도착 정보 조회 **/
    fun fetchRealtimeStationArrivals(
        stationName: String
    )  {
        repository
            .fetchRealtimeStationArrivals(stationName)
            .onStart { _isProgress.value = true }
            .onEach { _arrivalInfoMap.value = it }
            .onCompletion { _isProgress.value = false }
            .catch {
                _arrivalInfoMap.value = emptyList()
                it.printStackTrace()
            }
            .launchIn(viewModelScope)
    }

    fun swapDepartureAndArrival() {
        val temp = Pair(_arrival.value, _departure.value)
        _arrival.value = temp.second
        _departure.value = temp.first
    }

    fun setDeparture(departure: StationItem) {
        _departure.value = departure
        if (_arrival.value == departure) {
            _arrival.value = null
        }
    }

    fun setArrival(arrival: StationItem) {
        _arrival.value = arrival
        if (_departure.value == arrival) {
            _arrival.value = null
        }
    }

}
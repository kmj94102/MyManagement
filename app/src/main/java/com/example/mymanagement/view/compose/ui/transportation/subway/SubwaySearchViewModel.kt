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
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SubwaySearchViewModel @Inject constructor(
    private val repository: SubwayRepository,
) : ViewModel() {

    private val _isProgress = mutableStateOf(false)
    val isProgress: State<Boolean> = _isProgress

    // 즐겨찾기만 표시 여부
    private var isFavoriteOnly = false

    // 검색할 역 이름
    private var stationName = ""

    private val _lineNumbersMap = mutableStateMapOf<String, Boolean>()
    val lineNumbersMap: Map<String, Boolean> = _lineNumbersMap

    // 목적지
    private val _departure = mutableStateOf<StationItem?>(null)
    val departure: State<StationItem?> = _departure

    // 출발지
    private val _arrival = mutableStateOf<StationItem?>(null)
    val arrival: State<StationItem?> = _arrival

    // 지하철 역 리스트
    private val stationItemsSharedFlow = MutableSharedFlow<String>(replay = 1)
    val stationItems = stationItemsSharedFlow
        .flatMapLatest {
            repository.fetchStationItems(
                stationName = it,
                isFavoriteOnly = isFavoriteOnly
            )
        }
        .onStart { _isProgress.value = true }
        .onCompletion { _isProgress.value = false }
        .retryWhen { cause, attempt ->
            // 재시도 여부를 결정하는 함수
            if (cause is NoSuchElementException && attempt < 2) {
                true
            } else {
                throw cause // 그 외의 경우에는 예외를 다시 던짐
            }
        }
        .catch { it.printStackTrace() }

    // 실시간 도착 정보 리스트
    private val _arrivalInfoList = MutableStateFlow<List<SubwayArrival>>(listOf())
    val arrivalInfoList: Flow<List<SubwayArrival>> = _arrivalInfoList

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
        isFavoriteOnly = isFavorite
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
    ) {
        repository
            .fetchRealtimeStationArrivals(stationName)
            .onStart { _isProgress.value = true }
            .onEach { _arrivalInfoList.value = it }
            .onCompletion { _isProgress.value = false }
            .catch {
                _arrivalInfoList.value = emptyList()
                it.printStackTrace()
            }
            .launchIn(viewModelScope)
    }

    /** 출발지, 목적지 바꾸기 **/
    fun swapDepartureAndArrival() {
        val temp = Pair(_arrival.value, _departure.value)
        _arrival.value = temp.second
        _departure.value = temp.first
    }

    /** 목적지 설정 **/
    fun setDeparture(departure: StationItem) {
        _departure.value = departure
        if (_arrival.value == departure) {
            _arrival.value = null
        }
    }

    /** 출발지 설정 **/
    fun setArrival(arrival: StationItem) {
        _arrival.value = arrival
        if (_departure.value == arrival) {
            _arrival.value = null
        }
    }

}
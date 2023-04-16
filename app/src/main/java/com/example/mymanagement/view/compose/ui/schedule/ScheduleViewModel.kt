package com.example.mymanagement.view.compose.ui.schedule

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymanagement.util.getToday
import com.example.mymanagement.view.compose.ui.custom.model.CalendarItem
import com.example.mymanagement.view.compose.ui.custom.model.fetchCalendarInfo
import com.example.network.repository.KakaoRepository
import com.example.network.util.convertToDateTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val kakaoRepository: KakaoRepository
) : ViewModel() {

    val today = getToday("yyyy.MM.dd")
    private val _year = mutableStateOf(today.substring(0, 4))
    val year: State<String> = _year
    private val _month = mutableStateOf(today.substring(5, 7))
    val month: State<String> = _month

    private val _calendarInfo = mutableStateListOf<CalendarItem>()
    val calendarInfo: List<CalendarItem> = _calendarInfo

    private val _selectDate = mutableStateOf(today)
    val selectDate: State<String> = _selectDate

    val selectCalendarItem
        get() = run { _calendarInfo.find { it.detailDate == _selectDate.value } }


    init {
        setCalendarInfo()
    }

    private fun setCalendarInfo() {
        _calendarInfo.clear()
        _calendarInfo.addAll(
            fetchCalendarInfo(year = _year.value.toInt(), month = _month.value.toInt())
        )
        fetchSchedules()
    }

    private fun fetchHoliday() {
        kakaoRepository
            .fetchHolidays(
                year = _year.value,
                month = _month.value
            )
            .onEach {
                it.forEach { item ->
                    val index = _calendarInfo.indexOfFirst { calendarItem ->
                        calendarItem.detailDate == convertToDateTime(item.time.startAt)
                    }

                    if (index != -1) {
                        _calendarInfo[index] = _calendarInfo[index].copy(
                            isHoliday = item.isHoliday,
                            dateInfo = item.title
                        )
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun fetchSchedules() {
        kakaoRepository
            .fetchSchedules(
                year = _year.value,
                month = _month.value
            )
            .onEach {
                it.forEach { item ->
                    val index = _calendarInfo.indexOfFirst { calendarItem ->
                        calendarItem.detailDate == convertToDateTime(item.startAt)
                    }

                    if (index != -1) {
                        _calendarInfo[index] = _calendarInfo[index].copy(
                            isHoliday = item.isHoliday,
                            dateInfo = item.title
                        )
                        if (item.group == 1) {
                            _calendarInfo[index].scheduleList.add(item)
                        }
                    }
                }
            }
            .catch { it.printStackTrace() }
            .launchIn(viewModelScope)
    }

    fun setYearMonth(year: String, month: String) {
        if (_year.value == year && _month.value == month) return

        _year.value = year
        _month.value = month
        _selectDate.value = "$year.$month.01"
        setCalendarInfo()
    }

    fun onSelectChange(selectDate: String) {
        _selectDate.value = selectDate
    }

}
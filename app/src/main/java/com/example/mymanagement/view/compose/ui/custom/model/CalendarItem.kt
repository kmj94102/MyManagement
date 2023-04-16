package com.example.mymanagement.view.compose.ui.custom.model

import com.example.network.model.kakao.Schedule
import java.util.*

/**
 * 달력 아이템
 * @param date 날짜
 * @param isHoliday 공휴일 여부
 * @param dateInfo 날짜 정보 (ex : 추석, 설날)
 * @param dayOfWeek 요일
 * @param detailDate 상세 날짜
 * @param scheduleList 일정 리스트
 * **/
data class CalendarItem(
    val date: String = "",
    val isHoliday: Boolean = false,
    val dateInfo: String = "",
    val dayOfWeek: String = "",
    val detailDate: String = "",
    val scheduleList: MutableList<Schedule> = mutableListOf(),
)

/** 달력 정보 생성 **/
fun fetchCalendarInfo(
    year: Int,
    month: Int
): List<CalendarItem> {
    val calendar = Calendar.getInstance().apply {
        set(Calendar.YEAR, year)
        set(Calendar.MONTH, month - 1)
        set(Calendar.DAY_OF_MONTH, 1)
    }
    val monthDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    val firstDay = calendar.get(Calendar.DAY_OF_WEEK)

    val lastDayIndex = firstDay + monthDays - 2
    val lastIndex = if (lastDayIndex < 35) 34 else 41

    return (0..lastIndex)
        .map {
            if (it < firstDay - 1 || it > lastDayIndex) {
                CalendarItem()
            } else {
                val date = (it - firstDay + 2).toString()
                CalendarItem(
                    date = date,
                    dayOfWeek = getDayOfWeek(it),
                    detailDate = getDetailDate(year, month, date)
                )
            }
        }
}

private fun getDayOfWeek(index: Int): String {
    val list = listOf("일", "월", "화", "수", "목", "금", "토")
    return list[index % 7]
}

private fun getDetailDate(year: Int, month: Int, date: String) =
    "$year.${month.toString().padStart(2, '0')}.${date.padStart(2, '0')}"

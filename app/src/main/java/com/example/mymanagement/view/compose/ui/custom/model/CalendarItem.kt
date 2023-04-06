package com.example.mymanagement.view.compose.ui.custom.model

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
    val scheduleList: List<String> = listOf(),
)

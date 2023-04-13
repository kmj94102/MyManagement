package com.example.network.model.kakao

data class CalendarIdInfo(
    val calendars: List<Calendars>
)

data class Calendars(
    val id: String
)
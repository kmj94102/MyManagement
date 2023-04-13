package com.example.network.model.kakao

import com.google.gson.annotations.SerializedName

data class EventParam(
    val event: EventCreate
)
/**
 * @param title 일정 제목(최대 50자) [필수]
 * @param startAt 일정 시작 시간 [필수]
 * @param endAt 일정 종료 시간 [필수]
 * @param isAllDay 하루 종일 여부
 * @param rrule 일정의 반복 주기, UTC*, RFC5545의 RRULE 형식(예: FREQ=DAILY;UNTIL=20211208T155959Z)
 *              주의: 해당 파라미터 포함 시 반복 일정 생성
 * @param description 일정 설명(최대 5000자)
 * **/
data class EventCreate(
    val title: String,
    val startAt: String,
    val endAt: String,
    val isAllDay: Boolean = false,
    val rrule: String = "",
    val description: String = "",
)

data class EventCaretResult(
    @SerializedName("event_id")
    val eventId: String
)
package com.example.network.model.kakao

import com.example.network.util.convertToDateTime
import com.example.network.util.convertToRFC5545
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

object RepeatRrule {
    const val NoRepeat = "NoRepeat"
    const val Daily = "DAILY"
    const val Weekly = "WEEKLY"
    const val Monthly = "MONTHLY"
    const val Yearly = "YEARLY"

    fun createRrule(repeatCycle: String, until: String) =
        if (repeatCycle == NoRepeat) {
            ""
        } else {
            "FREQ=$repeatCycle;UNTIL=${convertToRFC5545("$until 23:59")}"
        }

    fun getRepeatState(rrule: String) =
        when {
            rrule.contains(Daily) -> Daily
            rrule.contains(Weekly) -> Weekly
            rrule.contains(Monthly) -> Monthly
            rrule.contains(Yearly) -> Yearly
            else -> NoRepeat
        }

    fun getUntilTile(rrule: String) = convertToDateTime(rrule.split("=").last())

    fun rruleToUiString(rrule: String) =
        when(getRepeatState(rrule)) {
            Daily -> {
                "${getUntilTile(rrule)}까지 매일 반복"
            }
            Weekly -> {
                "${getUntilTile(rrule)}까지 매주 반복"
            }
            Monthly -> {
                "${getUntilTile(rrule)}까지 매월 반복"
            }
            Yearly -> {
                "${getUntilTile(rrule)}까지 매년 반복"
            }
            else -> "반복 안 함"
        }
}
package com.example.network.model.kakao

data class ScheduleResult(
    val id: String?,
    val title: String?,
    val time: Time
) {
    fun toSchedule(): Schedule? {
        return Schedule(
            id = id ?: return null,
            title = title ?: return null,
            startAt = time.startAt ?: return null,
            endAt = time.endAt ?: return null,
            isAllDay = time.allDay,
            isHoliday = false,
            group = 1
        )
    }
}

/**
 * 일정
 * @param id 일정 아이디
 * @param title 일정 제목
 * @param startAt 일정 시작 시간
 * @param endAt 일정 종료 시간
 * @param isAllDay 하루 종일 여부
 * @param isHoliday 공휴일 여부
 * @param group 일정 그룹 : 0 - 공휴일, 1 - 등록한 일정
 * **/
data class Schedule(
    val id: String,
    val title: String,
    val startAt: String,
    val endAt: String,
    val isAllDay: Boolean,
    val isHoliday: Boolean,
    val group: Int
)
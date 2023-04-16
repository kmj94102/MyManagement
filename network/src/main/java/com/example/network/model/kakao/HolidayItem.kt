package com.example.network.model.kakao

import com.google.gson.annotations.SerializedName

data class HolidayItem(
    val id: String,
    val title: String,
    val time: Time,
    @SerializedName("holiday")
    val isHoliday: Boolean = false
) {
    fun toSchedule(): Schedule? {
        return Schedule(
            id = id,
            title = title,
            startAt = time.startAt ?: return null,
            endAt = time.endAt ?: return null,
            isAllDay = true,
            isHoliday = isHoliday,
            group = 0
        )
    }
}

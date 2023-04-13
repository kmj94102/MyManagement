package com.example.network.model.kakao

import com.google.gson.annotations.SerializedName

data class HolidayItem(
    val id: String,
    val title: String,
    val time: Time,
    @SerializedName("holiday")
    val isHoliday: Boolean
)

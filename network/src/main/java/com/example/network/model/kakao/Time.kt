package com.example.network.model.kakao

import com.google.gson.annotations.SerializedName

/**
 * @param startAt 일정 시작 시각, UTC*, RFC5545의 DATE-TIME 형식(예: 2022-05-17T12:00:00Z)
 *                 주의: all_day가 true인 경우 YYYY-MM-DDT00:00:00Z으로 설정(다른 값 설정 시 에러 발생)
 *                 [일정 생성 시 필수]
 * @param endAt   일정 종료 시각, start_at과 동일
 *                 [일정 생성 시 필수]
 * @param timeZone 타임존 설정, UTC*, RFC5545의 TZID 형식(예: Asia/Seoul)
 * @param allDay 종일 일정 여부(기본값: false)
 * @param lunar 날짜 기준을 음력으로 설정(기본값: false)
 * **/
data class Time(
    @SerializedName("start_at") val startAt: String? = null,
    @SerializedName("end_at") val endAt: String? = null,
    @SerializedName("time_zone") val timeZone: String? = "Asia/Seoul",
    @SerializedName("all_day") val allDay: Boolean = false,
    val lunar: Boolean = false
)

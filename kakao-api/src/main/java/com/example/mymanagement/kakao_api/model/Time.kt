package com.example.mymanagement.kakao_api.model

/**
 * @param start_at 일정 시작 시각, UTC*, RFC5545의 DATE-TIME 형식(예: 2022-05-17T12:00:00Z)
 *                 주의: all_day가 true인 경우 YYYY-MM-DDT00:00:00Z으로 설정(다른 값 설정 시 에러 발생)
 *                 [일정 생성 시 필수]
 * @param end_at   일정 종료 시각, start_at과 동일
 *                 [일정 생성 시 필수]
 * @param time_zone 타임존 설정, UTC*, RFC5545의 TZID 형식(예: Asia/Seoul)
 * @param all_day 종일 일정 여부(기본값: false)
 * @param lunar 날짜 기준을 음력으로 설정(기본값: false)
 * **/
data class Time(
    val start_at: String? = null,
    val end_at: String? = null,
    val time_zone: String? = null,
    val all_day: Boolean = false,
    val lunar: Boolean = false
)

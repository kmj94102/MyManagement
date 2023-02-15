package com.example.mymanagement.kakao_api.model

/**
 * @param title 일정 제목(최대 50자) [필수]
 * @param time 일정 시간 [필수]
 * @param rrule 일정의 반복 주기, UTC*, RFC5545의 RRULE 형식(예: FREQ=DAILY;UNTIL=20211208T155959Z)
 *              주의: 해당 파라미터 포함 시 반복 일정 생성
 * @param description 일정 설명(최대 5000자)
 * @param location 일정 장소
 * @param color 일정 색상, Color 중 하나 (기본값: 해당 캘린더의 color 값)
 * @param reminders 미리 알림 설정(단위: 분), 5분 간격으로 최대 2개 설정 가능
 *                  종일 일정 범위:
 *                  -1440(일정 당일이 끝나기 전) < 알림값 ≤ 43200(일정 시작 30일 전)
 *                  (기본값: 해당 캘린더의 reminder 값)
 *                  종일 일정이 아닌 일정 범위:
 *                  0(일정 시작 시각) < 알림값 ≤ 43200(일정 시작 30일 전)
 *                  (기본값: 해당 캘린더의 reminder_all_day 값)
 * **/
data class EventCreate(
    val title: String,
    val time: Time,
    val rrule: String? = null,
    val description: String? = null,
    val location: Location? = null,
    val reminders: List<Int>? = null,
    val color: String? = null
)

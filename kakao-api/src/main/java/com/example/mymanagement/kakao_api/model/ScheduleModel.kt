package com.example.mymanagement.kakao_api.model

data class SetScheduleParam(
    val calendar_id: String,
    val event: EventCreate
)
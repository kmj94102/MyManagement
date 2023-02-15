package com.example.mymanagement.kakao_api.model

/**
 * @param name 장소 이름(최대 100자) [일정 생성 시 필수]
 * @param location_id 장소 ID
 * @param address 주소
 * @param latitude 위도
 * @param longitude 경도
 * **/
data class Location(
    val name: String? = null,
    val location_id: Long? = null,
    val address: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null
)

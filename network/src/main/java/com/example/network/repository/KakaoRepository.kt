package com.example.network.repository

import android.content.Context
import com.example.mymanagement.database.entity.TokenEntity
import com.example.network.model.kakao.EventCreate
import com.example.network.model.kakao.HolidayItem
import com.example.network.model.kakao.Schedule
import kotlinx.coroutines.flow.Flow

interface KakaoRepository {
    /**
     * 카카오 토큰 조회
     * **/
    fun fetchAccessTokenFlow(): Flow<String>

    /**
     * 카카오 로그인
     * **/
    suspend fun kakaoLogin(context: Context)

    /**
     * 카카오 로그아웃
     * **/
    suspend fun kakaoLogout()

    /**
     * 공휴일 조회
     * **/
    fun fetchHolidays(
        year: String,
        month: String
    ): Flow<List<HolidayItem>>

    /**
     * 달력 Id 가져오기
     * **/
    suspend fun fetchCalendarId()

    /**
     * 일정 등록
     * **/
    suspend fun createEvent(
        eventCreate: EventCreate,
        onSuccess: (String) -> Unit,
        onError: () -> Unit
    )

    /**
     * 일정 조회
     * **/
    fun fetchSchedules(
        year: String,
        month: String
    ): Flow<List<Schedule>>
}
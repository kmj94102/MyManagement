package com.example.mymanagement.kakao_api.service

import com.example.mymanagement.kakao_api.model.EventCreate
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface KakaoService {

    /**
     * 사용자 캘린더 목록 가져오기
     * @param token ACCESS TOKEN
     * @param filter 목록을 가져올 캘린더 타입, 다음중 하나:
     *               USER: 기본 캘린더, 서브 캘린더
     *               SUBSCRIBE: 구독한 구독 캘린더
     *               ALL: 전체 사용자 캘린더(기본값)
     **/
    @GET("/v2/api/calendar/calendars")
    suspend fun getCalenderList(
        @Header("Authorization") token: String,
        @Query("filter") filter: String? = "USER"
    )

    @GET("/v2/api/calendar/holidays")
    suspend fun getHolidays(
        @Header("Authorization") token: String,
        @Query("from") from: String,
        @Query("to") to: String
    )

    @GET("/v2/api/calendar/events")
    suspend fun getSchedules(
        @Header("Authorization") token: String,
        @Query("calendar_id") id: String = "primary",
        @Query("from") from: String,
        @Query("to") to: String
    )
    @FormUrlEncoded
    @POST("/v2/api/calendar/create/event")
    suspend fun setSchedule(
        @Header("Authorization") token: String,
//        @Field("calendar_id") calendar_id: String,
        @Field("event") event: String
    )

}
package com.example.network.service

import com.example.network.BuildConfig
import com.example.network.model.PlaceResult
import com.example.network.model.kakao.*
import retrofit2.http.*

interface KakaoService {
    /**
     * 토큰 갱신
     * **/
    @FormUrlEncoded
    @POST("https://kauth.kakao.com/oauth/token")
    suspend fun fetchUpdateToken(
        @Field("grant_type") grantType: String = "refresh_token",
        @Field("client_id") clientId: String = BuildConfig.KAKAO_REST_API_KEY,
        @Field("refresh_token") refreshToken: String
    ): TokenResponse

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
    ): CalendarIdInfo

    /**
     * 공휴일 정보 가져오기
     * @param from 일정을 조회할 기간의 시작 시각, UTC*, RFC5545의 DATE-TIME 형식(예: 2022-05-17T12:00:00Z)
     * @param to 일정을 조회할 기간의 종료 시각, from 이후 31일 이내의 값, UTC*, RFC5545의 DATE-TIME 형식(예: 2022-06-17T12:00:00Z)
     * **/
    @GET("/v2/api/calendar/holidays")
    suspend fun getHolidays(
        @Header("Authorization") token: String = BuildConfig.KAKAO_ADMIN_AUTHORIZATION,
        @Query("from") from: String,
        @Query("to") to: String
    ): KakaoApiResult<HolidayItem>

    /**
     * 달력 권한 체크
     * **/
    @GET("v2/user/scopes")
    suspend fun fetchUserScopes(
        @Header("Authorization") accessToken: String,
        @Query("scopes") scopes: String
    ): KakaoScopeResult

    /**
     * 일정 리스트 조회
     * **/
    @GET("/v2/api/calendar/events")
    suspend fun fetchSchedules(
        @Header("Authorization") token: String,
        @Query("calendar_id") id: String = "primary",
        @Query("from") from: String,
        @Query("to") to: String
    ): KakaoApiResult<ScheduleResult>

    /**
     * 일정 생성
     * **/
    @FormUrlEncoded
    @POST("/v2/api/calendar/create/event")
    suspend fun setSchedule(
        @Header("Authorization") token: String,
        @Field("event", encoded=true) event: String,
    ): EventCaretResult

    @Headers("Content-Type: application/json")
    @POST("/v2/api/calendar/create/event")
    suspend fun setSchedule2(
        @Header("Authorization") token: String,
        @Body event: EventParam,
    ): EventCaretResult

    /**
     * 키워드로 장소 검색하기
     * **/
    @GET("https://dapi.kakao.com/v2/local/search/keyword.json")
    suspend fun fetchPlaceListByKeyword(
        @Header("Authorization") token: String = "KakaoAK ${BuildConfig.KAKAO_REST_API_KEY}",
        @Query("query") query: String = "서울역"
    ): PlaceResult

}
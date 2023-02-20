package com.example.network.service

import com.example.network.model.EventCreate
import com.example.network.model.PlaceResult
import javax.inject.Inject

class KakaoClient @Inject constructor(
    private val kakaoService: KakaoService
) {

    suspend fun getCalendarList(token: String) {
        try {
            kakaoService.getCalenderList(token)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun getHolidays() {
        try {
            kakaoService.getHolidays(
                token = "KakaoAK 78a770657caca9dec4175cf17375acf4",
                from = "2023-02-01T00:00:00Z",
                to = "2023-02-31T00:00:00Z"
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun getSchedules(token: String) {
        try {
            kakaoService.getSchedules(
                token = token,
                from = "2023-02-01T00:00:00Z",
                to = "2023-02-31T00:00:00Z"
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun setSchedule(
        token: String,
        calendarId: String,
        event: EventCreate
    ) {
        try {
            kakaoService.setSchedule(
                token = token,
                event = "{\"title\":\"test\", \"time\":{\"start_at\":\"2023-02-18T18:00:00Z\", \"end_at\":\"2023-02-18T20:00:00Z\", \"time_zone\": \"Asia/Seoul\", \"all_day\": false, \"lunar\": false}}"
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /** 키워드로 장소 검색 **/
    suspend fun fetchPlaceListByKeyword(keyword: String) =
        kakaoService.fetchPlaceListByKeyword(query = keyword)
}
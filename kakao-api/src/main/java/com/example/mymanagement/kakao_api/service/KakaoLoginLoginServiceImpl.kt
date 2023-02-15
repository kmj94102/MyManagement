package com.example.mymanagement.kakao_api.service

import android.content.Context
import android.util.Log
import com.example.mymanagement.kakao_api.model.EventCreate
import com.example.mymanagement.kakao_api.model.SetScheduleParam
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.User
import retrofit2.Retrofit
import javax.inject.Inject

class KakaoLoginLoginServiceImpl @Inject constructor(
    private val userApiClient: UserApiClient,
    private val kakaoService: KakaoService
) : KakaoLoginService {

    override fun getUserInfo(
        context: Context,
        callback: (User?, Throwable?) -> Unit,
    ) {
        userApiClient.me { user, error ->
            val scope = mutableListOf("talk_calendar")
            UserApiClient.instance.loginWithNewScopes(context, scope) { token, error ->
                if (error != null) {
                    Log.e("+++++", "error : ${error.cause}")
                }
                Log.e("+++++", "sucess : ${token?.scopes}")
            }
            callback(user, error)
        }
    }

    override fun loginWithKakaoAccount(
        context: Context,
        callback: (OAuthToken?, Throwable?) -> Unit
    ) {
        userApiClient.loginWithKakaoAccount(
            context = context,
            callback = callback
        )
    }

    override fun loginWithKakaoTalk(
        context: Context,
        callback: (OAuthToken?, Throwable?) -> Unit
    ) {
        userApiClient.loginWithKakaoTalk(
            context = context,
            callback = callback
        )
    }

    override fun logout(
        callback: (Throwable?) -> Unit
    ) {
        userApiClient.logout {
            callback(it)
        }
    }

    override suspend fun getCalendarList(token: String) {
        try {
            kakaoService.getCalenderList(token)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getHolidays() {
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

    override suspend fun getSchedules(token: String) {
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

    override suspend fun setSchedule(
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

}
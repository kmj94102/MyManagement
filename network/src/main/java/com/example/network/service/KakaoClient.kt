package com.example.network.service

import android.content.Context
import android.util.Log
import com.example.network.model.EventCreate
import com.example.network.model.PlaceResult
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.User
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class KakaoClient @Inject constructor(
    private val kakaoService: KakaoService,
    private val userApiClient: UserApiClient
) {

    /**
     * 카카오 로그인
     * **/
    suspend fun loginWithKakao(context: Context): OAuthToken {
        return if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            try {
                UserApiClient.loginWithKakaoTalk(context)
            } catch (error: Throwable) {
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) throw error

                UserApiClient.loginWithKakaoAccount(context)
            }
        } else {
            UserApiClient.loginWithKakaoAccount(context)
        }
    }

    /**
     * 카카오톡으로 로그인
     */
    private suspend fun UserApiClient.Companion.loginWithKakaoTalk(context: Context): OAuthToken {
        return suspendCoroutine { continuation ->
            instance.loginWithKakaoTalk(context) { token, error ->
                error?.printStackTrace()
                if (token != null) {
                    continuation.resume(token)
                }
            }
        }
    }

    suspend fun loginWithKakaoAccount(context: Context) =
        UserApiClient.loginWithKakaoAccount(context = context)

    /**
     * 카카오 계정으로 로그인
     */
    private suspend fun UserApiClient.Companion.loginWithKakaoAccount(context: Context): OAuthToken {
        return suspendCoroutine { continuation ->
            instance.loginWithKakaoAccount(context) { token, error ->
                error?.printStackTrace()
                if (token != null) {
                    continuation.resume(token)
                }
            }
        }
    }

    /**
     * 카카오 로그인
     * **/
    suspend fun logout(): Throwable? {
        return suspendCoroutine { continuation ->
            UserApiClient.instance.logout {
                continuation.resume(it)
            }
        }
    }

    fun getUserInfo2(
        context: Context,
        callback: (User?, Throwable?) -> Unit,
    ) {
        userApiClient.me { user, error ->
            val scope = mutableListOf("talk_calendar")
            UserApiClient.instance.loginWithNewScopes(context, scope) { token, e ->
                if (e != null) {
                    Log.e("+++++", "error : ${e.cause}")
                }
                Log.e("+++++", "sucess : ${token?.scopes}")
            }
            callback(user, error)
        }
    }

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
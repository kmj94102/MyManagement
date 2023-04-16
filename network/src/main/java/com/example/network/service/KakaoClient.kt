package com.example.network.service

import android.content.Context
import com.example.mymanagement.database.TokenDao
import com.example.mymanagement.database.entity.TokenEntity
import com.example.network.model.kakao.EventCreate
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class KakaoClient @Inject constructor(
    private val kakaoService: KakaoService,
    private val userApiClient: UserApiClient,
    private val dao: TokenDao
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
     * 카카오 로그아웃
     * **/
    suspend fun logout(): Throwable? {
        return suspendCoroutine { continuation ->
            UserApiClient.instance.logout {
                continuation.resume(it)
            }
        }
    }

    /**
     * 토큰 갱신하여 받아오기
     * **/
    suspend fun fetchUpdateToken(
        refreshToken: String
    ) = kakaoService.fetchUpdateToken(
        refreshToken = refreshToken
    )

    /**
     * 토큰 등록
     * **/
    suspend fun insetToken(
        tokenEntity: TokenEntity
    ) = dao.insertToken(tokenEntity)

    /**
     * 토큰 업데이트
     * **/
    suspend fun updateToken(
        tokenEntity: TokenEntity
    ) = dao.updateToken(tokenEntity)

    /**
     * 토큰 제거
     * **/
    suspend fun deleteToken() = dao.deleteAll()

    /**
     * 토큰 조회
     * **/
    suspend fun fetchToken() = dao.fetchToken()

    /**
     * AccessToken 조회
     * **/
    suspend fun fetchAccessToken() = dao.fetchAccessToken()

    /**
     * AccessToken 조회(flow)
     * **/
    fun fetchAccessTokenFlow() = dao.fetchAccessTokenFlow()

    /**
     * 달력 권한 체크
     * **/
    suspend fun checkCalendarPermission(
        token: String
    ) = kakaoService.fetchUserScopes(
        accessToken = "Bearer $token",
        scopes = "[\"talk_calendar\"]"
    ).scopes.map { it.id == "talk_calendar" }.firstOrNull()

    /** 달력 권한 요청 **/
    private suspend fun UserApiClient.Companion.loginWithNewScopes(context: Context): Boolean {
        val scope = mutableListOf("talk_calendar")
        return suspendCoroutine { continuation ->
            instance.loginWithNewScopes(context, scope) { _, error ->
                error?.printStackTrace()
                continuation.resume(error == null)
            }
        }
    }

    /** 달력 권한 요청 **/
    suspend fun requestCalendarPermission(context: Context) =
        UserApiClient.loginWithNewScopes(context)


    /**
     * 달력 리스트 조회
     * **/
    suspend fun getCalendarList(token: String) =
        kakaoService.getCalenderList("Bearer $token")

    /**
     * 공휴일 조회
     * **/
    suspend fun fetchHolidays(
        from: String,
        to: String
    ) = try {
        kakaoService.getHolidays(
            from = from,
            to = to
        ).events
    } catch (e: Exception) {
        e.printStackTrace()
        emptyList()
    }

    /**
     * 일정 조회
     * **/
    suspend fun fetchSchedules(
        token: String,
        from: String,
        to: String
    ) = try {
        kakaoService.fetchSchedules(
            token = "Bearer $token",
            from = from,
            to = to
        ).events
    } catch (e: Exception) {
        e.printStackTrace()
        emptyList()
    }

    /**
     * 일정 생성
     * **/
    suspend fun createSchedule(
        token: String,
        event: EventCreate
    ) = kakaoService.setSchedule(
        token = "Bearer $token",
        event = "{\"title\":\"${event.title}\", \"rrlue\":\"${event.rrule}\", \"description\":\"${event.description}\",\"title\":\"${event.title}\", \"time\":{\"start_at\":\"${event.startAt}\", \"end_at\":\"${event.endAt}\", \"time_zone\": \"Asia/Seoul\", \"all_day\": ${event.isAllDay}, \"lunar\": false}}"
    )

    /** 키워드로 장소 검색 **/
    suspend fun fetchPlaceListByKeyword(keyword: String) =
        kakaoService.fetchPlaceListByKeyword(query = keyword)
}
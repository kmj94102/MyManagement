package com.example.network.repository

import android.content.Context
import android.util.Log
import com.example.mymanagement.database.entity.TokenEntity
import com.example.network.model.kakao.EventCreate
import com.example.network.service.KakaoClient
import com.example.network.util.convertToRFC5545
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class KakaoRepositoryImpl @Inject constructor(
    private val client: KakaoClient,
    @ApplicationContext private val context: Context
) : KakaoRepository {

    override suspend fun kakaoLogin(context: Context) {
        val oauth = client.loginWithKakao(context)
        client.insetToken(
            TokenEntity(
                accessToken = oauth.accessToken,
                accessTokenExpiresAt = oauth.accessTokenExpiresAt.time,
                refreshToken = oauth.refreshToken
            )
        )
    }

    override suspend fun kakaoLogout() {
        client.logout()
        client.deleteToken()
    }

    override fun fetchAccessTokenFlow() = client.fetchAccessTokenFlow()

    private suspend fun fetchToken(): String {
        val todayTime = Calendar.getInstance().time.time
        val token = client.fetchToken()

        // accessToken 만료 10분 전 체크, 10분 이전 일 경우 토큰 갱신
        return if (token.accessTokenExpiresAt - 600 < todayTime) {
            val result = client.fetchUpdateToken(token.refreshToken)
            val tokenEntity = token.copy(
                accessToken = result.accessToken,
                accessTokenExpiresAt = if (result.expiresIn != null) {
                    (result.expiresIn * 1000) + todayTime
                } else {
                    token.accessTokenExpiresAt
                },
                refreshToken = result.refreshToken ?: token.refreshToken
            )
            client.updateToken(tokenEntity)
            client.fetchAccessToken()
        } else {
            token.accessToken
        }
    }

    override fun fetchHolidays(
        year: String,
        month: String
    ) = flow {
        emit(
            client.fetchHolidays(
                from = convertToRFC5545("$year.$month.01 00:00"),
                to = getLastDayOfMonth(year, month)
            )
        )
    }

    override suspend fun fetchCalendarId() {
//        val token = fetchToken()
//        try {
//            if (client.calendarPermission(context)) {
//                val calendars = client.getCalendarList(token)
//            } else {
        // 캘린더 권한 없음
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
    }

    private suspend fun checkCalendarPermission(): Boolean {
        val token = client.fetchAccessToken()
        return try {
            if (client.checkCalendarPermission(token) == true) {
                true
            } else {
                client.requestCalendarPermission(context)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun createEvent(
        eventCreate: EventCreate,
        onSuccess: (String) -> Unit,
        onError: () -> Unit
    ) {
        try {
            if (checkCalendarPermission()) {
                val token = fetchToken()
                onSuccess(
                    client.createSchedule(
                        token = token,
                        event = eventCreate
                    ).eventId
                )
            } else {
                onError()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            onError()
        }
    }

    override fun fetchSchedules(
        year: String,
        month: String
    ) = flow {
        try {
            val token = client.fetchAccessToken()
            if (checkCalendarPermission()) {
                val from = convertToRFC5545("$year.$month.01 00:00")
                val to = getLastDayOfMonth(year, month)

                emit(
                    client.fetchHolidays(
                        from = from,
                        to = to
                    ).mapNotNull { it.toSchedule() }
                )

                emit(
                    client.fetchSchedules(
                        token = token,
                        from = from,
                        to = to
                    ).mapNotNull { it.toSchedule() }
                )
            } else {
                throw Exception("달력 권한 없음")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getLastDayOfMonth(year: String, month: String): String {
        val calendar = Calendar.getInstance()
        SimpleDateFormat("yyyy.MM.dd", Locale.KOREA).parse("$year.$month.01")?.let {
            calendar.time = it
        }
        val lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        return convertToRFC5545(
            "$year.$month.${lastDay.toString().padStart(2, '0')} 23:59"
        )
    }
}
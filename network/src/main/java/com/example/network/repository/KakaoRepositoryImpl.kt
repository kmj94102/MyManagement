package com.example.network.repository

import android.content.Context
import android.util.Log
import com.example.network.model.kakao.EventCaretResult
import com.example.network.model.kakao.EventCreate
import com.example.network.model.storeOnLoginState
import com.example.network.service.KakaoClient
import com.example.network.util.convertToRFC5545
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class KakaoRepositoryImpl @Inject constructor(
    private val client: KakaoClient,
    @ApplicationContext private val context: Context
) : KakaoRepository {
    override fun fetchToken(): Flow<String> = context.storeOnLoginState.data.map { it.kakaoToken }

    private suspend fun updateToken(token: String) {
        context.storeOnLoginState.updateData {
            it.toBuilder().setKakaoToken(token).build()
        }
    }

    override suspend fun kakaoLogin(context: Context) {
        val oauth = client.loginWithKakao(context)
        Log.e("++++++", "$oauth")
        updateToken(oauth.accessToken)
    }

    override suspend fun kakaoLogout() {
        client.logout()
        updateToken("")
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

    override suspend fun fetchCalendarId() {
        val token = fetchToken()
        try {
            if (client.calendarPermission(context)) {
                val calendars = client.getCalendarList(token.first())
                calendars.calendars.forEach {
                    Log.e("+++++", it.id)
                }
            } else {
                Log.e("+++++", "캘린더 권한 없음")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun createEvent(
        eventCreate: EventCreate,
        onSuccess: (String) -> Unit,
        onError: () -> Unit
    ) {
        try {
            val token = fetchToken()
            Log.e("++++++", "${eventCreate.startAt} / ${eventCreate.endAt}")
            onSuccess(
                client.createSchedule(
                    token = token.first(),
                    event = eventCreate
                ).eventId
            )
        } catch (e: Exception) {
            e.printStackTrace()
            onError()
        }
    }
}
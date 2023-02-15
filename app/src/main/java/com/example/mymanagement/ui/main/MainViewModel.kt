package com.example.mymanagement.ui.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymanagement.kakao_api.model.EventCreate
import com.example.mymanagement.kakao_api.model.Time
import com.example.mymanagement.kakao_api.service.KakaoLoginService
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val client: KakaoLoginService
) : ViewModel() {

    var userInfo: User? = null
        private set
    private var accessToken = ""

    init {
//        getUserInfo()
    }

    fun loginWithKakaoAccount(
        context: Context,
        successListener: () -> Unit,
        failureListener: () -> Unit
    ) {
        client.loginWithKakaoAccount(
            context = context,
            callback = { token: OAuthToken?, error ->
                error?.printStackTrace()
                token?.let {
                    accessToken = it.accessToken
                    Log.e("+++++", "token : ${it.accessToken}")
                    successListener()
                } ?: failureListener()
            }
        )
    }

    fun getUserInfo(context: Context) {
        client.getUserInfo(context = context) { user, throwable ->
            userInfo = user
            throwable?.printStackTrace()
        }
    }

    fun logout() {
        client.logout {
            it?.printStackTrace()
            if (it == null) userInfo = null
        }
    }

    fun getCalendarList() = viewModelScope.launch {
        client.getCalendarList("Bearer $accessToken")
    }

    fun getHolidays() = viewModelScope.launch {
        client.getHolidays()
    }

    fun getSchedules() = viewModelScope.launch {
        client.getSchedules("Bearer $accessToken")
    }

    fun setSchedule() = viewModelScope.launch {
        client.setSchedule(
            token = "Bearer $accessToken",
            calendarId = "primary",
            event = EventCreate(
                title = "test",
                time = Time(
                    start_at = "2023-02-12T12:00:00Z",
                    end_at = "2023-02-12T12:00:00Z"
                )
            )
        )
    }

}
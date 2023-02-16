package com.example.mymanagement.ui.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymanagement.kakao_api.service.KakaoLoginClient
import com.example.network.model.EventCreate
import com.example.network.model.Time
import com.example.mymanagement.kakao_api.service.KakaoLoginService
import com.example.network.service.BusClient
import com.example.network.service.KakaoClient
import com.example.network.service.SubwayClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val loginClient: KakaoLoginClient,
    private val kakaoClient: KakaoClient,
    private val busClient: BusClient,
    private val subwayClient: SubwayClient
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
        loginClient.loginWithKakaoAccount(
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
        loginClient.getUserInfo(context = context) { user, throwable ->
            userInfo = user
            throwable?.printStackTrace()
        }
    }

    fun logout() {
        loginClient.logout {
            it?.printStackTrace()
            if (it == null) userInfo = null
        }
    }

    fun getCalendarList() = viewModelScope.launch {
        kakaoClient.getCalendarList("Bearer $accessToken")
    }

    fun getHolidays() = viewModelScope.launch {
        kakaoClient.getHolidays()
    }

    fun getSchedules() = viewModelScope.launch {
        kakaoClient.getSchedules("Bearer $accessToken")
    }

    fun setSchedule() = viewModelScope.launch {
        kakaoClient.setSchedule(
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

    fun getBusStopList() = viewModelScope.launch {
        busClient.getBusStopList()
    }

    fun getRouteList() = viewModelScope.launch {
        busClient.getTransitRouteList()
    }

    fun getEstimatedArrivalInfoList() = viewModelScope.launch {
        busClient.getEstimatedArrivalInfoList()
    }

    fun getRouteEstimatedArrivalInfoList() = viewModelScope.launch {
        busClient.getRouteEstimatedArrivalInfoList()
    }

    fun getPlaceListByKeyword() = viewModelScope.launch {
        kakaoClient.getPlaceListByKeyword()
    }

    fun getRealtimeStationArrivals() = viewModelScope.launch {
        subwayClient.getRealtimeStationArrivals()
    }

}
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

}
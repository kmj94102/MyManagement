package com.example.mymanagement.view.main

import androidx.lifecycle.ViewModel
import com.example.network.service.BusClient
import com.example.network.service.KakaoClient
import com.example.network.service.SubwayClient
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val kakaoClient: KakaoClient,
    private val busClient: BusClient,
    private val subwayClient: SubwayClient
) : ViewModel() {

}
package com.example.network.service

import com.example.network.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Path

interface SubwayService {

    @GET("subway/${BuildConfig.SUBWAY_API_KEY}/json/realtimeStationArrival/0/15/{keyword}")
    suspend fun getRealtimeStationArrivals(@Path("keyword", encoded = true) keyword: String)

}
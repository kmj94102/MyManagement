package com.example.network.model

import com.google.gson.annotations.SerializedName

/**
 * 버스 정류소 아이템
 * @param latitude 정류소 Y좌표
 * @param longitude 정류소 X좌표
 * @param nodeId 정류소 ID
 * @param nodeNm 정류소 명
 * @param nodeNo 정류소 번호
 * @param cityCode 도시 코드
 * **/
data class BusStop(
    @SerializedName("gpslati")
    val latitude: Double,
    @SerializedName("gpslong")
    val longitude: Double,
    @SerializedName("nodeid")
    val nodeId: String, // GGB207000604
    @SerializedName("nodenm")
    val nodeNm: String,
    @SerializedName("nodeno")
    val nodeNo: Int, // 8436
    @SerializedName("citycode")
    val cityCode: Int, // 31030
)

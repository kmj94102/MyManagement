package com.example.network.model

import com.google.gson.annotations.SerializedName

/**
 * 도착 예정 정보
 * @param nodeId 정류소 ID
 * @param nodeNm 정류소 명
 * @param routeId 노선 ID
 * @param routeNo 노선 번호
 * @param routeType 노선 유형
 * @param arrPrevStationCnt 도착 예정 버스 남은 정류장 수
 * @param arrTime 도착예정버스 차량유형
 * @param vehicleType 도착 예정 버스 도착 예상 시간(초)
 * **/
data class EstimatedArrivalInfo(
    @SerializedName("nodeid")
    val nodeId: String,
    @SerializedName("nodenm")
    val nodeNm: String,
    @SerializedName("routeid")
    val routeId: String,
    @SerializedName("routeno")
    val routeNo: Int,
    @SerializedName("routetp")
    val routeType: String,
    @SerializedName("arrprevstationcnt")
    val arrPrevStationCnt: Int,
    @SerializedName("arrtime")
    val arrTime: Int,
    @SerializedName("vehicletp")
    val vehicleType: String
)

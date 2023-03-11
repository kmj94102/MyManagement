package com.example.network.model

import com.google.gson.annotations.SerializedName

data class SubwayArrivalInfoResult(
    val realtimeArrivalList: List<SubwayArrivalInfo>
)

/**
 * 지하철 도착 정보
 * @param subwayLineId 지하철호선ID
 * @param updnLine 상하행선구분 (2호선 : (내선:0,외선:1),상행,하행)
 * @param prevStationId 이전지하철역ID
 * @param nextStationId 다음지하철역ID
 * @param destinationName 종착지하철역명
 * @param stationId 지하철 역 ID
 * @param subwayType 열차 종류 (급행, ITX)
 * @param arrTime 열차도착예정시간 (단위:초)
 * **/
data class SubwayArrivalInfo(
    @SerializedName("subwayId")
    val subwayLineId: String,
    val updnLine: String,
    @SerializedName("statnFid")
    val prevStationId: String,
    @SerializedName("statnTid")
    val nextStationId: String,
    @SerializedName("bstatnNm")
    val destinationName: String,
    @SerializedName("statnNm")
    val stationName: String,
    @SerializedName("statnId")
    val stationId: String,
    @SerializedName("btrainSttus")
    val subwayType: String,
    @SerializedName("barvlDt")
    val arrTime: String,
    @SerializedName("arvlMsg2")
    val arrInfo: String
)

/**
 * @param subwayLineId 지하철 호선 아이디
 * @param stationCode 지하철 역 코드
 * @param currentStationName 현재 역 이름
 * @param prevStationName 이전 역 이름
 * @param nextStationName 다음 역 이름
 * @param arrItemList 지하철 도착 정보
 * **/
data class SubwayArrival(
    val subwayLineId: String,
    val stationCode: String,
    val currentStationName: String,
    val prevStationName: String,
    val nextStationName: String,
    val arrItemList: List<SubwayArrivalItem>
)

/**
 * @param arrInfo 도착 정보 : 00행 0000
 * @param isUpLine 상행 또는 내선 여부
 * **/
data class SubwayArrivalItem(
    val arrInfo: String,
    val isUpLine: Boolean
)
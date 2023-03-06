package com.example.network.model

import com.google.gson.annotations.SerializedName
import kotlin.math.max

/**
 * 도착 예정 정보
 * @param nodeId 정류소 ID
 * @param nodeNm 정류소 명
 * @param routeId 노선 ID
 * @param routeNo 노선 번호
 * @param routeType 노선 유형
 * @param arrPrevStationCnt 도착 예정 버스 남은 정류장 수
 * @param arrTime 도착 예정 버스 도착 예상 시간(초)
 * @param vehicleType 도착예정버스 차량유형
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

data class BusEstimatedArrivalInfo(
    val busNumber: String,
    val routeId: String,
    val nodeId: String,
    val nodeName: String,
    val arrInfo: List<String>,
    var isFavorite: Boolean
)

fun List<EstimatedArrivalInfo>.toBusEstimatedArrivalInfo(isFavorite: Boolean) =
    BusEstimatedArrivalInfo(
        busNumber = "${this[0].routeNo}",
        routeId = this[0].routeId,
        nodeId = this[0].nodeId,
        nodeName = this[0].nodeNm,
        arrInfo = this.map { info ->
            "${minSecFormat(info.arrTime)} (${max(info.arrPrevStationCnt, 1)} 정류장 전)"
        },
        isFavorite = isFavorite
    )

private fun minSecFormat(originSec: Int): String {
    val min = originSec / 60
    val sec = originSec % 60

    return if (min == 0 && sec == 0) {
        "곧 도착"
    } else if (min == 0) {
        "${sec.toString().padStart(2, '0')}초"
    } else {
        "${min}분 ${sec.toString().padStart(2, '0')}초"
    }
}
package com.example.network.model

import com.example.mymanagement.database.entity.FavoriteEntity
import com.google.gson.annotations.SerializedName

/**
 * @param latitude 위도 좌표
 * @param longitude 경도 좌표
 * @param nodeId 정류소 ID
 * @param nodeName 정류소 명
 * @param nodeNumber 정류소 번호
 * @param routeId 노선 ID
 * @param upDownCode 상하행 구분 코드 [0:상행, 1:하행]
 * @param index 정류소 순번
 * **/
data class BusStopRoute(
    @SerializedName("gpslati")
    val latitude: Double,
    @SerializedName("gpslong")
    val longitude: Double,
    @SerializedName("nodeid")
    val nodeId: String,
    @SerializedName("nodenm")
    val nodeName: String,
    @SerializedName("nodeord")
    val index: Int,
    @SerializedName("nodeno")
    val nodeNumber: Int,
    @SerializedName("routeid")
    val routeId: String,
    @SerializedName("updowncd")
    val upDownCode: Int
) {
    fun toBusStopRouteItem(
        isEndNode: Boolean,
        isFavorite: Boolean
    ) = BusStopRouteItem(
        nodeId = nodeId,
        nodeName = nodeName,
        nodeNumber = nodeNumber,
        routeId = routeId,
        upDownCode = upDownCode,
        isStartNode = index == 1,
        isEndNode = isEndNode,
        isFavorite = isFavorite
    )
}

data class BusStopRouteItem(
    val nodeId: String,
    val nodeName: String,
    val nodeNumber: Int,
    val routeId: String,
    val upDownCode: Int,
    val isStartNode: Boolean,
    val isEndNode: Boolean,
    val isFavorite: Boolean
)
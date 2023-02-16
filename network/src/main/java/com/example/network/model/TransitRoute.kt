package com.example.network.model

import com.google.gson.annotations.SerializedName

data class TransitRouteResultBody(
    val items: TransitRouteItem
)

data class TransitRouteItem(
    val item: List<TransitRoute>
)

/**
 * 경유 노선
 * @param routeId 노선 ID
 * @param routeNo 노선 번호
 * @param routeType 노선 유형
 * @param endNodeNm 종점
 * @param startNodeNm 기점
 * **/
data class TransitRoute(
    @SerializedName("routeid")
    val routeId: String,
    @SerializedName("routeno")
    val routeNo: Int,
    @SerializedName("routetp")
    val routeType: String,
    @SerializedName("endnodenm")
    val endNodeNm: String,
    @SerializedName("startnodenm")
    val startNodeNm: String
)
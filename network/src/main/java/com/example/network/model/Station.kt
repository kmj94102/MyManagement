package com.example.network.model

import com.example.mymanagement.database.entity.StationEntity
import com.google.gson.annotations.SerializedName

data class StationsResults(
    @SerializedName("SearchSTNBySubwayLineInfo")
    val info: StationsResult
)

data class StationsResult(
    @SerializedName("list_total_count")
    val listTotalCount: Int,
    val row: List<StationInfo>
)

/**
 * 지하철역 정보
 * @param stationCode 지하철 역 코드
 * @param stationName 지하철 역 이름
 * @param lineNum 호선 정보
 * **/
data class StationInfo(
    @SerializedName("STATION_CD")
    val stationCode: String,
    @SerializedName("STATION_NM")
    val stationName: String,
    @SerializedName("LINE_NUM")
    val lineNum: String
) {
    fun mapper(): StationEntity = StationEntity(
        stationCode = stationCode,
        stationName = stationName,
        lineNum = if (lineNum.first() == '0') lineNum.drop(1) else lineNum
    )
}
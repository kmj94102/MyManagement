package com.example.network.model

import com.google.gson.annotations.SerializedName

data class SubwayScheduleResults(
    val SearchSTNTimeTableByIDService: SubwayScheduleResult
)

data class SubwayScheduleResult(
    @SerializedName("list_total_count")
    val totalCount: Int,
    val row: List<SubwaySchedule>
)

/**
 * 시간표 정보
 * @param arriveTime 도착 시간
 * @param leftTime 출발 시간
 * @param startStation 기점 역 이름
 * @param lastStationName 종점 역 이름
 * @param inoutTag 상/하행 : 1 / 2
 * @param generalOrDirect G : 일반 / D : 급행
 * **/
data class SubwaySchedule(
    @SerializedName("ARRIVETIME")
    val arriveTime: String,
    @SerializedName("LEFTTIME")
    val leftTime: String,
    @SerializedName("SUBWAYSNAME")
    val startStation: String,
    @SerializedName("SUBWAYENAME")
    val lastStationName: String,
    @SerializedName("INOUT_TAG")
    val inoutTag: String,
    @SerializedName("EXPRESS_YN")
    val generalOrDirect: String,
) {
    fun toSubwayScheduleInfo(): SubwayScheduleInfo {
        val direct = if (generalOrDirect == "D") "(급행)" else ""
        return SubwayScheduleInfo(
            hour = "${leftTime.substring(0, 2)}시",
            minute = "${leftTime.substring(3, 5)}분",
            name = "$startStation > $lastStationName $direct",
            isUpLine = inoutTag == "1"
        )
    }
}

/**
 * 지하철 시간표 정보
 * @param hour 시간
 * @param minute 분
 * @param name 기점 > 종점
 * @param isUpLine 상행 여부
 * **/
data class SubwayScheduleInfo(
    val hour: String,
    val minute: String,
    val name: String,
    val isUpLine: Boolean
)
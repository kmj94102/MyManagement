package com.example.network.model

import com.google.gson.annotations.SerializedName
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * 지하철 이동 경로 결과
 * @param data 지하철 이동 경로 데이터
 * @param pageNo 페이지 번호
 * @param currentCount 현재 결과 수
 * @param totalCount 전체 결과 수
 * @param numOfRows 한 페이지 결과 수
 * @param resultCode 결과 코드
 * @param resultMsg 결과 메시지
 * **/
data class SubwayRouteResult(
    val data: SubwayRouteData,
    val pageNo: String,
    val currentCount: Int,
    val totalCount: Int,
    val numOfRows: String,
    val resultCode: Int,
    val resultMsg: String
)

/**
 * 지하철 이동 경로 데이터
 * @param arrivalTime 도착 시간
 * @param deptTime 출발 시간
 * @param time 소요 시간
 * @param fee 요금
 * @param transfer 환승 횟수
 * @param route 이동 경로
 * **/
data class SubwayRouteData(
    @SerializedName("dept_time")
    val deptTime: String,
    @SerializedName("arrv_time")
    val arrivalTime: String,
    val time: String,
    val fee: Int,
    val transfer: Int,
    val route: List<SubwayRoute>
)

/**
 * 이동 경로
 * @param stationCode 경로 역사 코드
 * @param stationName 경로 역사명
 * @param timestamp 출발 시간
 * @param trainCode 탑승 열차 코드
 * @param lineNumber 호선 번호
 * @param transferLocation 최소시간 환승 하차위치
 * **/
data class SubwayRoute(
    @SerializedName("station_cd")
    val stationCode: String,
    @SerializedName("station_nm")
    val stationName: String,
    val timestamp: String,
    @SerializedName("train_code")
    val trainCode: String,
    @SerializedName("line_num")
    val lineNumber: String,
    @SerializedName("transfer_loc")
    val transferLocation: String?
) {
    fun toRouteItem() = RouteItem(
        stationCode = stationCode,
        stationName = stationName,
        time = formatTime(timestamp),
        lineCode = lineNumber,
        transferLocation = transferLocation
    )
}

/**
 * 지하철 경로 정보
 * @param deptTime 출발 시간
 * @param arrivalTime 도착 시간
 * @param transferCount 환승 횟수
 * @param fee 요금
 * **/
data class SubwayRouteInfo(
    val deptTime: String = "",
    val arrivalTime: String = "",
    val transferCount: Int = 0,
    val fee: String = "0원",
    val list: List<RouteItem> = emptyList()
)

/**
 * 지하철 경로
 * @param stationCode 지하철 역 코드
 * @param stationName 지하철 역 이름
 * @param time 도착 시간
 * @param lineCode 지하철 호선 코드
 * @param transferLocation 환승 정보
 * **/
data class RouteItem(
    val stationCode: String,
    val stationName: String,
    val time: String,
    val lineCode: String,
    val transferLocation: String?
)


fun formatTime(
    timeString: String,
    beforeFormat: String = "HHmmss",
    afterFormat: String = "HH:mm"
): String {
    val beforeFormatter = SimpleDateFormat(beforeFormat, Locale.KOREA)
    val afterFormatter = SimpleDateFormat(afterFormat, Locale.KOREA)
    return try {
        beforeFormatter.parse(timeString)?.let {
            afterFormatter.format(it)
        } ?: ""
    } catch (e: ParseException) {
        e.printStackTrace()
        ""
    }
}
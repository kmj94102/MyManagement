package com.example.mymanagement.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 지하철 역 정보
 * @param stationCode 서울교통공사에서 제공하는 API에서 사용하는 코드
 * @param stationId 서울특별시에서 제공하는 API에서 사용하는 코드
 * @param outerCode 서울교통공사에서 제공하는 API에서 사용하는 외부 코드
 * @param stationName 지하철 역 이름
 * @param lineNum 호선 번호
 * @param lineName 호선 이름
 * **/
@Entity
data class StationEntity(
    @PrimaryKey
    val stationCode: String,
    val stationId: String,
    val outerCode: String,
    val stationName: String,
    val lineNum: String,
    val lineName: String,
) {
    companion object {
        fun fromCsv(infoList: List<String>) =
            StationEntity(
                lineNum = infoList[0],
                lineName = infoList[1],
                stationId = infoList[2],
                stationName = infoList[3].replace("&", ","),
                stationCode = infoList[4].padStart(4, '0'),
                outerCode = infoList[5]
            )
    }
}

/**
 * 지하철 역 아이템
 * @param stationCode 서울교통공사에서 제공하는 API에서 사용하는 코드
 * @param stationId 서울특별시에서 제공하는 API에서 사용하는 코드
 * @param stationName 지하철 역 이름
 * @param lineNames 호선 이름
 * @param isFavorite 즐겨찾기 여부
 * **/
data class StationItem(
    val stationCode: String,
    val stationId: String,
    val stationName: String,
    val lineNames: String,
    val isFavorite: Boolean
)
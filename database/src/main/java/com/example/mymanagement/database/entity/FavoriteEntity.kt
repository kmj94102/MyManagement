package com.example.mymanagement.database.entity

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * 즐겨 찾기
 * @param type Bus, BusStop, Subway, SubwayDestination 중 1개 선택 (const 제공)
 * @param startTime 시작 시간
 * @param endTime 종료 시간
 * @param name 이름
 * @param id 서버 조회를 위한 아이디
 * @param timeStamp 등록 시간
 * **/
@Entity(
    indices = [
        Index(unique = true, value = ["id", "name"])
    ]
)
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true) val index: Int,
    val type: String,
    val startTime: String,
    val endTime: String,
    val name: String,
    val id: String,
    val timeStamp: Long
) {
    fun toFavorite(): Favorite = Favorite(
        type = type,
        time = "$startTime - $endTime",
        name = name,
        id = id
    )
    companion object {
        const val TypeBus = "Bus"
        const val TypeBusStop = "BusStop"
        const val TypeSubway = "Subway"
        const val TypeSubwayDestination = "SubwayDestination"
        const val Separator = "|||"
    }
}

/**
 * 즐겨찾기 정보
 * @param type 즐겨찾기 타입
 * @param time 홈 노출 시간
 * @param name 이름
 * @param id 아이디
 * **/
data class Favorite(
    val type: String,
    val time: String,
    val name: String,
    val id: String
)
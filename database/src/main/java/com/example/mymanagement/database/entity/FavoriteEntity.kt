package com.example.mymanagement.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 즐겨 찾기
 * @param type
 * **/
@Entity
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true) val index: Int,
    val type: String,
    val startTime: String,
    val endTime: String,
    val name: String,
    val id: String
) {
    companion object {
        const val TypeBus = "Bus"
        const val TypeBusStop = "BusStop"
        const val TypeSubway = "Subway"
        const val TypeSubwayDestination = "SubwayDestination"
        const val Separator = "||"
    }
}
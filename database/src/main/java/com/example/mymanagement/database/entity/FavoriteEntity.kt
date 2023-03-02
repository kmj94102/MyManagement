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
    val id: String,
    val timeStamp: Long
) {
    fun mapper(): Favorite = Favorite(
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

data class Favorite(
    val type: String,
    val time: String,
    val name: String,
    val id: String
)
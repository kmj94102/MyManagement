package com.example.mymanagement.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StationEntity(
    @PrimaryKey
    val stationCode: String,
    val stationName: String,
    val lineNum: String
)

data class StationItem(
    val stationCode: String,
    val stationName: String,
    val lineNames: String,
    val isFavorite: Boolean
)
package com.example.mymanagement.view.xml.model

import com.example.mymanagement.database.entity.StationItem

data class Station(
    val stationCode: String,
    val stationId: String,
    val stationName: String,
    val lineList: List<String>,
    val isFavorite: Boolean,
    var isStartSelect: Boolean,
    var isEndSelect: Boolean
) {
    companion object {
        private fun fromStationItem(stationItem: StationItem) = Station(
            stationCode = stationItem.stationCode,
            stationId = stationItem.stationId,
            stationName = stationItem.stationName,
            lineList = stationItem.lineNames.split(","),
            isFavorite = stationItem.isFavorite,
            isStartSelect = false,
            isEndSelect = false
        )

        fun fromStationItemList(list: List<StationItem>): List<Station> {
            return list.map { fromStationItem(it) }
        }
    }
}

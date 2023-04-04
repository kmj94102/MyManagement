package com.example.mymanagement.view.xml.model

import com.example.mymanagement.util.toHexString
import com.example.network.model.RouteItem
import com.example.network.model.SubwayLine
import com.example.network.model.SubwayRouteInfo

sealed class SubwayRouteRecyclerItem {
    data class ArrivalInfo(
        val deptTime: String = "",
        val arrivalTime: String = "",
        val transferCount: Int = 0,
        val fee: String = "0원",
    ) : SubwayRouteRecyclerItem()

    data class SubwayEndPoint(
        val stationCode: String,
        val stationName: String,
        val time: String,
        val lineCode: String,
        val lineColor: String,
        val transferLocation: String?,
        val isStart: Boolean
    ) : SubwayRouteRecyclerItem() {
        companion object {
            fun fromRouteItem(item: RouteItem, isStart: Boolean) = SubwayEndPoint(
                stationCode = item.stationCode,
                stationName = item.stationName,
                time = item.time,
                lineCode = item.lineCode,
                lineColor = SubwayLine.getLineColorByRouteCode(item.lineCode).toHexString(),
                transferLocation = item.transferLocation,
                isStart = isStart
            )
        }
    }

    data class SubwayMidPoint(
        val stationCode: String,
        val stationName: String,
        val time: String,
        val lineCode: String,
        val lineColor: String,
    ) : SubwayRouteRecyclerItem() {
        companion object {
            fun fromRouteItem(item: RouteItem) = SubwayMidPoint(
                stationCode = item.stationCode,
                stationName = item.stationName,
                time = item.time,
                lineCode = item.lineCode,
                lineColor = SubwayLine.getLineColorByRouteCode(item.lineCode).toHexString(),
            )
        }
    }

    object Transfer : SubwayRouteRecyclerItem()

    companion object {
        fun convertArrivalInfo(info: SubwayRouteInfo) =
            ArrivalInfo(
                deptTime = info.deptTime,
                arrivalTime = info.arrivalTime,
                transferCount = info.transferCount,
                fee = info.fee
            )

        fun convertRecyclerItemList(list: List<RouteItem>): List<SubwayRouteRecyclerItem> {
            val result = mutableListOf<SubwayRouteRecyclerItem>()
            var isStart = true
            list.forEachIndexed { index, item ->
                when {
                    isStart -> {
                        result.add(SubwayEndPoint.fromRouteItem(item, true))
                        isStart = false
                    }
                    item.transferLocation != null || index == list.lastIndex -> {
                        result.add(SubwayEndPoint.fromRouteItem(item, false))
                        isStart = true
                        if (index != list.lastIndex) {
                            result.add(Transfer)
                        }
                    }
                    else -> {
                        result.add(SubwayMidPoint.fromRouteItem(item))
                    }
                }
            }

            return result
        }
    }
}
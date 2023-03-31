package com.example.network.repository

import com.example.mymanagement.database.FavoriteDao
import com.example.mymanagement.database.entity.FavoriteEntity
import com.example.network.model.BusEstimatedArrivalInfo
import com.example.network.model.BusStopRoute
import com.example.network.model.BusStopRouteItem
import com.example.network.model.toBusEstimatedArrivalInfo
import com.example.network.service.BusClient
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class BusRepositoryImpl @Inject constructor(
    private val client: BusClient,
    private val favoriteDao: FavoriteDao
) : BusRepository {

    override fun fetchEstimatedArrivalInfoList(
        cityCode: Int,
        nodeId: String,
    ) = flow {
        try {
            val list = mutableListOf<BusEstimatedArrivalInfo>()
            client.fetchEstimatedArrivalInfoList(
                cityCode = cityCode,
                nodeId = nodeId
            ).groupBy { it.routeId }.forEach { (_, value) ->
                list.add(
                    value.toBusEstimatedArrivalInfo(
                        isFavorite = favoriteDao.fetchFavoriteCountById(
                            id = "${value[0].nodeId}${FavoriteEntity.Separator}${value[0].routeId}"
                        )
                    )
                )
            }
            emit(list)
        } catch (e: Exception) {
            e.printStackTrace()
            emit(emptyList())
        }
    }

    override fun isFavoriteBusStop(nodeId: String): Flow<Boolean> =
        favoriteDao.fetchFavoriteById(nodeId)

    override suspend fun toggleBusStopFavoriteStatus(name: String, nodeId: String) {
        try {
            favoriteDao.favoriteInsertOrDelete(
                type = FavoriteEntity.TypeBusStop,
                id = nodeId,
                name = name
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun toggleBusFavoriteStatus(info: BusEstimatedArrivalInfo) {
        try {
            favoriteDao.favoriteInsertOrDelete(
                type = FavoriteEntity.TypeBus,
                id = "${info.nodeId}${FavoriteEntity.Separator}${info.routeId}",
                name = "${info.busNumber}${FavoriteEntity.Separator}${info.nodeName}"
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun fetchBusStopRouteList(
        cityCode: Int,
        routeId: String
    ) = flow {
        val list = mutableListOf<BusStopRoute>()
        val numberOfRow = 20
        var pageNumber = 1
        var totalCount: Int? = null

        try {
            while (totalCount == null || (pageNumber - 1) * numberOfRow < totalCount) {
                val result = client.fetchBusStopRouteList(
                    cityCode = cityCode,
                    routeId = routeId,
                    pageNo = pageNumber
                )
                list.addAll(result.items.item)
                totalCount = result.totalCount
                pageNumber++
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        emit(
            list.map {
                it.toBusStopRouteItem(
                    isEndNode = it.index == totalCount,
                    isFavorite = favoriteDao.fetchFavoriteCountById(
                        "${it.nodeId}${FavoriteEntity.Separator}${it.routeId}"
                    )
                )
            }
        )
    }
}
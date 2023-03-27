package com.example.network.repository

import android.content.Context
import android.content.res.Resources.NotFoundException
import android.util.Log
import com.example.mymanagement.database.FavoriteDao
import com.example.mymanagement.database.SubwayDao
import com.example.mymanagement.database.entity.FavoriteEntity
import com.example.mymanagement.database.entity.StationEntity
import com.example.mymanagement.database.entity.StationItem
import com.example.network.R
import com.example.network.model.*
import com.example.network.service.SubwayClient
import com.example.network.util.priceFormat
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import javax.inject.Inject

class SubwayRepositoryImpl @Inject constructor(
    private val subwayDao: SubwayDao,
    private val favoriteDao: FavoriteDao,
    private val client: SubwayClient,
    @ApplicationContext
    private val context: Context
) : SubwayRepository {

    override suspend fun fetchStationItems(
        stationName: String,
        isFavoriteOnly: Boolean,
    ) = flow {
        val allOrFavorite = if (isFavoriteOnly) listOf(1) else listOf(0, 1)
        if (subwayDao.getNumberOfStations() <= 0) {
            insertSubwayStation()
        }
        subwayDao.fetchStationItems(stationName, allOrFavorite).collect {
            emit(it)
        }
    }

    private suspend fun insertSubwayStation() {
        try {
            val inputStream = context.resources.openRawResource(R.raw.station_info)
            val reader = BufferedReader(InputStreamReader(inputStream))
            val stationInfoList = mutableListOf<StationEntity>()
            reader.forEachLine {
                val infoList = it.split(',')
                stationInfoList.add(StationEntity.fromCsv(infoList = infoList))
            }

            subwayDao.insertSubwayStation(stationInfoList.drop(1))
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: NotFoundException) {
            e.printStackTrace()
        }
    }

    override suspend fun fetchStationLineNumbers(): Map<String, Boolean> = try {
        subwayDao.fetchStationLineNumbers().associateWith { true }
    } catch (e: Exception) {
        e.printStackTrace()
        mapOf()
    }

    override suspend fun updateFavorite(
        item: StationItem
    ) = try {
        favoriteDao.favoriteInsertOrDelete(
            type = FavoriteEntity.TypeSubway,
            id = item.stationCode,
            name = item.stationName
        )
    } catch (e: Exception) {
        e.printStackTrace()
    }

    override fun fetchRealtimeStationArrivals(
        keyword: String
    ) = flow {
        val items = mutableListOf<SubwayArrival>()
        val result = client.fetchRealtimeStationArrivals(keyword)

        if (result.isEmpty()) throw NoSuchElementException("데이터가 없습니다.")

        items.addAll(
            result
                .groupBy { it.subwayLineId }
                .map { (subwayLineId, subwayArrivalInfoList) ->
                    createSubwayArrival(
                        stationName = result[0].stationName,
                        subwayLineId = subwayLineId,
                        subwayArrivalInfoList = subwayArrivalInfoList
                    )
                }
        )
        emit(items.sortedBy { it.subwayLineId })
    }

    private suspend fun createSubwayArrival(
        stationName: String,
        subwayLineId: String,
        subwayArrivalInfoList: List<SubwayArrivalInfo>
    ): SubwayArrival {
        val stationCode = fetchSubwayCodeById(subwayArrivalInfoList[0].stationId)
        val prevStation =
            subwayDao.fetchSubwayNameById(subwayArrivalInfoList[0].nextStationId)
        val nextStation =
            subwayDao.fetchSubwayNameById(subwayArrivalInfoList[0].prevStationId)

        return SubwayArrival(
            subwayLineId = subwayLineId,
            stationCode = stationCode,
            prevStationName = prevStation,
            nextStationName = nextStation,
            currentStationName = stationName,
            arrItemList = subwayArrivalInfoList
                .map {
                    SubwayArrivalItem(
                        arrInfo = "${it.destinationName}행 ${it.arrInfo}",
                        isUpLine = it.updnLine == "상행" || it.updnLine == "외선"
                    )
                }
        )
    }

    override fun fetchSubwayRoute(
        searchTime: String,
        startStationCode: String,
        endStationCode: String,
        week: String
    ): Flow<SubwayRouteInfo> = flow {
        val list = mutableListOf<RouteItem>()
        val numberOfRow = 50
        var pageNumber = 1
        var totalCount: Int? = null
        var arrivalTime = ""
        var fee = 0
        var transferCount = 0

        try {
            while (totalCount == null || (pageNumber - 1) * numberOfRow < totalCount) {
                val result = client.fetchSubwayRoute(
                    pageNo = pageNumber,
                    numOfRows = numberOfRow,
                    searchTime = searchTime,
                    startStationCode = startStationCode,
                    endStationCode = endStationCode,
                    week = week
                )
                arrivalTime = result.data.arrivalTime
                fee = result.data.fee
                list.addAll(result.data.route.map { it.toRouteItem() })
                totalCount = result.totalCount
                transferCount = result.data.transfer
                pageNumber++
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        emit(
            SubwayRouteInfo(
                deptTime = list.getOrNull(0)?.time ?: "",
                arrivalTime = formatTime(arrivalTime),
                transferCount = transferCount,
                fee = fee.priceFormat(),
                list = list
            )
        )
    }

    override fun fetchSubwaySchedule(
        stationCode: String,
        week: Int,
        direction: Int
    ) = flow {
        var start = 1
        val numberOfRow = 100
        var totalCount: Int? = null

        try {
            while (totalCount == null || start < totalCount) {
                val result = client.fetchSubwaySchedule(
                    start = start,
                    end = start + numberOfRow - 1,
                    stationCode = stationCode,
                    week = week,
                    direction = direction
                )
                totalCount = result.totalCount
                start += numberOfRow
                emit(result.row.map { it.toSubwayScheduleInfo() })
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(emptyList())
        }
    }

    private suspend fun fetchSubwayCodeById(stationId: String): String =
        subwayDao.fetchSubwayCodeById(stationId)
}
package com.example.network.repository

import android.content.Context
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.BufferedReader
import java.io.InputStreamReader
import java.text.DecimalFormat
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
        allOrFavorite: List<Int>,
    ): Flow<List<StationItem>> {
        if (subwayDao.getNumberOfStations() <= 0) {
            val inputStream = context.resources.openRawResource(R.raw.station_info)
            val reader = BufferedReader(InputStreamReader(inputStream))
            val stationInfoList = mutableListOf<StationEntity>()
            reader.forEachLine {
                val infoList = it.split(',')
                stationInfoList.add(
                    StationEntity(
                        lineNum = infoList[0],
                        lineName = infoList[1],
                        stationId = infoList[2],
                        stationName = infoList[3].replace("&", ","),
                        stationCode = infoList[4].padStart(4, '0'),
                        outerCode = infoList[5]
                    )
                )
            }

            subwayDao.insertSubwayStation(stationInfoList.drop(1))
        }

        return subwayDao.fetchStationItems(
            stationName = stationName,
            allOrFavorite = allOrFavorite
        )
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

        val currentStation = result[0].stationName

        items.addAll(
            result
                .groupBy { it.subwayLineId }
                .map { (subwayLineId, subwayArrivalInfoList) ->
                    val prevStation =
                        subwayDao.fetchSubwayName(subwayArrivalInfoList[0].nextStationId)
                    val nextStation =
                        subwayDao.fetchSubwayName(subwayArrivalInfoList[0].prevStationId)
                    SubwayArrival(
                        subwayLineId = subwayLineId,
                        prevStationName = prevStation,
                        nextStationName = nextStation,
                        currentStationName = currentStation,
                        arrItemList = subwayArrivalInfoList
                            .map {
                                SubwayArrivalItem(
                                    arrInfo = "${it.destinationName}행 ${it.arrInfo}",
                                    isUpLine = it.updnLine == "상행" || it.updnLine == "외선"
                                )
                            }
                    )
                }
        )
        emit(items.sortedBy { it.subwayLineId })
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

}